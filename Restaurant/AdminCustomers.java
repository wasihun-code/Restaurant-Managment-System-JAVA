package Restaurant;

// Import necessary packages
import java.io.BufferedReader;
import java.io.FileReader;
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
     * MENU SHOULD BE INITIALIZED FROM THE DATABASE NOT FROM A FILE
     */
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
                    if (menuItem.containsValue(itemNumber)) {

                        // then set duplicateFound to true and break out of the loop
                        duplicateFound = true;
                        break;
                    }
                }

                // item is already present in the menu so skip adding it to menu again
                if (duplicateFound) {

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
        f.format(Utilities.ANSI_PURPLE + "%15s %15s %15s %15s\n" +
                Utilities.ANSI_RESET, "", "Number", "Item Name", "Item Price");

        f.format(Utilities.ANSI_GREEN + "%15s %15s %15s %15s\n"
                + Utilities.ANSI_RESET, "",
                "---------", "-------------", "------------");
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

}