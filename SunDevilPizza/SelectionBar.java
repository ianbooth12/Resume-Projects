
import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class SelectionBar extends Pane {
    //Declaring Variables...
    private Label optionLabel;
    private List<Button> buttonList;
    private String selectionType;
    private Button currentSelection;
    private final String defaultButtonStyle = "-fx-text-fill: black; -fx-background-color: lightgrey;";
    
    //Constructor
    SelectionBar(int barWidth, String selectionType, String labelText, int numOfButtons, String[] buttonText, int offSet, int buttonWidth) {
        setWidth(600); //Sets this pane width
        setHeight(100); //Sets this pane height
        this.selectionType = selectionType; //populating variables...
        optionLabel = new Label(labelText); //creating a new option label
        optionLabel.relocate(5, 5); //relocating nodes
        buttonList = new ArrayList<>(numOfButtons); //creating a new arraylist of buttonss
        getChildren().add(optionLabel);
        for (int i = 0; i < numOfButtons; i++) { //creating buttons to populate the arraylist
            buttonList.add(new Button()); //creating a new button
            buttonList.get(i).setText(buttonText[i]); //setting the text of the button depending on which button in the arraylist is being created
            buttonList.get(i).setPrefSize(buttonWidth, 20); //setting the button height to 20
            buttonList.get(i).relocate(offSet + ((i + 1) * (buttonWidth + 20)), 5); //setting offests so theres no collisons
            buttonList.get(i).setStyle(defaultButtonStyle); //setting style in accordance to the css file
            buttonList.get(i).setOnAction(new ControlsHandler()); //creating control handlers
            getChildren().add(buttonList.get(i)); //adding the button
        }
    }
    
    //Handler for all UI controls...
    private class ControlsHandler implements EventHandler<javafx.event.ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            Sounds.playButtonClick(); //Plays button click sound
            if (currentSelection == (Button)event.getSource()) { //creates color response to being clicked
                currentSelection.setStyle(defaultButtonStyle);
                if (selectionType.equals("topping")) { //if it is a topping being removed from selection
                    ((Customer)SunDevilPizza.session.getUser()).getCurrentOrder().getPizza().removeTopping(optionLabel.getText()); //removing the topping from the order
                    ((PizzaBuilderUI)currentSelection.getScene().getRoot()).refreshOrderSummary(); //reloading order summary
                }
                currentSelection = null;
            }
            else {
                currentSelection = (Button)event.getSource(); //reading the button inputs
                for (int i = 0; i < buttonList.size(); i++) {
                    buttonList.get(i).setStyle(defaultButtonStyle); //setting button styles to default one by one
                }
                currentSelection.setStyle("-fx-text-fill: black; -fx-background-color: #90ee90");
                if (selectionType.equalsIgnoreCase("topping")) { //selecting a topping
                    ((Customer)SunDevilPizza.session.getUser()).getCurrentOrder().getPizza().removeTopping(optionLabel.getText()); //removing a topping if too many are selected
                    ((Customer)SunDevilPizza.session.getUser()).getCurrentOrder().getPizza().addTopping(optionLabel.getText() + " (" + ((Button)event.getSource()).getText() + ")"); //adding the new topping
                    ((PizzaBuilderUI)currentSelection.getScene().getRoot()).refreshOrderSummary();
                }
                if (selectionType.equalsIgnoreCase("time")) { //slecting when to pick up the order if time is clicked
                    ((Customer)SunDevilPizza.session.getUser()).getCurrentOrder().setPickupTime(((Button)event.getSource()).getText());
                    ((PizzaBuilderUI)currentSelection.getScene().getRoot()).refreshOrderSummary();
                }
                if (selectionType.equalsIgnoreCase("status")) { //displaying status of the order if status is clicked
                    ((EmployeePortalUI)currentSelection.getScene().getRoot()).updateQueue(optionLabel.getText(), ((Button)event.getSource()).getText(), (SelectionBar)((Button)event.getSource()).getParent());
                }
            }
        }
    }
}
