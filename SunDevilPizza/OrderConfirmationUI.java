
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;


public class OrderConfirmationUI extends Pane {
    //Declaring Variables...
    private Label headerLabel;
    private Label subLabel;
    private Button confirmationButton;
    
    //Constructor
    OrderConfirmationUI(int width, int height, String orderNumber) {
        setWidth(width); //Sets this pane width
        setHeight(height); //Sets this pane height
        setStyle("-fx-background-color: #FFFFFF"); //setting background
        headerLabel = new Label("Your order is complete!"); //creating label if the order is complete
        headerLabel.layoutXProperty().bind(this.widthProperty().subtract(headerLabel.widthProperty()).divide(2));
        headerLabel.setLayoutY(360);
        headerLabel.setFont(new Font("Arial", 40));
        subLabel = new Label ("Order confirmation #" + orderNumber); //creating label to confirm it is the correct order number
        subLabel.layoutXProperty().bind(this.widthProperty().subtract(subLabel.widthProperty()).divide(2));
        subLabel.setLayoutY(420);
        subLabel.setFont(new Font("Arial", 24));
        confirmationButton = new Button("Order Status"); //creating a button that asks for order status
        confirmationButton.setPrefSize(160, 60);
        confirmationButton.setStyle("-fx-text-fill: black; -fx-background-color: lightgrey;");
        confirmationButton.layoutXProperty().bind(this.widthProperty().subtract(confirmationButton.widthProperty()).divide(2));
        confirmationButton.setLayoutY(480);
        confirmationButton.setOnAction(new OrderConfirmationControlsHandler());
        getChildren().addAll(headerLabel, subLabel, confirmationButton); //adding all
    }
    
    private class OrderConfirmationControlsHandler implements EventHandler<javafx.event.ActionEvent> {
        @Override
        public void handle(javafx.event.ActionEvent event) { //when a button is clicked make a button click sound
            Sounds.playButtonClick();
            if (event.getSource() == confirmationButton) { //display order confirmation if the confirmation button is hit
                SunDevilPizza.newRoot(new CustomerPortalUI(SunDevilPizza.width, SunDevilPizza.height));
            }
        }
    }
}
