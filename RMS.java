import java.util.Scanner;


public class RMS extends Utilities{
    public static void main(String[] args) {
        AdminCustomers.clearScreen();
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n\n");
                System.out.println(ANSI_CYAN + "\t \t \t \t 1. Admin Login" + ANSI_RESET);
                System.out.println(ANSI_CYAN + "\t \t \t \t 2. Customer Login" + ANSI_RESET);
                System.out.println(ANSI_CYAN + "\t \t \t \t 3. Exit" + ANSI_RESET);

                int choice;
                // Validate User input using try-catch
                try { 
                    System.out.print(ANSI_CYAN + "\n\t\t\t\t =>Enter your choice: " + ANSI_RESET);
                    choice = sc.nextInt();
                    sc.nextLine(); // To consume the newline character
                } catch (Exception e) {
                    AdminCustomers.clearScreen();
                    System.out.println(ANSI_RED + "\n\t\t\t\t =>Invalid Input" + ANSI_RESET);
                    sc.nextLine(); // To consume the newline character
                    continue;
                }

                AdminCustomers.clearScreen();
                switch (choice) {
                    case 1:
                        new Admin();
                        break;
                    case 2:
                        new Customers();
                        break;
                    case 3:
                        System.out.println(ANSI_RED + "\n\t\t\t\t =>Thank you for using our service" + ANSI_RESET);
                        System.exit(0);
                        break;
                    default:
                        System.out.println(ANSI_RED + "I\n\t\t\t\t =>nvalid choice" + ANSI_RESET);
                        break;
                }
            }
        }
    }
}