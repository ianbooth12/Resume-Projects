import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


//Class sets the images for each button in program
public class ButtonMaker extends Button {
    //Basic Button Maker
    ButtonMaker(String type) {
       if (type.equalsIgnoreCase("login"))  { //Checking if new button be created is labeled "login"
            try {
                setGraphic(new ImageView(new Image(new FileInputStream(SunDevilPizza.resourcesPath + "login.png")))); //Sets login button icon
            }
            catch(FileNotFoundException e) { //Exception catch for when login button icon can't be found
            }   
            relocate(1700, 900); //Relocates login.png icon
        }
        else if (type.equalsIgnoreCase("order"))  { //Checking if new button be created is labeled "order"
            try {
                setGraphic(new ImageView(new Image(new FileInputStream(SunDevilPizza.resourcesPath + "orderButton.png"))));
            }
            catch(FileNotFoundException e) { //Exception catch for when order Button icon can't be found 
            }
        }
        else if (type.equalsIgnoreCase("back")) { //Checking if new button be created is labeled "back"
            try {
                setGraphic(new ImageView(new Image(new FileInputStream(SunDevilPizza.resourcesPath + "backarrow.png"))));
            }
            catch(FileNotFoundException e) { //Exception catch for when back arrow icon can't be found
            }
            relocate(1640, 900); //Relocates backarrow.png icon
        }
        else if (type.equalsIgnoreCase("forward")) { //Checking if new button be created is labeled "forward"
            try {
                setGraphic(new ImageView(new Image(new FileInputStream(SunDevilPizza.resourcesPath + "forwardarrow.png"))));
            }
            catch(FileNotFoundException e) { //Exception catch for when forward arrow icon can't be found
            }
            relocate(1740, 900); //Relocates forwardarrow.png icon
        }
        else if (type.equalsIgnoreCase("home")) { //Checking if new button be created is labeled "home"
            try {
                setGraphic(new ImageView(new Image(new FileInputStream(SunDevilPizza.resourcesPath + "homeIcon.png"))));
            }
            catch(FileNotFoundException e) { //Exception catch for when home icon can't be found
            }
            relocate(1740, 900); //Relocates homeIcon.png icon
        }
    }
} //End of ButtonMaker class
