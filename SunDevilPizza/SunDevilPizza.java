import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class SunDevilPizza extends Application { //Launches the main application
    public static final String resourcesPath = "./resources/"; //defines resources directory
    public static final String customerFilesPath = "./customerFiles/"; //defines area to save customer files
    static final int width = 1920, height = 1080; //Initializes the width and height for the app window
    private static List<Parent> rootNodes = new ArrayList<>(); //creates an arraylist for rootnodes
    private static int currentRootIndex = -1;
    private static Scene scene; //initialize a new scene
    public static Session session = new Session(); //create a new default sesssion
    
    @Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(new WelcomeUI(width, height), width, height); //Generates a new GUI scene
        newRoot(scene.getRoot()); //sets a new root that is derived from the root of the new generated scene
        stage.setTitle("SunDevil Pizza"); //Sets the window title
        stage.setScene(scene); //Sets the window scene
        //try { //Custom cursor, icons, and CSS
        try {
            Image icon = new Image(new FileInputStream(SunDevilPizza.resourcesPath + "sdpLogoIcon.png")); //pulls the sun devil pizza logo from the resources folder and sets the icon image as it
            Image cursor = new Image(new FileInputStream(SunDevilPizza.resourcesPath + "cutter.png"));//pulls the pizza cutter image from the resource folder and sets the cursor as the pizza cutter
            stage.getIcons().add(icon); //sets the icon on the stage
            scene.setCursor(new ImageCursor(cursor)); //initalizes new cursor
            scene.getStylesheets().add("sdp.css"); //uses the custom css to create a style for the scene
        }
        catch (Exception e) {
            System.out.println(e); //if an exception is caught print in the console log
        }
        stage.setFullScreen(true); //Sets app to fullscreen
        stage.show(); //Shows the window
    }
    
    //Main
    public static void main(String[] args) {
        launch(args);
    }
    
    //Sets new Root node
    public static void newRoot(Parent node) {        
        scene.setRoot(node);
        rootNodes.add(scene.getRoot());
        currentRootIndex++;
    }
    
    //Sets Root to previous node
    public static void previousRoot() {
        if (currentRootIndex == 1) { //if the root index is 1, go to node 0 which is home
            home();
        }
        else {
            scene.setRoot(rootNodes.get(currentRootIndex - 1));
            currentRootIndex--; //tracks what node of the page the user is on by moving one node back and updating the nodelist
        }   
    }
    
    //Sets Root to next node
    public static boolean nextRoot() {
        if (rootNodes.size() > currentRootIndex + 1) { //checks if the next node can exist
            scene.setRoot(rootNodes.get(currentRootIndex + 1)); //sets the next root
            currentRootIndex++; //updates the root index
            return true; //returns true if the root was set to the next node
        }
        return false; //returns false if the root is the last node
    }
    
    //Returns app to welcome screen
    public static void home() {
        for (int i = rootNodes.size() - 1; i > 0; i--) { //removes all of the nodes in the list iteratively
            rootNodes.remove(i);
        }
        currentRootIndex = 0; //resets the root node index to home
        scene.setRoot(rootNodes.get(0)); //serts the new empty root to home
    }
}    
