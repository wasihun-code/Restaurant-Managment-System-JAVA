import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

// 1. Place your order
// 2. View your ordered items
// 3. Delete an item from order
// 4. Display final bill
// 5. Back To Main Menu

public class Customers {
    // An item is represented as a HashMap with itemNumber, itemName, itemPrice,
    // itemQuantity as keys
    HashMap<String, Object> orderItem = new HashMap<String, Object>();

    // An order is represented as an ArrayList of HashMaps(items representation)
    ArrayList<HashMap<String, Object>> orderMenu = new ArrayList<HashMap<String, Object>>();

    // A customers order is represented as an ArrayList of HashMaps(items) created
    // from orderMenu
    ArrayList<HashMap<String, Object>> customersList = new ArrayList<HashMap<String, Object>>();

    // Scanner to read user input
    Scanner sc = new Scanner(System.in);

    /**
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    public Customers() {
        // OrderMenu should be populated from admins orderMenu
        orderMenu = createInitialOrderMenu();

        System.out.println("\t \t\t\t\t Welcome Customer");
        while (true) {
            System.out.println("\t \t \t \t 1. Place Your Order");
            System.out.println("\t \t \t \t 2. View Your Ordered Items");
            System.out.println("\t \t \t \t 3. Delete an item from order");
            System.out.println("\t \t \t \t 4. Display final bill");
            try {
                // Scanner class to read
                sc = new Scanner(System.in);
                System.out.print("\t \t \t \t \t \t Enter your choice: ");
                int choice = sc.nextInt();
                clearScreen();
                switch (choice) {
                    case 1:
                        placeYourOrder();
                        break;
                    case 2:
                        viewYourOrderedItems();
                        break;
                    case 3:
                        deleteAnItemFromOrder();
                        break;
                    case 4:
                        displayFinalBill();
                        break;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            } catch (Exception ex) {
                System.out.println("Exception in constructor");
                continue;
            }

        }

    }

    /**
     * @param orderMenu
     * @return void
     */
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

    /**
     * @name placeYourOrder
     * @param orderMenu
     * @return void
     */
    public void placeYourOrder() {
        displayOrderMenu();
        System.out.println("Enter the item Number");
        sc = new Scanner(System.in);
        int itemNumber = sc.nextInt();

        // Check if itemNumber is present in the orderMenu
        for (HashMap<String, Object> orderItem1 : orderMenu) {
            // if itemnumber is present in the orderMenu
            if (orderItem1.containsValue(itemNumber)) {
                for (HashMap<String, Object> customersListItem : customersList) {
                    if (customersListItem.containsValue(itemNumber)) {
                        customersListItem.put("itemQuantity", (int) customersListItem.get("itemQuantity") + 1);
                        System.out.println("Item quantity increased");
                        return;
                    }
                }
                customersList.add(orderItem1);
                System.out.println("Item added to your order");
                return;
            }
        }
        System.out.println("You choosed an item not listed in the menu");
        System.out.println("Please choose an item from the menu");
    }

    /**
     * @name viewYourOrderedItems
     * @param customersList
     * @return void
     */
    public void viewYourOrderedItems() {
        if (customersList.isEmpty()) {
            System.out.println("Your order is empty");
        } else {
            System.out.println("\t \t \t \t Number \t Food \t Price \t Quantity");
            for (HashMap<String, Object> orderItem1 : customersList) {
                System.out.print("\t \t \t \t\t" + orderItem1.get("itemNumber") + " \t  ");
                System.out.print(orderItem1.get("itemName") + " \t  ");
                System.out.print(orderItem1.get("itemPrice") + " \t\t  ");
                System.out.print(orderItem1.get("itemQuantity") + " \t ");
                System.out.println();
            }
        }
    }

    /**
     * @name deleteAnItemFromOrder
     * @param customersList
     * @return void
     */
    public void deleteAnItemFromOrder() {
        if (customersList.isEmpty()) {
            System.out.println("Your order is empty");
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the item Number");
        int itemNumber = sc.nextInt();
        boolean itemFound = false;

        // Loop through the customersList to find the itemNumber
        for (HashMap<String, Object> orderItem1 : customersList) {
            // if itemnumber is present in the orderMenu
            if (orderItem1.containsValue(itemNumber)) {
                // Remove the item from the customersList
                customersList.remove(orderItem1);
                System.out.println("Item removed from your order");
                break;
            }
        }
        // If itemNumber is not found in the customersList after looping through it
        if (!itemFound) {
            System.out.println("You choosed an item not listed in Your Order");
            System.out.println("Please choose an item listed in Your Order");
        }
        sc.close();
    }

    /**
     * @name displayFinalBill
     * @param customersList
     * @return void
     */
    public void displayFinalBill() {
        if (customersList.isEmpty()) {
            System.out.println("Your order is empty");
            return;
        }
        int totalBill = 0;
        viewYourOrderedItems();
        for (HashMap<String, Object> orderItem1 : customersList) {
            int itemPrice = (int) orderItem1.get("itemPrice");
            int itemQuantity = (int) orderItem1.get("itemQuantity");
            totalBill += itemPrice * itemQuantity;
        }
        System.out.println("\t Total Bill: " + totalBill);
    }

    public void displayOrderMenu() {
        if (orderMenu.isEmpty()) {
            System.out.println("Order Menu is empty");
            return;
        }
        System.out.println("\t \t \t \t Number \t Food \t Price");
        for (HashMap<String, Object> orderMenuItem : orderMenu) {
            System.out.print("\t \t \t \t" + orderMenuItem.get("itemNumber") + " \t  ");
            System.out.print(orderMenuItem.get("itemName") + " \t  ");
            System.out.print(orderMenuItem.get("itemPrice") + " \t\t  ");
            System.out.print(orderMenuItem.get("itemQuantity") + " \t ");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Customers c = new Customers();
        c.displayOrderMenu();
    }

    public void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
