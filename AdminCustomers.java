import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Scanner;

public class AdminCustomers {
    // An item is represented as a HashMap with itemNumber, \
    // itemName, itemPrice, itemQuantity as keys
     public static HashMap<String, Object> item;

    // An order is represented as an ArrayList of HashMaps(items representation)
     public static ArrayList<HashMap<String, Object>> menu = new ArrayList<HashMap<String, Object>>();

    // A customers order is represented as an ArrayList of HashMaps(items) created
    // from menu
     public static ArrayList<HashMap<String, Object>> cart = new ArrayList<HashMap<String, Object>>();

    // An Admins sales is represented as an array of HashMaps(items) created from
    // user carts
     public static ArrayList<HashMap<String, Object>> sales = new ArrayList<HashMap<String, Object>>();

    // Scanner object to take input from the user
    Scanner sc = new Scanner(System.in);

    // Constructor to initialize the menu
    public AdminCustomers() {
        try (BufferedReader in = new BufferedReader(new FileReader("ETH.txt"))) {
            String line;
            boolean duplicateFound = false;
            while ((line = in.readLine()) != null) {
                String[] parts = line.split(",");

                int itemNumber = Integer.parseInt(parts[0]);
                for (HashMap<String, Object> menuItem : menu) {
                    System.out.println("ItemNumber: " +menuItem.get("itemNumber"));
                    if (menuItem.containsValue(itemNumber)) {;
                        System.out.println("Item Already Added");
                        duplicateFound = true;
                        break;
                    }
                }
                if (duplicateFound){
                    duplicateFound = false;
                    continue;
                }
                item = new HashMap<>() {
                    {
                        put("itemNumber", Integer.parseInt(parts[0]));
                        put("itemName", parts[1]);
                        put("itemPrice", Integer.parseInt(parts[2]));
                    }
                };
                menu.add(item);
            }
            System.out.println(menu);
        } catch (IOException e) {
            e.printStackTrace();
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

    public void displayMenu() {
        if (menu.isEmpty()) {
            System.out.println("\t \t \t \t =>Order Menu is empty");
            return;
        }

        Formatter f = new Formatter();
        f.format("%15s %15s %15s %15s\n", "", "Number",
                "Item Name", "Item Price", "Item Quantity");

        for (HashMap<String, Object> item1 : menu) {
            f.format("%14s %14s %18s %10s\n", "", item1.get("itemNumber"),
                    item1.get("itemName"), item1.get("itemPrice"));
        }
        System.out.println(f);
    }

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

    public void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}