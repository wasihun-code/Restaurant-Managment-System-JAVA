package Commmons;

import java.io.IOException;
import java.util.Scanner;

public class Utilities {
    static Scanner sc = new Scanner(System.in);
    // Colors
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";

    // Method to clear the console screen
    public static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int validateUserInputTryCatch() {

        int choice;
        try {
            // Read user input
            System.out.println(ANSI_CYAN + "\t\t\t\t => Enter your choice: " + ANSI_RESET);
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline left-over
            return choice;
        } catch (Exception e) {
            System.out.print(Utilities.ANSI_RED + "\t\t\t\t => Invalid Choice" +
                    Utilities.ANSI_RESET);
            sc.nextLine(); // Consume newline left-over
            Utilities.clearScreen();
            return -1234;
        }
    }
}
