
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

//class to manage file assets
public class FileManager {

    public static void saveCustomer(Customer customer) { //saving the customer locally to the administrator's computer
        try { //customer class contains order information too
            FileOutputStream fileout = new FileOutputStream(SunDevilPizza.customerFilesPath + customer.getIDNum() + ".dat"); //creating a new data file to hold output for the specific customer
            ObjectOutputStream streamOut = new ObjectOutputStream(fileout); //creating a new object data stream
            streamOut.writeObject(customer); //writing customer data to the output
            streamOut.close();
            fileout.close(); //closing stream
        } catch(NotSerializableException n) { //printing exceptions to console log
            System.out.println("Not serializable exception\n");
        } catch(IOException e) {
            System.out.println("Data file read exception\n");
        }
    }
    
    public static Customer loadCustomer(String asuriteID) { //loading customers from the data file
        try {
            FileInputStream fileIn = new FileInputStream(SunDevilPizza.customerFilesPath + asuriteID + ".dat"); //reading data file for the customer if it exists
            ObjectInputStream in = new ObjectInputStream(fileIn); //creating a data stream
            Customer customer = (Customer)in.readObject();//reading the file to see if the customer is present
            return customer;
        } catch(ClassNotFoundException c) { //if the customer class is not found
           System.out.println("Class not found\n");    
        } catch (FileNotFoundException f) { //if the specific customer does not exist
            System.out.println("No customer save exists\n");
        } catch(NotSerializableException n) { //not serializable or data exception
            System.out.println("Not serializable exception\n");
        } catch(IOException e) {
           System.out.println("Data file read exception\n");
        }
        return null;
    }
    
    public static boolean existingCustomer(String asuriteID) { //if the customer is already existing
        File f = new File(SunDevilPizza.customerFilesPath + asuriteID + ".dat"); //creating a new file for the customer
        return f.exists(); //return file exists
    }
}
