package Main;

// Import necessary project packages
import Accounts.CreateAccount;
import Accounts.LoginToAccount;
import Commmons.Utilities;

public class RMS {
    public static void main(String[] args) {

        // Clear the screen
        Utilities.clearScreen();

        // Loop indefinitely until user chooses to exit
        while (true) {

            // Display the main menu
            System.out.println("\n\n");
            System.out.println(Utilities.ANSI_CYAN + "\t \t \t \t 1. LOGIN" + Utilities.ANSI_RESET);
            System.out.println(Utilities.ANSI_CYAN + "\t \t \t \t 2. CREATE ACCOUNT" + Utilities.ANSI_RESET);
            System.out.println(Utilities.ANSI_CYAN + "\t \t \t \t 3. EXIT" + Utilities.ANSI_RESET);

            // Validate User input using -catch
            int choice = Utilities.validateAndReturnUserInput();

            if (choice == -1234) {
                continue;
            }

           
            // Clear the screen everytime user makes a choice
            Utilities.clearScreen();

            // Switch case to handle user choice
            switch (choice) {

                // Login
                case 1:
                    new LoginToAccount();
                    break;

                // Create
                case 2:
                    new CreateAccount();
                    break;

                // Exit
                case 3:
                    // Display a goodbye message to the user
                    System.out.println(Utilities.ANSI_RED + "\n\t\t\t => Thank you for using our service"
                            + Utilities.ANSI_RESET);
                    // Exit the program if user chooses to exit
                    System.exit(0);
                    break; 

                // Invalid choice
                default:
                    // Display an error message if user enters a number other than 1, 2 or 3
                    System.out.println(Utilities.ANSI_RED + "\n\t\t\t => Invalid choice" + Utilities.ANSI_RESET);
                    break;
            }
        }
    }
}
