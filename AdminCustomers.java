import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Scanner;

public class AdminCustomers {
    // Item representation {"itemNumber"=1, "itemName"="Burger", "itemPrice"=100}
    public static HashMap<String, Object> item;

    // An order is represented as an ArrayList of items
    public static ArrayList<HashMap<String, Object>> menu;

    // A customers cart is represented as an ArrayList of items chosen
    public static ArrayList<HashMap<String, Object>> cart;

    // An Admins sales is represented as an ArrayList of items sold
    public static ArrayList<HashMap<String, Object>> sales;

    // Scanner object to take input from the user
    Scanner sc = new Scanner(System.in);

    // Constructor to initialize the menu from a file
    public AdminCustomers() {
        try (BufferedReader in = new BufferedReader(new FileReader("ETH.txt"))) {
            String line;
            boolean duplicateFound = false;

            // read the file line by line
            while ((line = in.readLine()) != null) {
                // split the line into tokens: itemNumber, itemName and itemPrice
                String[] parts = line.split(",");

                // extract the itemNumber
                int itemNumber = Integer.parseInt(parts[0]);

                // Check if the itemNumber is already present in the menu
                for (HashMap<String, Object> menuItem : menu) {

                    // if the itemNumber is already present
                    if (menuItem.containsValue(itemNumber)) {;

                        // then set duplicateFound to true and break out of the loop
                        duplicateFound = true;
                        break;
                    }
                }

                // item is already present in the menu so skip adding it to menu again
                if (duplicateFound){

                    // reset duplicateFound to false
                    duplicateFound = false;
                    continue;
                }

                // item is not present in the menu so create its representation
                item = new HashMap<>() {
                    {
                        put("itemNumber", Integer.parseInt(parts[0]));
                        put("itemName", parts[1]);
                        put("itemPrice", Integer.parseInt(parts[2]));
                    }
                };

                // then add it to the menu
                menu.add(item);
            }
            System.out.println(menu);
        } catch (IOException e) {
            System.out.println("Error: " + "Menu does not exist.");
        }
     
        /*   
        item = new HashMap<String, Object>() {
            {
                put("itemNumber", 1);
                put("itemName", "Burger");
                put("itemPrice", 100);
                put("itemQuantity", 1);
            }
        };
        menu.add(item);
        item = new HashMap<String, Object>() {
            {
                put("itemNumber", 2);
                put("itemName", "Pizza");
                put("itemPrice", 200);
                put("itemQuantity", 1);
            }
        };
        menu.add(item);
        item = new HashMap<String, Object>() {
            {
                put("itemNumber", 3);
                put("itemName", "Sandwich");
                put("itemPrice", 50);
                put("itemQuantity", 1);
            }
        };
        menu.add(item);
        item = new HashMap<String, Object>() {
            {
                put("itemNumber", 4);
                put("itemName", "Fries");
                put("itemPrice", 30);
                put("itemQuantity", 1);
            }
        };
        menu.add(item);
        item = new HashMap<String, Object>() {
            {
                put("itemNumber", 5);
                put("itemName", "Pasta");
                put("itemPrice", 150);
                put("itemQuantity", 1);
            }
        };
        menu.add(item);
        */
    }

    // Method to display the menu in a tabular format
    public void displayMenu() {
        if (menu.isEmpty()) {
            System.out.println("\t \t \t \t =>Order Menu is empty");
            return;
        }

        // Create a table to display the menu
        Formatter f = new Formatter();

        // format the table header
        f.format("%15s %15s %15s %15s\n", "", "Number",
                "Item Name", "Item Price", "Item Quantity");

        // loop through the menu 
        for (HashMap<String, Object> item1 : menu) {

            // format the table body
            f.format("%14s %14s %18s %10s\n", "", item1.get("itemNumber"),
                    item1.get("itemName"), item1.get("itemPrice"));
        }

        // display the table menu
        System.out.println(f);
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
                    System.out.println("\n\t\t\t\t =>Item already exists");
                    return true;
                }
            }
        }
        return false;
    }

    // Method to clear the console screen
    public void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}