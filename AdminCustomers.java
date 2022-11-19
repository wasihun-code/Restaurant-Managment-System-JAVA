import java.util.ArrayList;
import java.util.HashMap;

public abstract class AdminCustomers {
    // An item is represented as a HashMap with itemNumber, \
    // itemName, itemPrice, itemQuantity as keys
    HashMap<String, Object> orderItem = new HashMap<String, Object>();

    // An order is represented as an ArrayList of HashMaps(items representation)
    ArrayList<HashMap<String, Object>> orderMenu = new ArrayList<HashMap<String, Object>>();

    // A customers order is represented as an ArrayList of HashMaps(items) created
    // from orderMenu
    ArrayList<HashMap<String, Object>> customersList = new ArrayList<HashMap<String, Object>>();

    public ArrayList<HashMap<String, Object>> createInitialOrderMenu() {
        orderItem = new HashMap<String, Object>() {
            {
                put("itemNumber", 1);
                put("itemName", "Burger");
                put("itemPrice", 100);
                put("itemQuantity", 1);
            }
        };
        orderMenu.add(orderItem);
        orderItem = new HashMap<String, Object>() {
            {
                put("itemNumber", 2);
                put("itemName", "Pizza");
                put("itemPrice", 200);
                put("itemQuantity", 1);
            }
        };
        orderMenu.add(orderItem);
        orderItem = new HashMap<String, Object>() {
            {
                put("itemNumber", 3);
                put("itemName", "Sandwich");
                put("itemPrice", 50);
                put("itemQuantity", 1);
            }
        };
        orderMenu.add(orderItem);
        orderItem = new HashMap<String, Object>() {
            {
                put("itemNumber", 4);
                put("itemName", "Fries");
                put("itemPrice", 30);
                put("itemQuantity", 1);
            }
        };
        orderMenu.add(orderItem);
        return orderMenu;
    }

    public void displayMenu() {
        if (orderMenu.isEmpty()) {
            System.out.println("\t \t \t \t =>Order Menu is empty");
            return;
        }
        System.out.println("\t \t \t \t Number \t Food \t Price");
        for (HashMap<String, Object> orderMenuItem : orderMenu) {
            System.out.print("\t \t \t \t \t" + orderMenuItem.get("itemNumber") + " \t  ");
            System.out.print(orderMenuItem.get("itemName") + "\t");
            System.out.print(orderMenuItem.get("itemPrice"));
            System.out.println();
        }
    }

}
