import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MobilePhoneContactList {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/mydatabase";

    //  Database credentials
    static final String USER = "username";
    static final String PASS = "password";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> contacts = new ArrayList<String>();
        
        // Connect to the database
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected to database successfully...");
            stmt = conn.createStatement();
        } catch (Exception e) {
            System.out.println("Error in connecting to database: " + e.getMessage());
        }
        
        // Load contacts from the database
        try {
            String sql = "SELECT name, phone_number FROM contacts";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("name");
                String phone = rs.getString("phone_number");
                contacts.add(name + " " + phone);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error in loading contacts from database: " + e.getMessage());
        }
        
        // Display the contacts
        System.out.println("Contacts:");
        for (String contact : contacts) {
            System.out.println(contact);
        }
        
        // Prompt the user to add, remove, or modify a contact
        while (true) {
            System.out.println("Enter 'add' to add a new contact, 'remove' to remove a contact, 'modify' to modify a contact, or 'exit' to quit:");
            String command = scanner.nextLine();
            
            // Add a new contact
            if (command.equals("add")) {
                System.out.print("Enter the name of the new contact: ");
                String name = scanner.nextLine();
                System.out.print("Enter the phone number of the new contact: ");
                String phone = scanner.nextLine();
                try {
                    String sql = "INSERT INTO contacts (name, phone_number) VALUES (?, ?)";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, name);
                    ps.setString(2, phone);
                    ps.executeUpdate();
                    contacts.add(name + " " + phone);
                    System.out.println("Contact added successfully.");
                } catch (Exception e) {
                    System.out.println("Error in adding contact to database: " + e.getMessage());
                }
            
            // Remove a contact
            } else if (command.equals("remove")) {
                System.out.print("Enter the name of the contact to remove: ");
                String name = scanner.nextLine();
                try {
                    String sql = "DELETE FROM contacts WHERE name=?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, name);
                    ps.executeUpdate();
                    contacts.removeIf(contact -> contact.startsWith(name + " "));
                    System.out.println("Contact removed successfully.");
                } catch (Exception e) {
                    System.out.println("Error in removing contact from database: " + e.getMessage());
                }
            
            // Modify a contact
            } else if (command.equals("modify")) {
                System.out.print("Enter the name of the contact to modify: ");
                String name = scanner.nextLine();
                System.out.print("Enter the new phone number of the contact");
                
                	
                scanner.close();
                
            }
        }
    }
}