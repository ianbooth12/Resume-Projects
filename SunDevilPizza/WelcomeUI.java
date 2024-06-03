import java.io.FileInputStream;
import javafx.event.EventHandler;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WelcomeUI extends Pane {
    //Declaring Variables...
    private ImageView mainLogoBanner;
    private Button startButton;
    private Button loginButton;
    private int clickCount = 0;
    private Label counter;
    
    //Constructor
    WelcomeUI(int width, int height) {
        setWidth(width); //Sets this pane width
        setHeight(height); //Sets this pane height
        setStyle("-fx-background-color: #FFFFFF");
        counter = new Label("");
        counter.setFont(new Font("Arial", 24));
        counter.relocate(180, 920);
        try {
            Image logoImage = new Image(new FileInputStream("./resources/sdpLogo.png"));// creates an image object to be used
            mainLogoBanner = new ImageView();// creates a image view object to interact with the pane spaces
            mainLogoBanner.setImage(logoImage);// sets the banner to be the sdp logo
            mainLogoBanner.setFitWidth(logoImage.getWidth());// fits the width
            mainLogoBanner.layoutXProperty().bind(this.widthProperty().subtract(mainLogoBanner.getFitWidth()).divide(2)); // defines the horozontal properties
            mainLogoBanner.layoutYProperty().set(140);
            mainLogoBanner.setOnMouseClicked(event ->  { //Balasooriya mode easter egg
                if (clickCount < 420) {
                    clickCount++;
                }
                if (clickCount >= 69) {
                    try {
                        Image cursor = new Image(new FileInputStream(SunDevilPizza.resourcesPath + "bala.png"));//pulls the pizza cutter image from the resource folder and sets the cursor as the pizza cutter
                        this.getScene().setCursor(new ImageCursor(cursor)); //initalizes new cursor
                    }
                    catch (Exception e) {
                        System.out.println(e); //if an exception is caught print in the console log
                    }
                }
                if (clickCount == 420) {
                    try {
                        Image cursor = new Image(new FileInputStream(SunDevilPizza.resourcesPath + "cutter.png"));//pulls the pizza cutter image from the resource folder and sets the cursor as the pizza cutter
                        this.getScene().setCursor(new ImageCursor(cursor)); //initalizes new cursor
                    }
                    catch (Exception e) {
                        System.out.println(e); //if an exception is caught print in the console log
                    }
                    try {
                      Image icon = new Image(new FileInputStream(SunDevilPizza.resourcesPath + "bala.png"));
                      ((Stage)this.getScene().getWindow()).getIcons().add(icon); //sets the icon on the stage
                    }
                    catch (Exception e) {
                        System.out.println(e); //if an exception is caught print in the console log
                    }
                }
                counter.setText(String.valueOf(clickCount));
            });
        }
        catch(Exception e) {     
        }
        loginButton = new ButtonMaker("login"); // Creates a login button
        loginButton.setOnAction(new WelcomePageControlsHandler()); // when login button is clicked, trigger new login UI
        startButton = new ButtonMaker("order");// Creates an order button
        startButton.layoutXProperty().bind(this.widthProperty().subtract(startButton.widthProperty()).divide(2));// defines horizontal property dependent on the width of the buttons
        startButton.setLayoutY(560);// defines static height of the button
        startButton.setOnAction(new WelcomePageControlsHandler()); // when start button is clicked trigger new pizza builder UI
        getChildren().addAll(mainLogoBanner, loginButton, startButton, counter); // initializing the pane
    }
    
    private class WelcomePageControlsHandler implements EventHandler<javafx.event.ActionEvent> { //control handler for the welcome page
        @Override
        public void handle(javafx.event.ActionEvent event) {
            Sounds.playButtonClick(); // when button is clicked play a sound
            if (event.getSource() == loginButton) { // if login button is clicked
                SunDevilPizza.newRoot(new LoginUI("ASURITE", SunDevilPizza.width, SunDevilPizza.height)); // new LoginUI
            }
            else if (event.getSource() == startButton) { // if login button is clicked
                SunDevilPizza.newRoot(new PizzaBuilderUI(SunDevilPizza.width, SunDevilPizza.height)); // new pizza builder UI
                if (SunDevilPizza.session.getUser() == null) { // if there is no user
                    SunDevilPizza.session.setUser(new Customer("-1")); // create temp user
                    SunDevilPizza.session.getUser().setType("customer"); // setting type of the session
                }
            }
        }
    }
}
