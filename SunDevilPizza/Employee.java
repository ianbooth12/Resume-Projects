
//Class with getter and setter methods for employee role
public class Employee extends User {
    private String role; //Role can be either Chef or Order Processing Agent
    
    //Default Constructor
    Employee(String role) {
        super("EMPLOYEE");
        this.role = role; //Updating employee role from string in paratemeter list
    }
    
    //Getter method for returning employee role
    public String getRole() {
        return role;
    }
    
} //End of Employee class
