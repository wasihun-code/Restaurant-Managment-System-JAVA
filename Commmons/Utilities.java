package Commmons;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Utilities {
    static Scanner sc = new Scanner(System.in);

    // Create Strings for the database connection
    public static final String url = "jdbc:mysql://localhost:3306/restaurant";
    public static final String uname = "root";
    public static final String pass = "RMS.java";

    // Colors
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_PINK = "\u001B[35m";
    public static final String ANSI_INDIGO = "\u001B[34m";
    public static final String ANSI_GREY = "\u001B[37m";
    public static final String ANSI_VIOLET = "\u001B[35m";

    // Method to clear the console screen
    public static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int validateAndReturnUserInput() {

        int choice;
        try {
            // Read user input
            System.out.print(ANSI_VIOLET + "\n\t\t\t   => Enter your choice: " + ANSI_RESET);
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline left-over
            return choice;
        } catch (NoSuchElementException ex){
            // Handling ctrl + d Exception exit the program with exit code 0 and display a message
            System.out.println(ANSI_RED + "\n\t\t\t\t => Thank you for using our service" + ANSI_RESET);
            System.exit(1);
            return -1234;
        } catch (Exception e) {
            e.printStackTrace();
            sc.nextLine(); // Consume newline left-over
            Utilities.clearScreen();
            System.out.print(Utilities.ANSI_RED + "\t\t\t\t => Invalid Choice" +
                    Utilities.ANSI_RESET);
            return -1234;
        }
    }
}
