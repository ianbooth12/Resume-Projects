
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


//Pizza class containing specific pie information
public class Pizza implements Serializable {
    private static final long serialVersionUID = 42069L;
    
    //Declaring Variables...
    private String type;
    private String size;
    private List<String> toppings = new ArrayList();
    
    //Default Constructor
    Pizza() {}
    
    public void setType(String type) {
        this.type = type;
    } //setter for pizza type

    public void setSize(String size) {
        this.size = size;
    } //setter for pizza size
    
    public String getType() {
        return type;
    } //getter for pizza type
    
    public String getSize() {
        return size;
    } //getter for pizza size
    
    public List getToppings() {
        return toppings;
    } //getter for pizza toppings
    
    public void addTopping(String topping) {
        this.toppings.add(topping);
    } //adding a topping to toppings
    
    public void removeTopping(String topping) {
        for (int i = 0; i < toppings.size(); i++) { //iterating through the toppings list seeing if the topping exists
            if(toppings.get(i).contains(topping)) { //if the topping exists
                toppings.remove(i); //remove said topping
            }
        }
    }
}
