package Creating_Account;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Account_Login.loginAbstract;
import Commmons.Utilities;
import Restaurant.Admin;
import Restaurant.Customers;

public class loginAccount extends loginAbstract {
    Scanner sc = new Scanner(System.in);

    public loginAccount(){
        int userChoice;
        boolean jumpToMain = false;
        System.out.println("\n\n");
                System.out.println(Utilities.ANSI_CYAN + "\t \t \t \t 1. Login to Admin Account" + Utilities.ANSI_RESET);
                System.out.println(Utilities.ANSI_CYAN + "\t \t \t \t 2. Login To User Account" + Utilities.ANSI_RESET);
                System.out.println(Utilities.ANSI_CYAN + "\t \t \t \t 3. Jump to Main Menu" + Utilities.ANSI_RESET);
                System.out.println(Utilities.ANSI_CYAN + "\t \t \t \t 4. Exit" + Utilities.ANSI_RESET);
        
        // Utilities.clearScreen();
        // System.out.println(Utilities.ANSI_RED + "\n \t \t \t \t " + Utilities.ANSI_RESET);

        while (true) {
            
            try {
                System.out.print(Utilities.ANSI_CYAN + "\n\t\t\t\t => Enter your choice: " + Utilities.ANSI_RESET);
                userChoice = sc.nextInt();
                sc.nextLine(); // To consume the newline character
            } catch (Exception e) {
                Utilities.clearScreen();
                System.out.println(Utilities.ANSI_RED + "\n\t\t\t\t => Invalid input" + Utilities.ANSI_RESET);
                sc.nextLine(); // To consume the newline character
                continue;
            }

            String adminPass;
            Utilities.clearScreen();

            if(userChoice==1){

                System.out.println(Utilities.ANSI_CYAN + "\t \t \t \t Enter Admin Pass Code" + Utilities.ANSI_RESET);
                adminPass=sc.nextLine();
                if(adminPass.equals("admin")){
                    System.out.println(Utilities.ANSI_CYAN + "\t \t \t \t 1. Enter Your Admin ID" + Utilities.ANSI_RESET);
                    ID=sc.nextLine();

                   
                    if( userIDAuthentication("Admin.txt", ID)){
                        System.out.println(Utilities.ANSI_CYAN + "\t \t \t \t 2. Enter Your Password:" + Utilities.ANSI_RESET);
                        password=sc.nextLine();
                        if(userPasswordAuthentication("User.txt", password)){
                            new Admin();

                        }
                    }
                    else{
                        System.out.println(Utilities.ANSI_CYAN + "\t \t \t \t Invalide user Id. Enter Correct Id" + Utilities.ANSI_RESET);
                    }

                }
                

            }
            else if(userChoice== 2){
                System.out.println(Utilities.ANSI_CYAN + "\t \t \t \t Enter Your account Details" + Utilities.ANSI_RESET);

                System.out.println(Utilities.ANSI_CYAN + "\t \t \t \t \n\n\nEnter your ID:" + Utilities.ANSI_RESET);
                ID=sc.nextLine();
                if(userIDAuthentication("User.txt", ID)){
                    System.out.println(Utilities.ANSI_CYAN + "\t \t \t \t Enter your password" + Utilities.ANSI_RESET);
                    if(userPasswordAuthentication("User.txt", password)){
                        new Customers();
                    }
                    else{
                        System.out.println(Utilities.ANSI_CYAN + "\t \t \t \t Invalide user password. Enter Correct password" + Utilities.ANSI_RESET);
                    }

                }
                else{
                    System.out.println(Utilities.ANSI_CYAN + "\t \t \t \t Invalide user Id. Enter Correct Id" + Utilities.ANSI_RESET);
                }

            }
            
            // if user wants to go back to main menu
            if (jumpToMain) {
                break;
            }
        }
    }

    public boolean userIDAuthentication(String fileToOpen, String ID) {
        Matcher idMatcher = null;
        Pattern idPattern = Pattern.compile("^ID: ([0-9]{4})");
        return searchString(fileToOpen, idPattern, idMatcher, ID);
    }

    public boolean userPasswordAuthentication(String fileToOpen, String password) {
        Matcher passwordMatcher = null;
        Pattern passwordPattern = Pattern.compile("^Password: ([0-9]{4})");

        return searchString(fileToOpen, passwordPattern, passwordMatcher, password);
    }

    public boolean searchString(String fileToOpen, Pattern pattern,
            Matcher matcher, String toSearch) {

        // Open the file to be searched using try resource for easy exception handling
        try (BufferedReader reader = new BufferedReader(new FileReader(fileToOpen))) {
            String line = reader.readLine();

            // Scan through the end of file for the regex pattern: idPattern
            while (line != null) {
                line = reader.readLine();
                // System.out.println(line);

                if (line == null)
                    return false;
                matcher = pattern.matcher(line);

                // Check if any match is found
                if (matcher.find()) {

                    // Extract the string from the match
                    String extractedString = matcher.group(1);

                    // Compare the extracted string and the string to be seached
                    if (extractedString.equals(toSearch))
                        return true;
                }
            }
        } catch (IOException ex) {
            System.out.println("Account couldn't be found! Try again letter");
        }
        return false;
    }
}
