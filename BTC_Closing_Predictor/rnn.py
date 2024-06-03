import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from sklearn.metrics import r2_score
import torch
import torch.nn as nn
from torch.utils.data import Dataset, DataLoader
from torchvision import transforms

torch.manual_seed(42)

# output "Close" depends on "High", "Low", "Open"
df = pd.read_csv("coin_Bitcoin.csv")
x = df[['High', 'Low', 'Open']].values
y = df[['Close']].values

# utilizing scikit-learn for standardization
scaler_x = StandardScaler()
scaler_y = StandardScaler()
x = scaler_x.fit_transform(x)
y = scaler_y.fit_transform(y)

# 8:2 training split
train_x, test_x, train_y, test_y = train_test_split(x, y, test_size=0.2, random_state=42)

train_x = torch.tensor(train_x, dtype=torch.float).unsqueeze(1)
train_y = torch.tensor(train_y, dtype=torch.float)
test_x = torch.tensor(test_x, dtype=torch.float).unsqueeze(1)
test_y = torch.tensor(test_y, dtype=torch.float)

# different from CNN project which uses ImageFolder method, we don't have such method for RNN, so we need to write the dataset class ourselves, reference tutorial is in the main documentation
class BitCoinDataSet(Dataset):
    def __init__(self, x, y):
        self.x = x
        self.y = y

    def __len__(self):
        return len(self.x)

    def __getitem__(self, idx):
        return self.x[idx], self.y[idx]

hidden_size = 128
num_layers = 2
learning_rate = 0.001
batch_size = 64
epoch_size = 10

train_dataset = BitCoinDataSet(train_x, train_y)
test_dataset = BitCoinDataSet(test_x, test_y)
train_loader = DataLoader(train_dataset, batch_size=batch_size, shuffle=True)
test_loader = DataLoader(test_dataset, batch_size=batch_size, shuffle=False)

# model design
class RNN(nn.Module):
    def __init__(self, input_feature_size=3, hidden_size=128, num_layers=2):
        super(RNN, self).__init__()
        self.lstm = nn.LSTM(input_feature_size, hidden_size, num_layers, batch_first=True)
        self.linear1 = nn.Linear(hidden_size, 50)
        self.relu = nn.ReLU()
        self.linear2 = nn.Linear(50, 1)

    def forward(self, x):
        x, lst = self.lstm(x)
        x = self.linear1(x[:, -1, :])
        x = self.relu(x)
        x = self.linear2(x)
        return x

device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')
rnn = RNN().to(device)
# loss function is nn.MSELoss since it is regression task
criteria = nn.MSELoss()

optimizer = torch.optim.Adam(rnn.parameters(), lr=learning_rate)

# Training
rnn.train()
for epoch in range(epoch_size):
    loss = 0.0
    for batch_idx, (inputs, targets) in enumerate(train_loader):
        inputs, targets = inputs.to(device), targets.to(device)
        optimizer.zero_grad()

        outputs = rnn(inputs)

        loss = criteria(outputs, targets)
        loss.backward()

        optimizer.step()
        
        if batch_idx % 100 == 99:
            print(f'[{epoch + 1}, {batch_idx + 1:5d}] loss: {loss.item():.3f}')

# Evaluation
rnn.eval()
predictions = []
ground_truth = []
with torch.no_grad():
    for inputs, targets in test_loader:
        inputs, targets = inputs.to(device), targets.to(device)
        outputs = rnn(inputs)
        
        predictions.extend(scaler_y.inverse_transform(outputs.cpu().detach().numpy()).flatten())
        ground_truth.extend(scaler_y.inverse_transform(targets.cpu().detach().numpy()).flatten())

r2score = r2_score(ground_truth, predictions)
print(f'R2 score: {r2score:.4f}')
