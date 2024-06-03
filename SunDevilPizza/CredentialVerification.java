
//Class verifies if login attempt is made by either an order processing agent, chef employee, or a customer
public class CredentialVerification {
    private static final String[] dummyOPACredentials = new String[] {"opa", "opa"}; //Credentials veriable for "Order Processing Agent"
    private static final String[] dummyChefCredentials = new String[] {"chef", "chef"}; //Credentials variable for "chef"
    
    //Method for checking if login attempt is made by a customer 
    public static Customer customerLoginCheck(String loginType, String userName, String password) {
        Customer customerSave = FileManager.loadCustomer(userName); //Loading customer account from username login input
        if (customerSave != null) { //Checking if customer account exists 
            if (userName.equals(customerSave.getIDNum()) && password.equals(customerSave.getPassword())) { //Checking if customer login information is valid for the account
                return customerSave; //Returns customer account information if account exists 
            }
        }
        return null; //Returns null if customer account does not exist
    }
    
    //Method for checking if login attempt is made by an order processing agent or chef employee 
    public static Employee employeeLoginCheck(String loginType, String userName, String password) {
        if (loginType.equalsIgnoreCase("employee")) { //Checking if login type was an employee
            if (userName.equals(dummyOPACredentials[0]) && password.equals(dummyOPACredentials[1])) { //Checking if username and password are associated with a "opa" employee
                return new Employee("OPA"); //Returns that employee was verified as an Order Processing Agent
            }
            else if (userName.equals(dummyChefCredentials[0]) && password.equals(dummyChefCredentials[1])) { //Checking if username and password are associated with a "chef" employee
                return new Employee("CHEF"); //Returns that employee was verified as a chef
            }
        }
        return null; //Returns null if employee login was invalid 
    }
    
    //Method for chekcing validity of ASURITEID using length of inputted value
    public static boolean isAnAsuriteID(String stringToCheck) { 
        try { 
            Integer.parseInt(stringToCheck); //Parsing ASURITEID input to check int length
            if (stringToCheck.length() == 10) { //Checking if parsed input was 10 digits long
                return true; //Returns true if ASURITEID was 10 digits long
            }
            else {
                return false; //Returns false if ASURITEID was invalid 
            }
        } 
        catch (NumberFormatException e) {  //Exception error to catch invalid inputs 
            return false; //Returns false after catching invalid inputs for failed verification 
        }
    }
} //End of CredentialVerification class
