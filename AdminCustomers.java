import java.util.ArrayList;
import java.util.HashMap;

public class AdminCustomers {
    // An item is represented as a HashMap with itemNumber, \
    // itemName, itemPrice, itemQuantity as keys
    HashMap<String, Object> item = new HashMap<String, Object>();

    // An order is represented as an ArrayList of HashMaps(items representation)
    ArrayList<HashMap<String, Object>> menu = new ArrayList<HashMap<String, Object>>();

    // A customers order is represented as an ArrayList of HashMaps(items) created
    // from menu
    ArrayList<HashMap<String, Object>> cart = new ArrayList<HashMap<String, Object>>();

    // An Admins sales is represented as an array of HashMaps(items) created from
    // user carts
    ArrayList<HashMap<String, Object>> sales = new ArrayList<HashMap<String, Object>>();

    // Constructor to initialize the menu
    public AdminCustomers() {
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
    }

    public void displayMenu() {
        if (menu.isEmpty()) {
            System.out.println("\t \t \t \t =>Order Menu is empty");
            return;
        }
        System.out.println("\t \t \t \t Number \t Food \t Price");
        for (HashMap<String, Object> menuItem : menu) {
            System.out.print("\t \t \t \t \t" + menuItem.get("itemNumber") + " \t  ");
            System.out.print(menuItem.get("itemName") + "\t");
            System.out.print(menuItem.get("itemPrice"));
            System.out.println();
        }
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
                    System.out.println("Item already exists");
                    return true;
                }
            }
        }
        return false;
    }
}