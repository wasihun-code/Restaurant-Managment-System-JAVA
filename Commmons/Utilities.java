package Commmons;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Utilities {
    public static Scanner sc = new Scanner(System.in);

    // Create Strings for the database connection
    public static final String url = "jdbc:mysql://localhost:3306/restaurant";
    public static final String uname = "root";
    public static final String pass = "Mysqlmikisroot1";

    // Colors
    public static final String ANSI_RESET = "\u001B[0m";
    
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_INDIGO = "\u001B[34m";
    public static final String ANSI_VIOLET = "\u001B[35m";

    // Method to clear the console screen
    public static void clearScreen() {
        try {
            delay(700);
            //display delay message in blue Colors
            System.out.println(ANSI_PURPLE + "\n\n\t\t\t => Please wait..." + ANSI_RESET);
            delay(800);
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int validateAndReturnUserInput() {

        int choice;
        try {

            // Read user input
            System.out.print(ANSI_VIOLET + "\n\t\t\t=> Enter your choice: " + ANSI_RESET);
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline left-over
            
            return choice;

            // Handle ctrl + d exception
        } catch (NoSuchElementException ex) {

            // Clear the console screen
            clearScreen();

            // Print goodbye message
            System.out.println(ANSI_RED + "\n\t\t\t => Thank you for using our service" + ANSI_RESET);

            // Exit the program
            System.exit(1);
            return -12341;

        } catch (Exception e) {

            // Clear the console screen
            clearScreen();

            // Print error message
            e.printStackTrace();

            // Consume the newline left-over
            sc.nextLine(); 

            // Return -1234 to indicate an error
            System.out.print(Utilities.ANSI_RED + "\t\t\t => Invalid Choice" +
                    Utilities.ANSI_RESET);
            return -1234;
        }
    }
    //method to add time delay in Displaying the output
    public static void delay(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
