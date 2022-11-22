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
    // Scanner object to read user input
    static Scanner sc = new Scanner(System.in);

    // Create strings to save user input
    static String UserName, UserPassword, UserEmail, UserPhone, AccountType;

    static int UserId;

    public CreateAccount() {
        try (Scanner input = new Scanner(System.in)) {

            // Display create account main menu
            System.out.println(Utilities.ANSI_RED + "\n\t\t\t   => Choose Account Type: "
                    + Utilities.ANSI_RESET);
            System.out.println(Utilities.ANSI_CYAN + "\t\t\t\t 1. ADMIN" +
                    Utilities.ANSI_RESET);
            System.out.println(Utilities.ANSI_CYAN + "\t\t\t\t 2. CUSTOMER" +
                    Utilities.ANSI_RESET);
            System.out.println(Utilities.ANSI_CYAN + "\t\t\t\t 3. EXIT" +
                    Utilities.ANSI_RESET);

            int choice = 0;

            // Loop indefinitely until user chooses to exit
            while (true) {
                // Get user choice
                System.out.print(Utilities.ANSI_CYAN + "\t\t\t\t => Enter your choice: " +
                        Utilities.ANSI_RESET);

                // Validate user input using try-catch
                try {
                    choice = input.nextInt();
                    input.nextLine(); // Consume newline left-over
                } catch (Exception e) {
                    System.out.println(Utilities.ANSI_RED + "\t\t\t\t => Invalid Choice" +
                            Utilities.ANSI_RESET);
                    System.out.println(Utilities.ANSI_RED + "\t\t\t\t => Exiting..." +
                            Utilities.ANSI_RESET);
                    input.nextLine(); // Consume newline left-over
                    continue;
                }

                // switch case to choose account type
                switch (choice) {

                    // Admin Account
                    case 1:
                        // Enter Master password to create admin account
                        System.out.print(Utilities.ANSI_CYAN + "\t\t\t\t => Enter Master Password: "
                                + Utilities.ANSI_RESET);
                        String masterPassword;

                        try {
                            masterPassword = input.nextLine();
                            if (masterPassword == "RMC.java") {
                                System.out.println(Utilities.ANSI_RED + "\t\t\t\t => Invalid Password" +
                                        Utilities.ANSI_RESET);
                                System.out.println(Utilities.ANSI_RED + "\t\t\t\t => Exiting..." +
                                        Utilities.ANSI_RESET);
                                System.exit(1);
                            }
                        } catch (Exception e) {
                            System.out.println(Utilities.ANSI_RED + "\t\t\t\t => Exiting..." +
                                    Utilities.ANSI_RESET);
                            System.exit(1);
                        }
                        AccountType = "A";
                        break;

                    // Customer Account
                    case 2:
                        AccountType = "U";
                        break;

                    // Exit
                    case 3:
                        System.out.println(Utilities.ANSI_RED + "\t\t\t\t => Exiting..." +
                                Utilities.ANSI_RESET);
                        System.exit(1);

                        // Invalid choice
                    default:
                        System.out.println(Utilities.ANSI_RED + "\t\t\t\t => Invalid Choice" +
                                Utilities.ANSI_RESET);
                        break;
                }
                if (choice == 1 || choice == 2)
                    break;
            }
            readAccountDetail();
            WriteOnDatabase();
            displayAccountDetail();
            System.out.println(Utilities.ANSI_RED + "\t\t\t\t => Exiting..." +
                    Utilities.ANSI_RESET);
            System.exit(1);
        }

    }

    // receive new account DETAILS
    public void readAccountDetail() {

        // Create the user name pattern which users have to follow
        Pattern UserNamePattern = Pattern.compile("^[a-zA-Z]{2,10} [a-zA-z]{2,}$");

        // Create the user phone number pattern which users have to follow
        Pattern phonePattern = Pattern.compile("^[0-9]{10}$");

        // Create the user password pattern which users have to follow
        Pattern passwordPattern = Pattern.compile(
                "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{6,10}$");

        // Create the email pattern which users have to follow
        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");

        // Create string prompt to display to the user
        String prompt;
        prompt = """
                First name must be minimum 2 characters long and contain only letters
                \t\t\t\t Last name must be minimum 2 characters long and contain only letters
                \t\t\t\t There must be a space between the first and last name
                \t\t\t\t Enter your name:""";

        // Get verified user name from the user
        UserName = verifyUserInputREGEX(UserNamePattern, prompt);

        // Get verified user phone number from the user
        UserPhone = verifyUserInputREGEX(phonePattern, "\t Phone Number(10 digits):");

        // Get verified user email from the user
        UserEmail = verifyUserInputREGEX(emailPattern, "\t Email:");

        // Create string prompt to display to the user about password requirements
        prompt = """
                Minimum 6 characters, Maximum 10 characters
                \t\t\t\t At least One uppercase letter, One lowercase letter
                \t\t\t\t At least One Digit and One special characteር
                \t\t\t\t Enter Password:""";

        // Get verified user password from the user
        UserPassword = verifyUserInputREGEX(passwordPattern, prompt);
    }

    // Write UserId, phone, password, bankaccount to database
    public static boolean WriteOnDatabase() {

        // Create strings for the database connection
        String uname = "root";
        String pass = "RMS.java";
        String url = "jdbc:mysql://localhost:3306/restaurant";

        try {
            // Connect to the database
            Connection con = DriverManager.getConnection(url, uname, pass);

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

            // Display account could not be created
            System.out.println("Error: " + e.getMessage());
            System.out.println(Utilities.ANSI_RED + "\t\t\t\t => Account could not be created" + Utilities.ANSI_RESET);
            return false;
        }
        return true;
    }

    // Displaying detail after creating a new account
    public void displayAccountDetail() {

        // Create formatter object to format the user account details
        Formatter f = new Formatter();

        // Format the column names
        f.format("%-8s %-20s %-13s %-25s %-12s\n", "ID", "Name", "Phone",
                "Email", "Password");

        // Loop through the result set

        // Format the user account details
        f.format("%-8s %-20s %-13s %-25s %-12s",
                CreateAccount.UserId, CreateAccount.UserName, CreateAccount.UserPhone,
                CreateAccount.UserEmail, CreateAccount.UserPassword);

        System.out.println(f);
        // Close the formatter
        f.close();
    }

    // Use regular expression to match user inputs: phone, name and accountnumber
    public static String verifyUserInputREGEX(Pattern pattern, String prompt) {
        Matcher matcher = null;
        String string;

        // Create scanner object to get user input
        do {

            // Clear the screen
            Utilities.clearScreen();

            // Display the prompt
            System.out.print(Utilities.ANSI_PURPLE + "\t\t\t\t " + prompt + " " + Utilities.ANSI_RESET);

            // Get the user input
            string = sc.nextLine();

            // Create a matcher object to match the user input with the pattern
            matcher = pattern.matcher(string);

        } while (!(matcher.find())); // Loop until the user input matches the pattern

        return string;
    }

}