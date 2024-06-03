
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class TestingUI extends Pane {
    private List<Button> buttonList;
    
    TestingUI (int width, int height) {
        setWidth(width); //Sets this pane width
        setHeight(height); //Sets this pane height
        buttonList = new ArrayList<>(); // creating an arraylist of buttons
        for (int i = 0; i < 6; i++) { // for six buttons
            Button button = new Button(String.valueOf(i + 1)); // create a new button unique button starting at 1
            button.setPrefSize(60, 60); // sets size of the new button
            buttonList.add(button); // adds the new button to the list
            buttonList.get(i).relocate((i * 60), 60); // places new button 60 px lower than the last button
            this.getChildren().add(buttonList.get(i)); // adds the button
        }
    }
}
