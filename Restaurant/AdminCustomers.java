package Restaurant;

// Import necessary packages
import java.io.*;
import java.io.FileReader;
import java.io.FileWriter;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Scanner;
import Commmons.Utilities;

public class AdminCustomers {
    // Item representation {"itemNumber"=1, "itemName"="Burger", "itemPrice"=100}
    public static HashMap<String, Object> item;

    // An order is represented as an ArrayList of items
    public static ArrayList<HashMap<String, Object>> menu = new ArrayList<HashMap<String, Object>>();

    // A customers cart is represented as an ArrayList of items chosen
    public static ArrayList<HashMap<String, Object>> cart = new ArrayList<HashMap<String, Object>>();

    // An Admins sales is represented as an ArrayList of items sold
    public static ArrayList<HashMap<String, Object>> sales = new ArrayList<HashMap<String, Object>>();

    // Scanner object to take input from the user
    Scanner sc = new Scanner(System.in);

   /*
    * After the user chose countryName, Initialize the constructor to read the menu form file
    */
    
        public void readCountryFile(String countryName){
            try {
                // Create a file reader
                FileReader fileReader = new FileReader(countryName + ".txt");
    
                // Create a buffered reader
                BufferedReader bufferedReader = new BufferedReader(fileReader);
    
                // Read the file line by line
                String line;
                //split the line from file by comma
                String[] splitLine;
                while ((line = bufferedReader.readLine()) != null) {
                    splitLine = line.split(",");
                    //add the split line to the menu
                    addItemToMenu(splitLine[0], splitLine[1], Double.parseDouble(splitLine[2]));
                }
                // while ((line = bufferedReader.readLine()) != null) {
                //     // System.out.println(line);
                // }
    
                // Close the file
                bufferedReader.close();
            } catch (IOException e) {
                System.out.println("Error: " + "Menu does not exist.");
            }
    
        }

    // Method to display the menu in a tabular format
    public void displayMenu() {
        if (menu.isEmpty()) {
            System.out.println("\t \t \t \t => Order Menu is empty");
            return;
        }

        // Create a table to display the menu
        Formatter f = new Formatter();

        f.format(Utilities.ANSI_GREEN + "%15s %15s %15s %15s\n"
                + Utilities.ANSI_RESET, "", "", "ORDER MENU", "");

        f.format(Utilities.ANSI_GREEN + "%15s %15s %15s %15s\n"
                + Utilities.ANSI_RESET, "", "---------",
                "-------------", "------------");
        // format the table header
        // f.format(Utilities.ANSI_PURPLE + "%15s %15s %15s %15s\n" +
        //         Utilities.ANSI_RESET, "" );

        // f.format(Utilities.ANSI_GREEN + "%15s %15s %15s %15s\n"
        //         + Utilities.ANSI_RESET, "",
        //         "---------", "-------------", "------------");
        // loop through the menu
        for (HashMap<String, Object> item1 : menu) {
            // format the table body
            f.format(Utilities.ANSI_CYAN + "%14s %14s %15s %10s\n" + Utilities.ANSI_RESET, "", item1.get("itemNumber"),
                    item1.get("itemName"), item1.get("itemPrice"));
            f.format(Utilities.ANSI_GREEN + "%15s %15s %15s %15s\n" + Utilities.ANSI_RESET, "",
                    "---------", "-------------", "------------");
        }
        
        

        // display the table menu
        // f.format(Utilities.ANSI_GREEN + "%15s %15s %15s %15s\n" +
        // Utilities.ANSI_RESET, "",
        // "-----------------", "-----------------", "------------");
        System.out.println(Utilities.ANSI_GREEN + f + Utilities.ANSI_RESET);
        f.close();
    }
    

    // Method to check if an item is already present in the menu
    public boolean itemExists(String itemName) {
        // if menu have a list of items
        if (!menu.isEmpty()) {

            // Loop through the menu items
            for (HashMap<String, Object> menuItem : menu) {

                // Convert the item name to capital case(to be identical with menu items case)
                String capitalCaseName = itemName.substring(0, 1).toUpperCase();
                capitalCaseName += itemName.substring(1).toLowerCase();

                // if item is present in the menu then return
                if (menuItem.containsValue(capitalCaseName)) {
                    System.out.println("\n\t\t\t\t => Item already exists");
                    return true;
                }
            }
        }
        return false;
    }

    //function for country list and food displayMenu
    public AdminCustomers(){
        while(true){
        //blue Colors
        System.out.println(Utilities.ANSI_BLUE + "Choose Country" + Utilities.ANSI_RESET);
        //green colors
        System.out.println(Utilities.ANSI_GREEN + "1. Ethiopia" + Utilities.ANSI_RESET);
        //yellow colors
        System.out.println(Utilities.ANSI_YELLOW + "2. India" + Utilities.ANSI_RESET);
        //red colors
        System.out.println(Utilities.ANSI_RED + "3. Syria" + Utilities.ANSI_RESET);
        //cyan colors
        System.out.println(Utilities.ANSI_CYAN + "4. Myanmar" + Utilities.ANSI_RESET);
        //pink colors
        System.out.println(Utilities.ANSI_PURPLE + "5. Jump to main" + Utilities.ANSI_RESET);
        //purple colors
        System.out.println(Utilities.ANSI_PURPLE + "6. Exit" + Utilities.ANSI_RESET);

        int choice;
        boolean goBackToMainMenu = false;

        // Validate user input with try-catch
        try {
            System.out.print(Utilities.ANSI_CYAN + "\n\t\t\t\t =>Enter your choice: " + Utilities.ANSI_RESET);
            choice = sc.nextInt();
            sc.nextLine(); // To consume the newline character
        } catch (Exception e) {
            Utilities.clearScreen();
            System.out.println(Utilities.ANSI_RED + "\n\t\t\t\t =>Invalid Input. Please Try again" + Utilities.ANSI_RESET);
            sc.nextLine(); // To consume the newline character
            continue;
        }

        Utilities.clearScreen();
            switch (choice) {
                case 1:
                    readCountryFile("Ethiopia");
                    break;
                case 2:
                   readCountryFile("India");
                    break;
                case 3:
                   readCountryFile("Syria");
                    break;
                case 4:
                   readCountryFile("Myanmar");
                    break;
                case 5:
                    goBackToMainMenu = true;
                    break;
                case 6:
                    System.out.println(Utilities.ANSI_RED + "\n\t\t\t\t => Thank you for using our service" + Utilities.ANSI_RESET);
                    System.exit(0);
                    break;
                default:
                    System.out.println(Utilities.ANSI_RED + "\n\t\t\t\t => Invalid choice" + Utilities.ANSI_RESET);
                    break;
            }
            // if user wants to go back to main menu
            if (goBackToMainMenu) {
                break;
            }

        }
    }

    //read text file to display different country foods
    // public void readCountryFile(String countryName){
    //     try {
    //         // Create a file reader
    //         FileReader fileReader = new FileReader(countryName + ".txt");

    //         // Create a buffered reader
    //         BufferedReader bufferedReader = new BufferedReader(fileReader);

    //         // Read the file line by line
    //         String line;
    //         //split the line from file by comma
    //         String[] splitLine;
    //         while ((line = bufferedReader.readLine()) != null) {
    //             splitLine = line.split(",");
    //             //add the split line to the menu
    //             addItemToMenu(splitLine[0], splitLine[1], Double.parseDouble(splitLine[2]));
    //         }
    //         while ((line = bufferedReader.readLine()) != null) {
    //             // System.out.println(line);
    //         }

    //         // Close the file
    //         bufferedReader.close();
    //     } catch (IOException e) {
    //         System.out.println("Error: " + "Menu does not exist.");
    //     }
    // }
    //Method to add item from file to Menu
    public void addItemToMenu(String itemNumber, String itemName, double itemPrice) {
        // Create a new item
        HashMap<String, Object> item = new HashMap<String, Object>();
        item.put("itemNumber", itemNumber);
        item.put("itemName", itemName);
        item.put("itemPrice", itemPrice);

        // Add the item to the menu
        menu.add(item);
    }
   
    //Method to add a new menu to file
    public void addNewMenuToFile(String fileName) {
        try {
            // Create a file writer
            FileWriter fileWriter = new FileWriter(fileName + ".txt");

            // Create a buffered writer
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Write the menu to the file
            for (HashMap<String, Object> item : menu) {
                bufferedWriter.write(item.get("itemNumber") + "," + item.get("itemName") + "," + item.get("itemPrice"));
                bufferedWriter.newLine();
            }

            // Close the file
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error: " + "Menu does not exist.");
        }

    }


}