import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class PizzaBuilderUI extends Pane {
    //Declaring Variables...
    private final String[] toppingsList = new String[] {"Mushroom", "Onion", "Olives", "Extra Cheese"};
    private final String[] sizeButtonText = new String[] {"Small", "Medium", "Large"};
    private final String[] typeButtonText = new String[] {"Cheese", "Pepperoni", "Vegetable"};
    private final String[] toppingButtonText = new String[] {"Lt", "Reg", "Ex"};
    private String[] timeOptions = new String[] {"12:00PM", "12:15PM", "12:30PM", "12:45PM", 
                                                    "1:00PM", "1:15PM", "1:30PM", "1:45PM", 
                                                    "2:00PM", "2:15PM", "2:30PM", "2:45PM", 
                                                    "3:00PM", "3:15PM", "3:30PM", "3:45PM", 
                                                    "4:00PM", "4:15PM", "4:30PM", "4:45PM", 
                                                    "5:00PM", "5:15PM", "5:30PM", "5:45PM", 
                                                    "6:00PM", "6:15PM", "6:30PM", "6:45PM",
                                                    "7:00PM", "7:15PM", "7:30PM", "7:45PM",
                                                    "8:00PM", "8:15PM", "8:30PM", "8:45PM",
                                                    "9:00PM", "9:15PM", "9:30PM", "9:45PM"};
    private Button backButton;
    private Button forwardButton;
    private Label customizationLabel;
    private Label pizzaToppingsLabel;
    private Label timeSelectionLabel;
    private ChoiceBox<String> timePicker;
    private ScrollPane toppingsSP;
    private LargeSelectionBar sizeSelector;
    private LargeSelectionBar pizzaBaseSelector;
    private Label orderSummaryLabel;
    private TextArea orderSummaryTextField;
    private String pizzaSize;
    private String pizzaBase;
    private String[] toppings;
    
    //Constructor
    PizzaBuilderUI(int width, int height) {
        setWidth(width); //Sets this pane width
        setHeight(height); //Sets this pane height
        setStyle("-fx-background-color: #FFFFFF"); //setting background color
        customizationLabel = new Label("Pizza Base Customization:"); //setting default labels and buttons
        customizationLabel.relocate(100, 80);
        customizationLabel.setFont(new Font("Arial", 40));
        pizzaToppingsLabel = new Label("Pizza Toppings:");
        pizzaToppingsLabel.relocate(100, 520);
        pizzaToppingsLabel.setFont(new Font("Arial", 40));
        timeSelectionLabel = new Label("Pickup Time:");
        timeSelectionLabel.relocate(100, 830);
        timeSelectionLabel.setFont(new Font("Arial", 40));
        timePicker = new ChoiceBox<>(); //creating a choice box for the time to pick up the order
        timePicker.getItems().addAll("12:00PM", "12:15PM", "12:30PM", "12:45PM",
                                    "1:00PM", "1:15PM", "1:30PM", "1:45PM", 
                                    "2:00PM", "2:15PM", "2:30PM", "2:45PM", 
                                    "3:00PM", "3:15PM", "3:30PM", "3:45PM", 
                                    "4:00PM", "4:15PM", "4:30PM", "4:45PM", 
                                    "5:00PM", "5:15PM", "5:30PM", "5:45PM", 
                                    "6:00PM", "6:15PM", "6:30PM", "6:45PM",
                                    "7:00PM", "7:15PM", "7:30PM", "7:45PM",
                                    "8:00PM", "8:15PM", "8:30PM", "8:45PM",
                                    "9:00PM", "9:15PM", "9:30PM", "9:45PM"); //order pickup options
        timePicker.setMinSize(240, 40);
        timePicker.relocate(100, 900);
        timePicker.setOnAction(new PizzaBuilderControlsHandler()); //calling the control handler when a button is pressed
        toppingsSP = new ScrollPane();
        toppingsSP.relocate(100, 600);
        toppingsSP.setPrefWidth(600);
        toppingsSP.setPrefHeight(170);
        toppingsSP.setContent(createToppingsPane()); //creating a topping pane
        sizeSelector = new LargeSelectionBar("size", "Size:", 3, sizeButtonText); //importing the selection bar for pizza size
        sizeSelector.relocate(100, 160);
        pizzaBaseSelector = new LargeSelectionBar("type", "Type:", 3, typeButtonText); //importing selection bar the pizza base
        pizzaBaseSelector.relocate(100, 320);
        orderSummaryLabel = new Label("Order Summary:"); //creating an order summary label
        orderSummaryLabel.relocate(1220, 520);
        orderSummaryLabel.setFont(new Font("Arial", 40));
        orderSummaryTextField = new TextArea(); //creating a text field for the order summary
        orderSummaryTextField.setPrefWidth(600);
        orderSummaryTextField.setPrefHeight(170);
        orderSummaryTextField.relocate(1220, 600);
        orderSummaryTextField.setEditable(false); //making sure that only the program can output to the text field, not the user
        backButton = new ButtonMaker("back"); //button for moving back between nodes
        backButton.setOnAction(new PizzaBuilderControlsHandler());
        forwardButton = new ButtonMaker("forward"); //button for moving forward btween nodes
        forwardButton.setOnAction(new PizzaBuilderControlsHandler());
        getChildren().addAll(customizationLabel, sizeSelector, pizzaBaseSelector, pizzaToppingsLabel, toppingsSP, timeSelectionLabel, timePicker, orderSummaryLabel, orderSummaryTextField, backButton, forwardButton);
    }
   
    private Pane createToppingsPane() {
        Pane toppingsBasePane = new Pane(); //creating a pane for the toppings
        toppingsBasePane.setPrefWidth(400); //setting width of the topping pane
        int toppingsSelectionBarBaseY = 5; //horizontal of the toppings selection bar
        for (String currentTopping : toppingsList) { //defining the toppings for each topping in the list
            SelectionBar bar = new SelectionBar(600, "topping", currentTopping, 3, toppingButtonText, 280, 60); //new selection bar for the toppings
            bar.relocate(5, toppingsSelectionBarBaseY); //setting space for the selection bar
            toppingsSelectionBarBaseY += 40;
            toppingsBasePane.getChildren().add(bar);
        }
        return toppingsBasePane;
    }
    
    public void refreshOrderSummary() { //method to ensure that the order summary is current
        Pizza currentPizza = ((Customer)SunDevilPizza.session.getUser()).getCurrentOrder().getPizza(); //getting the pizza object for the order
        String orderSummary = "Type: " + currentPizza.getType() + "\n" + "Size: " + currentPizza.getSize() + "\n" + "Toppings: \n\t"; //creating an order summary
        for (int i = 0; i < currentPizza.getToppings().size(); i++) { //for loop to delineate toppings by newlines and tabs
            orderSummary = orderSummary + currentPizza.getToppings().get(i) + "\n\t";
        }
        orderSummary = orderSummary + "\n\nPickup Time: " + ((Customer)SunDevilPizza.session.getUser()).getCurrentOrder().getPickupTime(); //adding the pickup time to be displayed after two newlines
        orderSummaryTextField.setText(orderSummary);
    }
    
    //Handler for all UI controls...
    private class PizzaBuilderControlsHandler implements EventHandler<javafx.event.ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            Sounds.playButtonClick();
            if (event.getSource() == backButton) {
                SunDevilPizza.previousRoot();
            }
            else if (event.getSource() == forwardButton) {
                customizationLabel.setStyle("-fx-text-fill: black;"); //Reset labels to black...
                timeSelectionLabel.setStyle("-fx-text-fill: black;");
                Pizza currentPizza = ((Customer)SunDevilPizza.session.getUser()).getCurrentOrder().getPizza(); //Get the current pizza
                if (currentPizza.getSize() == null || currentPizza.getType() == null) { //If the pizza customization options are null
                    customizationLabel.setStyle("-fx-text-fill: red;"); //Change label to red
                }
                if (timePicker.getValue() == null) { //If the pickup time option is null
                    timeSelectionLabel.setStyle("-fx-text-fill: red;"); //Change label to red
                }
                if (currentPizza.getSize() != null && currentPizza.getType() != null && timePicker.getValue() != null) {
                    if (SunDevilPizza.nextRoot() == false) {
                        SunDevilPizza.newRoot(new OrderSummaryUI(SunDevilPizza.width, SunDevilPizza.height, orderSummaryTextField.getText()));
                    }
                }
            }
            else if (event.getSource() == timePicker) {
                ((Customer)SunDevilPizza.session.getUser()).getCurrentOrder().setPickupTime(timePicker.getValue());
                refreshOrderSummary();
            }
        }
    }
}
