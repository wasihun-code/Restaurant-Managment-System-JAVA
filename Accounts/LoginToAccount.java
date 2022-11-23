package Accounts;

// Import necessary sql packages
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


// Import project packages
import Commmons.Utilities;
import Restaurant.Admin;
import Restaurant.Customer;

public class LoginToAccount {

    // Store the current active user's id
    public static String UserId;

    public LoginToAccount() {
        // Create Strings to store user input
        String password;

            // Get username and password from user
            System.out.print(Utilities.ANSI_VIOLET + "\n\t\t\t => Enter User ID: " + Utilities.ANSI_RESET);
            UserId = Utilities.sc.nextLine();

            // Exit the program if user enters incorrect password
            if (!authenticateId_From_DB(UserId)) {
                System.out.println(Utilities.ANSI_RED + "\n\t\t\t => Invalid Username" + Utilities.ANSI_RESET);
                System.out.println(Utilities.ANSI_RED + "\t\t\t => Exiting..." + Utilities.ANSI_RESET);
                System.exit(1);
            }

            // Get password from user
            System.out.print(Utilities.ANSI_VIOLET + "\t\t\t => Enter Password: " + Utilities.ANSI_RESET);
            password = Utilities.sc.nextLine();

            // Exit the program if user enters incorrect password
            if (!authenticatePassword_DB(UserId, password)) {
                System.out.println(Utilities.ANSI_RED + "\n\t\t\t => Invalid Password" + Utilities.ANSI_RESET);
                System.out.println(Utilities.ANSI_RED + "\t\t\t => Exiting..." + Utilities.ANSI_RESET);
                System.exit(1);
            }

            // Check if user is an admin or customer
            if (isAdmin(UserId)) {
                new Admin();
            } else {
                new Customer();
            }
    }

    public static boolean authenticateId_From_DB(String UserId) {

        // Connect to the database and check if the user id exists
        try (Connection con = DriverManager.getConnection(Utilities.url, Utilities.uname, Utilities.pass)) {

            // Create a statement to execute the query
            Statement st = con.createStatement();

            // Store the query result to result set
            ResultSet rs = st.executeQuery("select * from accounts where UserId = '" + UserId + "'");

            // Extract the user id from the result set
            while (rs.next()) {
                String useridstored = rs.getString("UserId");

                // If the user id exists, return true
                if (useridstored.equals(UserId)) {
                    return true;
                }
            }
        } catch (

        SQLException e) {
            // Display account not found message
            System.out.println("Account not found");
            return false;
        }
        return false;
    }

    public static boolean authenticatePassword_DB(String UserId, String UserPassword) {

        try (Connection con = DriverManager.getConnection(Utilities.url, Utilities.uname, Utilities.pass)) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select UserPassword from accounts where UserId = '" + UserId + "'");

            // Extract the user password from the result set
            while (rs.next()) {
                String userpasswordstored = rs.getString("UserPassword");

                // If the user password exists, return true
                if (userpasswordstored.equals(UserPassword)) {
                    return true;
                }
            }

        } catch (SQLException e) {

            // Display account not found message
            System.out.println("Wrong password");
            return false;
        }
        return false;
    }

    public void loginOptions() {
        // Display the main menu
        System.out.println("\n\n");
        System.out.println(Utilities.ANSI_CYAN + "\t \t \t \t 1. ADMIN" + Utilities.ANSI_RESET);
        System.out.println(Utilities.ANSI_CYAN + "\t \t \t \t 2. CUSTOMER" + Utilities.ANSI_RESET);
        System.out.println(Utilities.ANSI_CYAN + "\t \t \t \t 3. Exit" + Utilities.ANSI_RESET);
    }

    public boolean isAdmin(String usersid) {

        try (Connection con = DriverManager.getConnection(Utilities.url, Utilities.uname, Utilities.pass)) {

            // Create statement 
            String query_is_admin = "SELECT AccountType FROM accounts WHERE UserID = ?";

            // Create a prepared statement
            PreparedStatement pstmt = con.prepareStatement(query_is_admin);
            pstmt.setString(1, usersid);

            // Execute the statement and extract the admin's type
            ResultSet rs = pstmt.executeQuery();

            // Extract the user id from the result set
            while (rs.next()) {
                String accounttype = rs.getString("AccountType");

                // If the user id exists, return true
                if (accounttype.equals("ADMIN")) {
                    System.out.println("Dude is admin");
                    return true;
                }
            }
            return false;

        } catch(Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
