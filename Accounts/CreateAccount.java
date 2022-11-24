package Accounts;

// Import necessary util packages
import java.util.Formatter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Import necessary sql pacages
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Commmons.Utilities;

public class CreateAccount {

    // Create strings to store user details
    static String UserName = "SOMETHING", UserPassword = "13";
    static String UserEmail = "me@gmail.com", UserPhone = "1234576890", AccountType = "CUSTOMER";

    // Create an int to store user id
    static int UserId;

    public CreateAccount() {
        try (Scanner input = new Scanner(System.in)) {

            // Display create account main menu
            createAccountMainMenu();

            // Loop indefinitely until user chooses to exit
            while (true) {

                // Validate user choice
                int choice = Utilities.validateAndReturnUserInput();

                // User has chosen to input something other than an integer so ask them again
                if (choice == -1234)
                    continue;

                // Clear the screen
                Utilities.clearScreen();

                // switch case to choose account type
                switch (choice) {

                    // Admin Account
                    case 1:

                        // Prompt user to enter master password
                        System.out.print(Utilities.ANSI_VIOLET + "\t\t\t => Enter Master Password: "
                                + Utilities.ANSI_RESET);

                        // Store master password in a string
                        String masterPassword;

                        try {

                            // Get master password from user
                            masterPassword = input.nextLine();

                            // Check if master password is correct
                            if (!(masterPassword.equals("admin"))) {

                                // If Incorrect, throw an exception and exit
                                System.out.println(Utilities.ANSI_RED + "\t\t\t => Invalid Password" +
                                        Utilities.ANSI_RESET);
                            }
                        } catch (Exception e) {

                            // Catch the exception and exit
                            System.out.println(Utilities.ANSI_RED + "\t\t\t => Exiting..." +
                                    Utilities.ANSI_RESET);

                            System.exit(1);
                        }
                        AccountType = "ADMIN";
                        break;

                    // Customer Account
                    case 2:
                        AccountType = "CUSTOMER";
                        break;

                    // Exit
                    case 3:
                        System.out.println(Utilities.ANSI_RED + "\t\t\t => Exiting..." +
                                Utilities.ANSI_RESET);
                        System.exit(1);

                        // Invalid choice
                    default:
                        System.out.println(Utilities.ANSI_RED + "\t\t\t => Invalid Choice" +
                                Utilities.ANSI_RESET);
                        break;
                }
                if (choice == 1 || choice == 2)
                    break;
            }

            // If account detail is valid
            if (readAccountDetail()) {

                // If account is created on the database successfully
                if (storeAccount_DB()) {

                    // If cart is created on the database successfully
                    if (createCart_DB()) {

                        // Display the account details
                        displayAccountDetail();

                        // Display success message and exit
                        System.out.println(Utilities.ANSI_GREEN + "\n\t\t\t => Account Created Successfully" +
                                Utilities.ANSI_RESET);
                    }
                }
            }

            // If account is not created successfully
            else {

                // Display error message and exit
                System.out.println(Utilities.ANSI_GREEN + "\t\t\t => Account Creation Failed" +
                        Utilities.ANSI_RESET);
            }

            // But whatever happens, exit
            System.out.println(Utilities.ANSI_RED + "\t\t\t => Exiting..." +
                    Utilities.ANSI_RESET);
            System.exit(1);
        }

    }

    // receive new account DETAILS
    public static boolean readAccountDetail() {

        // Create the user name pattern which users have to follow
        Pattern UserNamePattern = Pattern.compile("^[a-zA-Z]{2,10} [a-zA-z]{2,}$");

        // Create the user phone number pattern which users have to follow
        Pattern phonePattern = Pattern.compile("^[0-9]{1,}$");

        // Create the user password pattern which users have to follow
        Pattern passwordPattern = Pattern.compile("^[a-zA-z0-9]{4,10}$");

        // Create the email pattern which users have to follow
        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");

        // Create string prompt to display to the user
        String prompt;
        prompt = " \t\t\t Enter your name(Fisrt and Last):";

        // Get verified user name from the user
        UserName = validateUserInput(UserNamePattern, prompt);

        // Get verified user phone number from the user
        UserPhone = validateUserInput(phonePattern, "\t Phone Number:");

        // Get verified user email from the user
        UserEmail = validateUserInput(emailPattern, "\t Email:");

        // Create string prompt to display to the user about password requirements
        prompt = "\t\t\t Enter Password(4-10 Character):";

        // Get verified user password from the user
        UserPassword = validateUserInput(passwordPattern, prompt);
        return true;
    }

    // Write username, phone, password, bankaccount to database table: account
    public static boolean storeAccount_DB() {

        // Create a connection object
        try (Connection con = DriverManager.getConnection(Utilities.url, Utilities.uname, Utilities.pass)) {
            // Create a statement
            Statement st = con.createStatement();

            // Write the user details to the database
            st.executeUpdate(
                    "INSERT INTO accounts (UserName, UserPhone, UserEmail, UserPassword, AccountType) VALUES('"
                            + UserName + "', '" + UserPhone + "', '" + UserEmail + "', '"
                            + UserPassword + "', '" + AccountType + "')");

            // Extract user id from the database where user email and password match
            ResultSet rs = st.executeQuery("SELECT UserId FROM accounts WHERE UserEmail = '"
                    + UserEmail + "' AND UserPassword = '" + UserPassword + "'");

            // Get the user id
            while (rs.next()) {
                UserId = rs.getInt("UserId");
            }
        } catch (SQLException e) {
            // Catch any SQL exception and print the error and return false
            System.out.println(Utilities.ANSI_RED + "\t\t\t => Error: " + e.getMessage() +
                    Utilities.ANSI_RESET);
            return false;
        }

        return true;
    }

    // Displaying detail after creating a new account
    public static void displayAccountDetail() {

        // Create formatter object to format the user account details
        Formatter f = new Formatter();

        // Format the column names
        f.format("%-8s %-20s %-13s %-25s %-12s\n", "ID", "Name", "Phone",
                "Email", "Password");

        // Format the user account details
        f.format("%-8s %-20s %-13s %-25s %-12s",
                CreateAccount.UserId, CreateAccount.UserName, CreateAccount.UserPhone,
                CreateAccount.UserEmail, CreateAccount.UserPassword);

        System.out.println(f);
        // Close the formatter
        f.close();
    }

    // Use regular expression to match user inputs: phone, name and accountnumber
    public static String validateUserInput(Pattern pattern, String prompt) {
        Matcher matcher = null;
        String string;

        // Create scanner object to get user input
        do {

            // Clear the screen
            Utilities.clearScreen();

            // Display the prompt
            System.out.print(Utilities.ANSI_PURPLE + "\t\t\t " + prompt + " " + Utilities.ANSI_RESET);

            // Get the user input
            string = Utilities.sc.nextLine();

            // Create a matcher object to match the user input with the pattern
            matcher = pattern.matcher(string);

        } while (!(matcher.find())); // Loop until the user input matches the pattern

        return string;
    }

    // Create a cart table for the user on the database
    public static boolean createCart_DB() {

        try (Connection con = DriverManager.getConnection(Utilities.url, Utilities.uname, Utilities.pass)) {
            // Create a statement
            Statement st = con.createStatement();

            // Create a cart table for the user
            st.executeUpdate("CREATE TABLE cart_" + UserId
                    + " (itemNumber INT(4), itemName VARCHAR(50), itemPrice INT(4), itemQuantity INT(2))");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    // Display the create account menu
    public static void createAccountMainMenu() {
        System.out.println(Utilities.ANSI_GREEN + "\n\t\t\t   => Choose Account Type: "
                + Utilities.ANSI_RESET);
        System.out.println(Utilities.ANSI_CYAN + "\t\t\t 1. ADMIN" +
                Utilities.ANSI_RESET);
        System.out.println(Utilities.ANSI_CYAN + "\t\t\t 2. CUSTOMER" +
                Utilities.ANSI_RESET);
        System.out.println(Utilities.ANSI_CYAN + "\t\t\t 3. EXIT" +
                Utilities.ANSI_RESET);
    }

}