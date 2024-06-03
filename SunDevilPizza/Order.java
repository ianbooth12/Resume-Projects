
import java.io.Serializable;


//Order containing a pizza, order # and total amount
public class Order implements Serializable {
    private static final long serialVersionUID = 42069L;
    
    //Declaring Variables...
    private Pizza pizza;
    private String status;
    private String orderNumber;
    private String pickupTime;
    private String emailAddress;
    
    //Order default constructor
    Order() {}
    
    //Order constructor with a pizza passed in
    Order(Pizza pizza) {
        this.pizza = pizza;
    }
    
    //Getter method for pizza
    public Pizza getPizza() {
        return pizza;
    }
    
    //Setter method for pizza pickup time
    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }
    
    //Getter method for pizza pickup time
    public String getPickupTime() {
        return pickupTime;
    }
    
    //Getter method for pizza status
    public String getStatus() {
        return status;
    }
    
    //Getter method for pizza order number
    public String getOrderNumber() {
        return orderNumber;
    }
    
    //Setter method for pizza status 
    public void setStatus(String status) {
        this.status = status;
    }
    
    //Getter method for customer email
    public String getEmail() {
        return emailAddress;
    }
    
    //Setter method for customer email
    public void setEmail(String email) {
        this.emailAddress = email;
    }
    
    //Setter method for pizza order number
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
