
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import static jdk.internal.org.jline.terminal.Terminal.MouseTracking.Button;

//class for displaying the order summary
public class OrderSummaryUI extends Pane { //defining variables
    private Button backButton;
    private Button forwardButton;
    private Button purchaseButton;
    private Button verifyButton;
    private Label orderSummaryLabel;
    private Label completePurchaseLabel;
    private Label signedInAsLabel;
    private TextArea orderSummaryTextField;
    private TextField asuriteIDField;
    private PasswordField passwordField;
    private TextField emailField;
    private boolean alreadySignedIn = false;

    OrderSummaryUI(int width, int height, String orderSummary) {
        setWidth(width); //Sets this pane width
        setHeight(height); //Sets this pane height
        setStyle("-fx-background-color: #FFFFFF");
        
        orderSummaryTextField = new TextArea(); //creating a new non editable text area that will display the order summary
        orderSummaryTextField.setPrefWidth(600); //600 x 200 px size
        orderSummaryTextField.setPrefHeight(200);
        orderSummaryTextField.layoutXProperty().bind(this.widthProperty().subtract(orderSummaryTextField.getPrefWidth()).divide(2)); //setting the size of the text feild in relation to the overall layout
        orderSummaryTextField.layoutYProperty().set(220);
        orderSummaryTextField.setEditable(false);
        orderSummaryTextField.setStyle("-fx-text-fill: black; -fx-background-color: lightgrey;");
        orderSummaryTextField.setText(orderSummary);
        
        orderSummaryLabel = new Label("Order Summary:"); //Order summary label to denote which page the user is on
        orderSummaryLabel.layoutXProperty().bind(this.widthProperty().subtract(orderSummaryTextField.getPrefWidth()).divide(2)); //setting horizontal label in relation to everything else
        orderSummaryLabel.layoutYProperty().set(140); //setting static width
        orderSummaryLabel.setFont(new Font("Arial", 40));
        
        completePurchaseLabel = new Label("Complete Purchase:"); //complete purchase label
        completePurchaseLabel.layoutXProperty().bind(this.widthProperty().subtract(orderSummaryTextField.getPrefWidth()).divide(2));
        completePurchaseLabel.layoutYProperty().set(520);
        completePurchaseLabel.setFont(new Font("Arial", 40));
        
        asuriteIDField = new TextField(); //initiating a user editable text field which will take a string input from the user to save as an ASURITE ID
        asuriteIDField.setPrefSize(600, 40);
        asuriteIDField.layoutXProperty().bind(this.widthProperty().subtract(asuriteIDField.getPrefWidth()).divide(2));
        asuriteIDField.layoutYProperty().set(600);
        asuriteIDField.setPromptText("ASURITE ID");
        asuriteIDField.setStyle("-fx-background-color: lightgrey;");
        
        passwordField = new PasswordField(); //initiating a user editable text field which will take a string input from the user to save as an unique PASSWORD
        passwordField.setPrefSize(600, 40);
        passwordField.layoutXProperty().bind(this.widthProperty().subtract(asuriteIDField.getPrefWidth()).divide(2));
        passwordField.layoutYProperty().set(660);
        passwordField.setStyle("-fx-background-color: lightgrey;");
        passwordField.setVisible(false);
        
        signedInAsLabel = new Label(""); //Invisible label to be hidden from the user
        signedInAsLabel.layoutXProperty().bind(this.widthProperty().subtract(orderSummaryTextField.getPrefWidth()).divide(2));
        signedInAsLabel.layoutYProperty().set(600);
        signedInAsLabel.setFont(new Font("Arial", 40));
        signedInAsLabel.setVisible(false);
        
        verifyButton = new Button("Verify ID"); //Button to verify that the ASURITE ID is valid
        verifyButton.setPrefSize(160, 40);
        verifyButton.setStyle("-fx-text-fill: black; -fx-background-color: lightgrey;");
        verifyButton.layoutXProperty().bind(this.widthProperty().subtract(verifyButton.widthProperty()).divide(2));
        verifyButton.setLayoutY(660);
        verifyButton.setOnAction(new OrderSummaryControlsHandler());
        
        emailField = new TextField(); //initiating a user editable text field which will take a string input from the user to save as an unique EMAIL to send updates to the user
        emailField.setPrefSize(600, 40);
        emailField.layoutXProperty().bind(this.widthProperty().subtract(asuriteIDField.getPrefWidth()).divide(2));
        emailField.layoutYProperty().set(720);
        emailField.setPromptText("Email Address");
        emailField.setStyle("-fx-background-color: lightgrey;");
        
        purchaseButton = new Button("Purchase"); //A PURCHASE button to complete the transaction
        purchaseButton.setPrefSize(160, 40);
        purchaseButton.setStyle("-fx-text-fill: black; -fx-background-color: lightgrey;");
        purchaseButton.layoutXProperty().bind(this.widthProperty().subtract(purchaseButton.widthProperty()).divide(2));
        purchaseButton.setLayoutY(800);
        purchaseButton.setOnAction(new OrderSummaryControlsHandler());
        
        backButton = new ButtonMaker("back"); //A BACK button to navigate the user across roots
        backButton.setOnAction(new OrderSummaryControlsHandler());
        
        if (!((Customer)SunDevilPizza.session.getUser()).getIDNum().equals("-1")) { //If the user is logged in already
            asuriteIDField.setVisible(false); //hide the field where a ASURITE id is required
            verifyButton.setVisible(false);
            Customer customer = (Customer)SunDevilPizza.session.getUser(); //getting the user ID
            signedInAsLabel.setText("Signed in as ASURITE ID: " + customer.getIDNum()); //displaying the USER's ASURITE ID
            alreadySignedIn = true; //The user is signed in
            signedInAsLabel.setVisible(true); //visual confirmation that user is signed in
        }
        //adding all
        getChildren().addAll(orderSummaryLabel, orderSummaryTextField, completePurchaseLabel, asuriteIDField, signedInAsLabel, verifyButton, passwordField, emailField, purchaseButton, backButton);  
    }
    
    //Handler for all UI controls...
    private class OrderSummaryControlsHandler implements EventHandler<javafx.event.ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) { //every time a button is clicked make a button click noise
            Sounds.playButtonClick();
            if (event.getSource() == backButton) { //go back to the previous root if BACK is clicked
                SunDevilPizza.previousRoot();
            }
            if (event.getSource() == verifyButton) { //if the VERIFY button is clicked
                boolean exists = FileManager.existingCustomer(asuriteIDField.getText()); //check if there is an existing customer that has the same ASURITE ID
                if (exists == true) { //if there is an existing user
                    verifyButton.setVisible(false); //verify button is no longer visible as now we are requesting a password
                    passwordField.setPromptText("Enter your password");
                    passwordField.setVisible(true); //password field is visible and prompts user to enter password
                    completePurchaseLabel.setStyle("-fx-text-fill: black;");
                }
                else if (CredentialVerification.isAnAsuriteID(asuriteIDField.getText())){ //if the user does not exist and the ASURITE ID is correctly formatted
                    SunDevilPizza.session.setUser(new Customer(asuriteIDField.getText()));
                    verifyButton.setVisible(false);
                    passwordField.setPromptText("Create a new password"); //user is prompted to to create a password
                    passwordField.setVisible(true);
                    completePurchaseLabel.setStyle("-fx-text-fill: black;");
                }
                else {
                    completePurchaseLabel.setStyle("-fx-text-fill: red;"); //if there is an error the button is red
                }
            }
            if(event.getSource() == purchaseButton) { //if purcase button is clicked
                if (emailField.getText().contains("@") && emailField.getText().contains(".") && (alreadySignedIn || !asuriteIDField.getText().equals("") && !passwordField.getText().equals(""))) { //if the email field is populated and if the user is signed in or the feilds are populated
                    Customer customer; //create a customer
                    if (FileManager.existingCustomer(asuriteIDField.getText())) { //check if it is an existing customer
                        customer = CredentialVerification.customerLoginCheck("asurite", asuriteIDField.getText(), passwordField.getText());
                        if (customer != null) { //if it is, set the session to that customer
                            SunDevilPizza.session.setUser(customer); //set it to the already signed in customer
                        }
                        else {
                            completePurchaseLabel.setStyle("-fx-text-fill: red;");
                            return;
                        }
                    }
                    else if (!passwordField.getText().equals("")){ //get the user password to create a new customer
                        ((Customer)SunDevilPizza.session.getUser()).setPassword(passwordField.getText());
                    }
                    customer = ((Customer)SunDevilPizza.session.getUser()); //set customer properties including current order, order number, and order status
                    customer.getCurrentOrder().setEmail(emailField.getText());
                    String orderNumber = SunDevilPizza.session.generateOrderNumber();
                    customer.getCurrentOrder().setOrderNumber(orderNumber);
                    customer.getCurrentOrder().setStatus("ACCEPTED");
                    customer.addOrder(customer.getCurrentOrder());
                    customer.resetCurrentOrder();
                    FileManager.saveCustomer(customer); //save the customer
                    SunDevilPizza.newRoot(new OrderConfirmationUI(SunDevilPizza.width, SunDevilPizza.height, orderNumber)); //new customer confirmation login
                }
                else {
                    completePurchaseLabel.setStyle("-fx-text-fill: red;");
                }
            }
        }
    }
}
