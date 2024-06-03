
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


//Customer class containing orders and customer info...
public class Customer extends User implements Serializable {
    private static final long serialVersionUID = 42069L;
    
    //Declaring Variables...
    private String asuriteIDNum;
    private List<Order> orderHistory;
    private Order currentOrder;
    private String password;
    
    //Default Constructor
    Customer() {}
    
    //Building Customer object from relevant information
    Customer(String asuriteIDNum) {
        super("CUSTOMER");
        this.asuriteIDNum = asuriteIDNum; //Sets new object's customer ASURITEID number
        this.currentOrder = new Order(new Pizza()); //Sets current order using new customized pizza
        this.orderHistory = new ArrayList<>(); //Creates new arrayList for customer object's order history
    }
    
    //Getter method for returning ASURITEID number
    public String getIDNum() {
        return asuriteIDNum;
    }
    
    //Setter method for setting ASURITEID number
    public void setIDNum(String asuriteIDNum) {
        this.asuriteIDNum = asuriteIDNum;
    }
    
    //Setter method for adding current order to customer's order history list
    public void addOrder(Order order) {
        orderHistory.add(order);
    }
    
    //Gettter method for returning customer's current order
    public Order getCurrentOrder() {
        return currentOrder;
    }
    
    //Setter method for setting current order's pizza type, toppings, and size from customization
    public void resetCurrentOrder() {
        currentOrder = new Order(new Pizza());
    }
    
    //Getter method for returning password
    public String getPassword() {
        return password;
    }
    
    //Setter method for setting password
    public void setPassword(String password) {
        this.password = password;
    }
    
    //Getter method for returning customer order history list
    public List<Order> getOrderHistory() {
        return orderHistory;
    }
    
} //End of Customer class
