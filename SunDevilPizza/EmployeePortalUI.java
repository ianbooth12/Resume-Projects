
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

//Class to build Employee portal UI pane
public class EmployeePortalUI extends Pane{
    
    //Declaring Variables...
    private Label headerLabel;
    private Label message;
    private Button homeButton;
    private List<Customer> queue = new ArrayList<>();
    private ScrollPane queueSP;
    private final String[] opaButtonText = new String[] {"READY TO COOK"};
    private final String[] chefButtonText = new String[] {"COOKING", "READY"};
    private String type;
    
    //Constructor
    EmployeePortalUI(int width, int height, String type) {
        setWidth(width); //Sets this pane width
        setHeight(height); //Sets this pane height
        setStyle("-fx-background-color: #FFFFFF"); //Sets pane background color to white
        this.type = type; //Sets current employee type to type in constructor parameters
        homeButton = new ButtonMaker("home"); //Creates new button with text "home"
        queueSP = new ScrollPane(); //Creates new scrollpane
        queueSP.setPrefWidth(800); //Sets scrollpane preffered width to 800
        queueSP.setPrefHeight(200); //Sets scrollpane preffered height to 200
        queueSP.layoutXProperty().bind(this.widthProperty().subtract(queueSP.getPrefWidth()).divide(2)); //Sets scrollpane centering X value
        queueSP.layoutYProperty().set(400); //Moving scrollPane down slightely
        File[] customerFileList = new File(SunDevilPizza.customerFilesPath).listFiles(); //Creates a new file array for the file path to customer records file
        iterateCustomers(customerFileList); //Makes a method call to iterateCustomers and passes in the newly created customerFileList object
        queueSP.setContent(createQueuePane()); //Sets scrollpane content by making a method call to create a new queuepane 
        headerLabel = new Label(type + " Queue:"); //Sets label's text to "Queue"
        headerLabel.setFont(new Font("Arial", 40)); //Sets label's font and text size
        headerLabel.layoutXProperty().bind(this.widthProperty().subtract(queueSP.getPrefWidth()).divide(2)); //Sets label's centering X value
        headerLabel.layoutYProperty().set(320); //Moving scrollPane down slightely
        message = new Label("");
        message.setFont(new Font("Arial", 32));
        message.layoutXProperty().bind(this.widthProperty().subtract(queueSP.getPrefWidth()).divide(2));
        message.layoutYProperty().set(640);
        homeButton.setOnAction(new AdminPortalControlsHandler()); //Setting up eventhandler for pane's buttons
        getChildren().addAll(headerLabel, homeButton, queueSP, message); //Adding everything to pane
    }
    
    //Method iterates through customer order records to check whether for which orders have finished being processed and which
    //      need to be added to the queue so order processing agents and chefs can interact with them to update pizza statuses
    public void iterateCustomers(File[] files) {
        for (File file : files) { //Looping for amount of customer orders in records
            Customer customer = FileManager.loadCustomer(file.getName().split("[.]")[0]); //Creating new file object by calling the FileManager class
            for (int i = 0; i < customer.getOrderHistory().size(); i++) { //Looping through customer's order history to verify past order's pizza statuses
                //Checks if current employee type is the "Order Processing Agent" and checks if the status of the current indexed order status is set to "Accepted"
                if (type.equalsIgnoreCase("Order Processing Agent") && customer.getOrderHistory().get(i).getStatus().equalsIgnoreCase("ACCEPTED")) {
                    queue.add(customer); //Adds customer's order to current queue
                } 
                else if (type.equalsIgnoreCase("Chef") && customer.getOrderHistory().get(i).getStatus().equals("READY TO COOK") || customer.getOrderHistory().get(i).getStatus().equals("COOKING")) {
                    //Checks if current employee type is the "Chef" and checks if the status of the current indexed
                    //  order status is set to "Ready to cook" or "Cooking" before adding customer order to queue
                    queue.add(customer); //Adds customer's order to current queue 
                }
            } 
        }
    }
    
     //Method for employees to update order queue status
    public void updateQueue(String orderNumber, String newStatus, SelectionBar bar) {
        for(Customer customer : queue) { //Loops for however many customers have orders in queue
            for(Order order : customer.getOrderHistory()) { //Loops for each customer's order history
                if (order.getOrderNumber().equals(orderNumber)) { //Checks if the customer's order number is the same as order that is currently being processed
                    order.setStatus(newStatus); //Updates order status to pizza's current status
                }
            }
            FileManager.saveCustomer(customer); //Saves customer's order information to system
        }
        //Checking if order is just coming in or if it is ready to be removed from queue
        if (newStatus.equals("READY TO COOK") || newStatus.equals("READY")) {
            ((Pane)queueSP.getContent()).getChildren().remove(bar); //Updates queue
            if (newStatus.equals("READY")) { //If the order is ready
                message.setText("Email sent for order " + orderNumber); //"Sends email"...
            }
        }
    }
    
    //Method creates pane for chef and order processing agents to make changes to pizza order statuses
    private Pane createQueuePane() {
        Pane toppingsBasePane = new Pane(); //Creates new pane
        toppingsBasePane.setPrefWidth(780); //Sets pane's preffered width to 400
        int toppingsSelectionBarBaseY = 5; //Setting base int value
        for(Customer customer : queue) { //Looping for customer orders in queue
            for(Order order : customer.getOrderHistory()) { //Looping for each customer's order history
                SelectionBar bar; //Crreats a selectionBar
                //Checking if Order Processing Agent is current session user and if current indexed order in customer list has "ACCEPTED" status 
                if (type.equalsIgnoreCase("Order Processing Agent") && order.getStatus().equalsIgnoreCase("ACCEPTED")) {
                    //Creating a new selectionBar to be added to the queue pane being constructed which includes an order's number and status 
                    bar = new SelectionBar(600, "status", order.getOrderNumber(), 1, opaButtonText, 400, 140);
                    bar.relocate(5, toppingsSelectionBarBaseY); //Relocating selectionBar to (5, (current value of toppingsSelectionBarBaseY after arithmetic))
                    toppingsSelectionBarBaseY += 40; //Incrementing integer by 40 each loop iteration
                    toppingsBasePane.getChildren().add(bar); //Adding selectionBar to base pane
                } //Checking if Chef is current session user and if current indexed order in customer list has "READY" status
                else if (type.equalsIgnoreCase("Chef") && !order.getStatus().equalsIgnoreCase("READY")) {
                    //Creating a new selectionBar to be added to the queue pane being constructed which includes an order's number and status
                    bar = new SelectionBar(600, "status", order.getOrderNumber(), 2, chefButtonText, 120, 140);
                    bar.relocate(5, toppingsSelectionBarBaseY); //Relocating selectionBar to (5, (current value of toppingsSelectionBarBaseY after arithmetic))
                    toppingsSelectionBarBaseY += 40; //Incrementing integer by 40 each loop iteration
                    toppingsBasePane.getChildren().add(bar); //Adding selectionBar to base pane 
                }
            }
        }
        return toppingsBasePane; //Returns completed queue pane 
    }
    
    private class AdminPortalControlsHandler implements EventHandler<javafx.event.ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            Sounds.playButtonClick();
            if (event.getSource() == homeButton) {
                SunDevilPizza.session.setUser(null);
                SunDevilPizza.home();
            }
        }
    }
    
} //End of EmployeePortalUI class
