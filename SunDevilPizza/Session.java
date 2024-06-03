
import java.util.Random;
import java.util.UUID;

public class Session {
    private User user; //creates a user
    private final UUID sessionID = UUID.randomUUID(); //creates a random session id
    Session() {
        user = null;
    } //sets the user as null by default

    public User getUser() {
        return user;
    } //user getter
    
    public void setUser(User user) {
        this.user = user;
    } //user setter
    
    public UUID getSessionID() {
        return sessionID;
    } //session ID getter

    public String generateOrderNumber() { //generate a unique order number
        Random rand = new Random();
        int num = rand.nextInt(999999); //creating a new random number that will serve as the order number
        String orderNumber = String.format("%06d", num); //converts the order number to a string filling the blanks with zeros up to 6 digits
        orderNumber = orderNumber.substring(0, 2) + "-" + orderNumber.substring(2); //creates a delimiter between the zeros and the order number
        return orderNumber; //returns the order number
    }
}

