import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class LoginUI extends Pane {
    //Declaring Variables...
    private String type;
    private Label headerLabel;
    private TextField userNameField;
    private PasswordField passwordField;
    private Button signInButton;
    private Button customerHubButton;    
    private Button signOutButton;
    private Label loginFailedLabel;
    private Button backButton;
    private Hyperlink employeeSignInLink;
    private int posy = 320;
    
    //Constructor
    LoginUI(String type, int width, int height) {
        this.type = type;
        this.setWidth(width);
        this.setHeight(height);


        //Username textfield attributes...
        userNameField = new TextField(); //Creates new textfield for username
        userNameField.setPrefSize(320, 40); //Sets textfield preferred size to (320, 40)
        userNameField.layoutXProperty().bind(this.widthProperty().subtract(userNameField.getPrefWidth()).divide(2)); //Sets textfield's X centering value
        userNameField.layoutYProperty().set(posy + 60); //Slightely lowers textfield
        userNameField.setPromptText(type.toUpperCase() + " ID"); //Sets prompt text for textfield to show type string from method paramters and "ID" before user types in it
        userNameField.setStyle("-fx-background-color: lightgrey;"); //Sets textfield background to be light grey
        
        
        //Password field attributes...
        passwordField = new PasswordField(); //Creates new textfield for password
        passwordField.setPrefSize(320, 40); //Sets textfield preferred size to (320, 40)
        passwordField.layoutXProperty().bind(this.widthProperty().subtract(userNameField.getPrefWidth()).divide(2)); //Sets textfield's X centering value
        passwordField.layoutYProperty().set(posy + 120); //Slightely lowers textfield
        passwordField.setPromptText("Password"); //Sets prompt text for textfield to show "Password" before user types in it
        passwordField.setStyle("-fx-background-color: lightgrey;"); //Sets textfield background to be light grey
        
        
        //Header label attributes...
        headerLabel = new Label(type.toUpperCase() + " Login:"); //Creates new label with type string from mconstructor parameters and " Login" text
        headerLabel.layoutXProperty().bind(this.widthProperty().subtract(userNameField.getPrefWidth()).divide(2)); //Sets label's centering X value
        headerLabel.layoutYProperty().set(posy); //Slightely lowers label
        headerLabel.setFont(new Font("Arial", 24)); //Sets label's text font and size
        
        
        //Sign-in button attributes...
        signInButton = new Button("Sign In"); //Creates new button with text "Sign In"
        signInButton.setStyle("-fx-text-fill: black; -fx-background-color: lightgrey;"); //Sets button's text color to black and background to light grey
        signInButton.setPrefSize(120, 40); //Sets button's preffered size to (120, 40)
        signInButton.layoutXProperty().bind(this.widthProperty().subtract(signInButton.getPrefWidth()).divide(2)); //Sets button's X centering value
        signInButton.layoutYProperty().set(posy + 180); //Slightely lowers button
        signInButton.setOnAction(new LoginControlsHandler()); //Sets up sign in button's control handler for when sign in button is clicked
        
        
        //Customer hub button attributes...
        customerHubButton = new Button("Profile"); //Creates new button with text "Profile"
        customerHubButton.setStyle("-fx-text-fill: black; -fx-background-color: lightgrey;"); //Sets button's text color to black and background to light grey
        customerHubButton.setPrefSize(120, 40); //Sets button's preffered size to (120, 40)
        customerHubButton.layoutXProperty().bind(this.widthProperty().subtract(signInButton.getPrefWidth()).divide(2)); //Sets buton's X centering value
        customerHubButton.layoutYProperty().set(posy + 140); //Slightely lowers button
        customerHubButton.setVisible(false); //Sets button to be invisible initially 
        customerHubButton.setOnAction(new LoginControlsHandler()); //Sets up sign in button's control handler for when sign in button is clicked
        
        
        //Sign-out button attributes...
        signOutButton = new Button("Sign Out"); //Creates new button with text "Sign Out"
        signOutButton.setStyle("-fx-text-fill: black; -fx-background-color: lightgrey;"); //Sets button's text color to black and background to light grey
        signOutButton.setPrefSize(120, 40); //Sets button's preffered size to (120, 40)
        signOutButton.layoutXProperty().bind(this.widthProperty().subtract(signInButton.getPrefWidth()).divide(2)); //Sets buton's X centering value
        signOutButton.layoutYProperty().set(posy + 200); //Slightely lowers button
        signOutButton.setVisible(false); //Sets button to be invisible initially
        signOutButton.setOnAction(new LoginControlsHandler()); //Sets up sign in button's control handler for when sign in button is clicked
        
        
        //Employee sign-in hyperlink attributes...
        employeeSignInLink = new Hyperlink("Employee Login →"); //Sets up new hyperlink for employee users to navigate to their login window
        employeeSignInLink.setPrefWidth(120); //Sets hyperlink's preffered width to 120
        employeeSignInLink.layoutXProperty().bind(this.widthProperty().subtract(employeeSignInLink.getPrefWidth()).divide(2)); //Sets hyperlinks X centering value
        employeeSignInLink.layoutYProperty().set(posy + 240); //Slightely lowers hyperlink 
        employeeSignInLink.setOnAction(new LoginControlsHandler()); //Sets up employee login hyperlink's control handler for when it is clicked
        if (type.equalsIgnoreCase("employee")) { //Checks if the user's session type is an employee
            employeeSignInLink.setVisible(false); //If user type is not an employee then employee sign in hyperlink is made invisible 
        }
        
        
        //Error label attributes...
        loginFailedLabel = new Label("Login Denied"); //Creates label with text "Login Denied" for when user encounters an error when logging in
        loginFailedLabel.setStyle("-fx-text-fill: red;"); //Set's new label text to be red
        loginFailedLabel.layoutXProperty().bind(this.widthProperty().subtract(loginFailedLabel.getPrefWidth()).divide(2)); //Sets label's X centering value
        loginFailedLabel.layoutYProperty().set(posy + 300); //Slighely lowers label
        loginFailedLabel.setVisible(false); //Sets label to be invisible initially
        
        
        //Back button attributes...
        backButton = new ButtonMaker("back"); //Creates new button with text "back"
        backButton.setOnAction(new LoginControlsHandler()); //Sets up back button's control handler for when it is clicked 
        
        
        //USER ALREADY SIGNED IN
        if (SunDevilPizza.session.getUser() != null && !((Customer)SunDevilPizza.session.getUser()).getIDNum().equals("-1")) {
            headerLabel.setVisible(false); //"Login" Header label is made invisible
            userNameField.setVisible(false); //Username textfield is made invisible
            passwordField.setVisible(false); //Password textfield is made invisible
            signInButton.setVisible(false); //"Sign In" button is made invisible
            employeeSignInLink.setVisible(false); //Employee login hyperlink is made invisible
            signOutButton.setVisible(true); //"Sign Out" button is made visible 
            customerHubButton.setVisible(true); //Customer hub button is made visible
        }
        
        //Add everything to pane...
        this.getChildren().addAll(headerLabel, userNameField, passwordField, signInButton, customerHubButton, signOutButton, employeeSignInLink, loginFailedLabel, backButton);
    }
    
    //Handler for all UI controls...
    private class LoginControlsHandler implements EventHandler<javafx.event.ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) {
            Sounds.playButtonClick(); //Calls Sounds class to play audio when pane is clicked
            if (event.getSource() == signInButton) { //Checks if "Sign In" button is clicked
                if (type.equalsIgnoreCase("asurite")) { //Checks if type from constructor parameter list is a customer's ASURITEID
                    //Calls CredentialVerification class to verify customer credentials
                    Customer customer = CredentialVerification.customerLoginCheck(type, userNameField.getText(), passwordField.getText());
                    if (customer != null) {//Checking if customer login credentials were verified
                        SunDevilPizza.session.setUser(customer); //Setting current session user to be a customer
                         //Sending customer user to CustomerPortalUI pane
                        SunDevilPizza.newRoot(new CustomerPortalUI(SunDevilPizza.width, SunDevilPizza.height));
                    }
                    else {
                            //"Login Denied" red label becomes visible so user knows there was an error while trying to login
                            loginFailedLabel.setVisible(true);
                    }
                }
                else {
                    //Calls CredentialVerification class to verify employee credentials
                    Employee employee = CredentialVerification.employeeLoginCheck(type, userNameField.getText(), passwordField.getText());
                    if (employee != null) { //Checking if employee login credentials were verified 
                        SunDevilPizza.session.setUser(employee); //Setting current session user to be an employee
                        //Checking if current employee type is an order processing agent
                        if (((Employee)SunDevilPizza.session.getUser()).getRole().equalsIgnoreCase("OPA")) {
                            //Sending employee user to EmployeePortalUI pane and setting the employee's role type as Order Processing Agent for the class
                            SunDevilPizza.newRoot(new EmployeePortalUI(SunDevilPizza.width, SunDevilPizza.height, "Order Processing Agent"));
                        }
                        //Checking if current employee type is a chef
                        if (((Employee)SunDevilPizza.session.getUser()).getRole().equalsIgnoreCase("CHEF")) {
                            //Sending employee user to EmployeePortalUI pane and setting the employee's role type as Chef for the class
                            SunDevilPizza.newRoot(new EmployeePortalUI(SunDevilPizza.width, SunDevilPizza.height, "Chef"));
                        }
                    }
                    else {
                            //"Login Denied" red label becomes visible so user knows there was an error while trying to login
                            loginFailedLabel.setVisible(true);
                    }
                }
            }
            
            //Checking if "Sign Out" button was clicked
            if (event.getSource() == signOutButton) { 
                SunDevilPizza.session.setUser(null); //Setting current user's session to null
                SunDevilPizza.home(); //Sending user back to the program's main landing page
            }
            
            //Checking if customer hub button is clicked
            if (event.getSource() == customerHubButton) {
                SunDevilPizza.newRoot(new CustomerPortalUI(SunDevilPizza.width, SunDevilPizza.height)); //Sends user to customer hub pane
            }
            
            //Checking if employee login hyperlink is clicked
            if (event.getSource() == employeeSignInLink) { //Employee sign-in hyperlink actions...
                SunDevilPizza.newRoot(new LoginUI("EMPLOYEE", SunDevilPizza.width, SunDevilPizza.height)); //Sends user to employee login pane
            }
            
            //Checking if back button is clicked
            if (event.getSource() == backButton) { //Back button actions (ASURITE )...
                SunDevilPizza.previousRoot(); //Sends user back to previous pane  
            }
        }
    }
    
} //End of LoginUI class
