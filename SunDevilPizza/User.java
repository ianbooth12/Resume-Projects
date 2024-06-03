
public class User {
    private String type;
    
    //Default Constructor
    User() {
        type = null;
    }
    
    //Constructor
    User(String type) {
        this.type = type;
    }
    
    //Gets user type
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
}
