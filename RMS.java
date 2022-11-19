import java.io.IOException;
import java.util.Scanner;

public class RMS {
    // Method: clear the screen for improved look and feel
    public static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        clearScreen();
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n\n");
                System.out.println("\t \t \t \t 1. Admin Login");
                System.out.println("\t \t \t \t 2. Customer Login");
                System.out.println("\t \t \t \t 3. Exit");

                int choice;
                // Validate User input using try-catch
                try { 
                    System.out.print("\n\t\t\t\t =>Enter your choice: ");
                    choice = sc.nextInt();
                    sc.nextLine(); // To consume the newline character
                } catch (Exception e) {
                    clearScreen();
                    System.out.println("\n\t\t\t\t =>Invalid Input");
                    sc.nextLine(); // To consume the newline character
                    continue;
                }

                clearScreen();
                switch (choice) {
                    case 1:
                        new Admin();
                        break;
                    case 2:
                        new Customers();
                        break;
                    case 3:
                        System.out.println("\n\t\t\t\t =>Thank you for using our service");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("I\n\t\t\t\t =>nvalid choice");
                        break;
                }
            }
        }
    }
}