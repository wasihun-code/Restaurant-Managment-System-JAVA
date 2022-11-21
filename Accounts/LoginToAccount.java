package Accounts;

// Import necessary sql packages
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Import necessary packages
import java.util.Scanner;

import Commmons.Utilities;
import Restaurant.Admin;
import Restaurant.Customer;

public class LoginToAccount {

    public LoginToAccount() {
        // Create Strings to store user input
        String userid, password;

        // Create a scanner object to read user input and close it when done
        Scanner sc = new Scanner(System.in);

        // Get username and password from user
        System.out.print("\n\t\t\t\t => Enter your username: ");
        userid = sc.nextLine();

        // Authenticate user id
        if (authenticateId(userid)) {

            // Get password from user
            System.out.print("\n\t\t\t\t => Enter your password: ");
            password = sc.nextLine();

            // Authenticate password
            if (authenticatePassword(userid, password)) {

                while (true) {

                    // Display the main menu
                    System.out.println("\n\n");
                    System.out.println(Utilities.ANSI_CYAN + "\t \t \t \t 1. ADMIN" + Utilities.ANSI_RESET);
                    System.out.println(Utilities.ANSI_CYAN + "\t \t \t \t 2. CUSTOMER" + Utilities.ANSI_RESET);
                    System.out.println(Utilities.ANSI_CYAN + "\t \t \t \t 3. MAIN MENU" + Utilities.ANSI_RESET);
                    System.out.println(Utilities.ANSI_CYAN + "\t \t \t \t 3. Exit" + Utilities.ANSI_RESET);

                    // Delare variable to store user choice
                    int choice;
                    boolean goBackToMainMenu = false;

                    // Validate User input using -catch
                    try {
                        System.out.print(
                                Utilities.ANSI_CYAN + "\n\t\t\t\t => Enter your choice: " + Utilities.ANSI_RESET);
                        choice = sc.nextInt();
                        sc.nextLine(); // To consume the newline character
                    } catch (Exception e) {
                        Utilities.clearScreen();
                        System.out.println(Utilities.ANSI_RED + "\n\t\t\t\t => Invalid Input" + Utilities.ANSI_RESET);
                        sc.nextLine(); // To consume the newline character
                        continue;
                    }

                    // Clear the screen everytime user makes a choice
                    Utilities.clearScreen();

                    // Switch case to handle user choice
                    switch (choice) {

                        // Login
                        case 1:
                            new Admin();
                            break;

                        // Create
                        case 2:
                            new Customer();
                            break;

                        // Go back to main menu
                        case 3:
                            goBackToMainMenu = true;
                            break;

                        case 4:
                            // Display a goodbye message to the user
                            System.out.println(Utilities.ANSI_RED + "\n\t\t\t\t => Thank you for using our service"
                                    + Utilities.ANSI_RESET);
                            // Exit the program if user chooses to exit
                            System.exit(0);
                            break;

                        // Invalid choice
                        default:
                            // Display an error message if user enters a number other than 1, 2 or 3
                            System.out.println(
                                    Utilities.ANSI_RED + "\n\t\t\t\t => Invalid choice" + Utilities.ANSI_RESET);
                            break;
                    }

                    // Go back to main menu
                    if (goBackToMainMenu) {
                        break;
                    }
                }
            }
        }
    }

    public static boolean authenticateId(String UserId) {
        // Create strings to store database user name and password
        final String uname = "root";
        final String pass = "RMS.java";
        final String url = "jdbc:mysql://localhost:3306/restaurant";

        // Connect to the database and check if the user id exists
        try {
            Connection con = DriverManager.getConnection(url, uname, pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from accounts where UserId = '" + UserId + "'");

            // Extract the user id from the result set
            while (rs.next()) {
                String useridstored = rs.getString("UserId");

                // If the user id exists, return true
                if (useridstored.equals(UserId)) {
                    System.out.println("User ID exists");
                    return true;
                }
            }
        } catch (SQLException e) {
            // Display account not found message
            System.out.println("Account not found");
            return false;
        }
        return true;
    }

    public static boolean authenticatePassword(String UserId, String UserPassword) {
        final String uname = "root";
        final String pass = "RMS.java";
        final String url = "jdbc:mysql://localhost:3306/restaurant";

        try {
            Connection con = DriverManager.getConnection(url, uname, pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select UserPassword from accounts where UserId = '" + UserId + "'");

            // Extract the user password from the result set
            while (rs.next()) {
                String userpasswordstored = rs.getString("UserPassword");

                // If the user password exists, return true
                if (userpasswordstored.equals(UserPassword)) {
                    System.out.println("Login Successful");
                    return true;
                }
            }

        } catch (SQLException e) {

            // Display account not found message
            System.out.println("Wrong password");
            return false;
        }
        return true;
    }
}
