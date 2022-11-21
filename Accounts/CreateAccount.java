package Accounts;

// Import necessary util packages
import java.util.Formatter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Import necessary sql pacages
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import Commmons.Utilities;

public class CreateAccount {

    // Create strings to save user input
    String UserName, UserId, UserPassword, UserEmail, UserPhone, AccountType;

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
                Last name must be minimum 2 characters long and contain only letters
                There must be a space between the first and last name
                Enter your name:
                """;

        // Get verified user name from the user
        UserName = verifyUserInputREGEX(UserNamePattern, prompt);

        // Get verified user phone number from the user
        UserPhone = verifyUserInputREGEX(phonePattern, "\t Phone Number(10 digits): ");

        // Get verified user email from the user
        UserEmail = verifyUserInputREGEX(emailPattern, "\t Email: ");

        // Create string prompt to display to the user about password requirements
        prompt = """
                Minimum 6 characters, Maximum 10 characters
                At least One uppercase letter, One lowercase letter
                At least One Digit and One special characteር
                \t Enter Password:
                """;
        // Get verified user password from the user
        UserPassword = verifyUserInputREGEX(passwordPattern, prompt);
    }

    // Write UserId, phone, password, bankaccount to database
    public boolean WriteOnDatabase() {

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

        } catch (SQLException e) {

            // Display account could not be created
            System.out.println(Utilities.ANSI_RED + "\t\t\t\t => Account could not be created" + Utilities.ANSI_RESET);
            return false;
        }
        return true;
    }

    // Displaying detail after creating a new account
    public void displayAccountDetail() {

        // Create formatter object to format the user account details
        Formatter f = new Formatter();

        try {
            // Connect to the database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant",
                    "root", "RMS.java");

            // Create a statement
            Statement st = con.createStatement();

            // Create a result set and execute the statement
            var rs = st.executeQuery("SELECT * FROM accounts WHERE UserID = '" + UserId
                    + "' AND UserPassword = '" + UserPassword + "'");

            // Format the column names
            f.format("%-15s %-15s %-15s %-15s %-15s %-15s\n", "UserID", "UserName", "UserPhone",
                    "UserEmail", "UserPassword", "AccountType");

            // Loop through the result set
            while (rs.next()) {

                // Format the user account details
                f.format("%-15s %-15s %-15s %-15s %-15s %-15s", rs.getString(1),
                        rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6));

            }
            System.out.println(f);
        } catch (SQLException ex) {
            // Display error message
            System.out.println("Error: " + ex.getMessage());
        }
        // Close the formatter
        f.close();
    }

    // Use regular expression to match user inputs: phone, name and accountnumber
    public String verifyUserInputREGEX(Pattern pattern, String prompt) {
        Matcher matcher = null;
        String string;

        // Create scanner object to get user input
        Scanner in = new Scanner(System.in);

        do {

            // Clear the screen
            Utilities.clearScreen();

            // Display the prompt
            System.out.print(Utilities.ANSI_RED + "\t\t\t\t " + prompt + Utilities.ANSI_RESET);

            // Get the user input
            string = in.nextLine();

            // Create a matcher object to match the user input with the pattern
            matcher = pattern.matcher(string);

        } while (!(matcher.find())); // Loop until the user input matches the pattern

        in.close();
        return string;
    }

}