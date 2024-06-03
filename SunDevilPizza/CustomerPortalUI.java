
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

//Class to build Customer portal UI pane 
public class CustomerPortalUI extends Pane{
    //Declaring Variables...
    private Label currentOrdersLabel;
    private Label orderHistoryLabel;
    private ScrollPane currentOrderSP;
    private ScrollPane orderHistorySP;
    private List<Order> currentOrderList = new ArrayList<>();
    private List<Order> previousOrderList = new ArrayList<>();
    private Button homeButton;
    
    //Constructor
    CustomerPortalUI(int width, int height) {
        setWidth(width); //Sets this pane's width
        setHeight(height); //Sets this pane's height
        setStyle("-fx-background-color: #FFFFFF"); //Sets background to be white
        currentOrdersLabel = new Label("Current Orders:"); //Creates label with text "Current Orders"
        currentOrdersLabel.setFont(new Font("Arial", 40)); //Sets label text font and size
        currentOrdersLabel.relocate(100, 80); //Relocates new label to (100, 80)
        iterateOrders(); //
        currentOrderSP = new ScrollPane(); //Creates new scrollpane
        currentOrderSP.relocate(100, 160); //Relocates new scrollpane to (100, 160)
        currentOrderSP.setPrefWidth(800); //Sets scrollpane's preffered width to 800
        currentOrderSP.setPrefHeight(240); //Sets scrollpane's preffered height to 240
        currentOrderSP.setContent(createQueuePane(currentOrderList)); //Sets scrollpane's contents to be a list of current pizza orders 
        orderHistoryLabel = new Label("Previous Orders:"); //Creates new label with text "Previous Orders"
        orderHistoryLabel.setFont(new Font("Arial", 40)); //Sets new label's text font and size
        orderHistoryLabel.relocate(100, 520); //Relocates label to (100, 520)
        orderHistorySP = new ScrollPane(); //Creates new scrollpane
        orderHistorySP.relocate(100, 600); //Relocates new scrollpane to (100, 600)
        orderHistorySP.setPrefWidth(800); //Sets scrollpane's preffered wdith to 800
        orderHistorySP.setPrefHeight(240); //Sets scrollpane's preffered height to 240
        orderHistorySP.setContent(createQueuePane(previousOrderList)); //Sets scrollpane's contents to be a list of previous pizza orders
        homeButton = new ButtonMaker("home"); ///Creates new button with text "home"
        homeButton.setOnAction(new CustomerPortalControlsHandler()); //Sets up home buttons handler 
        getChildren().addAll(currentOrdersLabel, currentOrderSP, orderHistoryLabel, orderHistorySP, homeButton); //Adds everything to pane
    }
    
    //Method for iterating through a customer's orders
    public void iterateOrders() {
        Customer customer = (Customer)SunDevilPizza.session.getUser();
        for (int i = 0; i < customer.getOrderHistory().size(); i++) { //Looping through a customer's complete pizza order history
            if (!customer.getOrderHistory().get(i).getStatus().equalsIgnoreCase("READY")) { //Ignoring any previous orders with the status ready
                currentOrderList.add(customer.getOrderHistory().get(i)); //Adding all other orders that are not listed as "READY" to the current list
            }
            else { 
               previousOrderList.add(customer.getOrderHistory().get(i)); //Adding all pizza's with status "READY" to the customer's previous order's list
            }
        } 
    }
    
    //Method for creating a new queuePane given a list of a customer's orders
    private Pane createQueuePane(List<Order> orderList) {
        Pane orderBasePane = new Pane(); //Creates new pane
        orderBasePane.setPrefWidth(780); //Sets new pane's preffered width to 700
        int toppingsSelectionBarBaseY = 5; //Setting base int value
        SelectionBar bar; //Creating a selectionBar
        for (int i = 0; i < orderList.size(); i++) { //Looping for number of order's in a customer's list
            //Creating a new selectionBar to be added to the queue pane being constructed which includes an order's number and status 
            bar = new SelectionBar(780, "", orderList.get(i).getOrderNumber() + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + 
                            orderList.get(i).getStatus(), 0, null, 400, 140);
            bar.relocate(5, toppingsSelectionBarBaseY); //Relocating selectionBar to (5, (current value of toppingsSelectionBarBaseY after arithmetic))
            toppingsSelectionBarBaseY += 40; //Incrementing integer by 40 each loop iteration
            orderBasePane.getChildren().add(bar); //Adding selectionBar to base pane
        }
        return orderBasePane; //Returning completed queue pane
    }
    
    //Button handler for pane including audible button click sound and home button
    private class CustomerPortalControlsHandler implements EventHandler<javafx.event.ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            Sounds.playButtonClick();
            if (event.getSource() == homeButton) {
                SunDevilPizza.home();
            }
        }
    }
    
} //End of CustomerPortalUI class
