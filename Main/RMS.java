package Main;
import java.util.Scanner;
import Commmons.Utilities;
import Restaurant.Admin;
import Restaurant.Customers;

public class RMS {
    public static void main(String[] args) {
        Utilities.clearScreen();
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n\n");
                System.out.println(Utilities.ANSI_CYAN + "\t \t \t \t 1. Admin Login" + Utilities.ANSI_RESET);
                System.out.println(Utilities.ANSI_CYAN + "\t \t \t \t 2. Customer Login" + Utilities.ANSI_RESET);
                System.out.println(Utilities.ANSI_CYAN + "\t \t \t \t 3. Exit" + Utilities.ANSI_RESET);

                int choice;
                // Validate User input using -catch
                try { 
                    System.out.print(Utilities.ANSI_CYAN + "\n\t\t\t\t =>Enter your choice: " + Utilities.ANSI_RESET);
                    choice = sc.nextInt();
                    sc.nextLine(); // To consume the newline character
                } catch (Exception e) {
                    Utilities.clearScreen();
                    System.out.println(Utilities.ANSI_RED + "\n\t\t\t\t =>Invalid Input" + Utilities.ANSI_RESET);
                    sc.nextLine(); // To consume the newline character
                    continue;
                }

                Utilities.clearScreen();
                switch (choice) {
                    case 1:
                        new Admin();
                        break;
                    case 2:
                        new Customers();
                        break;
                    case 3:
                        System.out.println(Utilities.ANSI_RED + "\n\t\t\t\t =>Thank you for using our service" + Utilities.ANSI_RESET);
                        System.exit(0);
                        break;
                    default:
                        System.out.println(Utilities.ANSI_RED + "I\n\t\t\t\t =>nvalid choice" + Utilities.ANSI_RESET);
                        break;
                }
            }
        }
    }
}