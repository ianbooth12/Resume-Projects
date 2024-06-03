import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

//Class builds Large selection bars for use throughout program
public class LargeSelectionBar extends Pane {
    //Declaring Variables...
    private Label optionLabel;
    private List<Button> buttonList;
    private String selectionType;
    private Button currentSelection;
    private final String defaultButtonStyle = "-fx-text-fill: black; -fx-background-color: lightgrey;";
    
    //Constructor
    LargeSelectionBar(String selectionType, String labelText, int numOfButtons, String[] buttonText) {
        setWidth(600); //Sets this pane width
        setHeight(400); //Sets this pane height
        this.selectionType = selectionType;
        optionLabel = new Label(labelText); //Sets label text from labelText string in method parameters
        optionLabel.setFont(new Font("Arial", 28)); //Sets label text size and font
        optionLabel.relocate(5, 5); //Relocates label 
        buttonList = new ArrayList<>(numOfButtons); //Creates a new list ArrayList of buttons
        getChildren().add(optionLabel); //Adds created label to pane
        for (int i = 0; i < numOfButtons; i++) { //Looping for number of buttons that selection bar will have
            buttonList.add(new Button()); //Adds new button to the list
            buttonList.get(i).setText(buttonText[i]); //Sets text of current indexed button
            buttonList.get(i).setPrefSize(220, 80); //Set's current indexed button's preffered size to (220, 80)
            buttonList.get(i).relocate((i * 260), 60); //Relocate button 
            buttonList.get(i).setStyle(defaultButtonStyle); //Sets style of new button to have black text and lightgrey background
            buttonList.get(i).setFont(new Font("Arial", 28)); //Set's button text font and size
            buttonList.get(i).setOnAction(new ControlsHandler()); //Sets up control handler for button
            getChildren().add(buttonList.get(i)); //Adding current button to list 
        }
    }
    
    //Handler for all UI controls...
    private class ControlsHandler implements EventHandler<javafx.event.ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            Sounds.playButtonClick(); //Calls sound class to play audio when a button is clicked
            if (currentSelection == (Button)event.getSource()) { //Checks if button has already been toggled
                currentSelection.setStyle(defaultButtonStyle); //Sets button color to the default light grey
                if (selectionType.equals("size")) { //Checking if button labeled "size" was clicked
                    ((Customer)SunDevilPizza.session.getUser()).getCurrentOrder().getPizza().setSize(null);
                    ((PizzaBuilderUI)currentSelection.getScene().getRoot()).refreshOrderSummary(); //Updates pizza being currently built to show changes
                }
                else if (selectionType.equals("type")) { //Checking if button labeled "type" was clicked
                    ((Customer)SunDevilPizza.session.getUser()).getCurrentOrder().getPizza().setType(null);
                    ((PizzaBuilderUI)currentSelection.getScene().getRoot()).refreshOrderSummary(); //Updates pizza being currently built to show changes
                }
                currentSelection = null; //Setting button clikced to null to work as a toggle 
            }
            else {
                currentSelection = (Button)event.getSource(); //Checks if button has not been toggled
                if (selectionType.equals("size")) { //Checking if button labeled "size" was clicked
                    ((Customer)SunDevilPizza.session.getUser()).getCurrentOrder().getPizza().setSize(((Button)event.getSource()).getText());
                    ((PizzaBuilderUI)currentSelection.getScene().getRoot()).refreshOrderSummary(); //Updates pizza being currently built to show changes
                }
                else if (selectionType.equals("type")) { //Checking if button labeled "type" was clicked
                    ((Customer)SunDevilPizza.session.getUser()).getCurrentOrder().getPizza().setType(((Button)event.getSource()).getText());
                    ((PizzaBuilderUI)currentSelection.getScene().getRoot()).refreshOrderSummary(); //Updates pizza being currently built to show changes
                }
                for (int i = 0; i < buttonList.size(); i++) { //Looping for size of button list
                    buttonList.get(i).setStyle(defaultButtonStyle); //Setting style of each button in button list to have black text and a lightgrey background
                }
                currentSelection.setStyle("-fx-text-fill: black; -fx-background-color: #90ee90"); //Toggling button to change background to light green
            }
        }
    }
    
} //End of LargeSelection class
