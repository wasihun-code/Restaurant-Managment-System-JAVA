import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// 1. Place your order
// 2. View your ordered items
// 3. Delete an item from order
// 4. Display final bill
// 5. Back To Main Menu

public class Customers {
    // An item is represented as a HashMap with itemNumber, itemName, itemPrice, itemQuantity as keys
    HashMap<String, Object> orderItem = new HashMap<String, Object>();

    // An order is represented as an ArrayList of HashMaps(items representation)
    ArrayList<HashMap<String, Object>> orderMenu = new ArrayList<HashMap<String, Object>>();

    // A customers order is represented as an ArrayList of HashMaps(items) created from orderMenu
    ArrayList<HashMap<String, Object>> customersOrderList = new ArrayList<HashMap<String, Object>>();

    Customers() {
        // OrderMenu should be populated from admins orderMenu
        System.out.println("Welcome Customer");

        System.out.println("1. Place Your Order");
        System.out.println("2. View Your Ordered Items");
        System.out.println("3. Delete an item from order");
        System.out.println("4. Display final bill");

        Scanner sc = new Scanner(System.in);
        int customerChoice = sc.nextInt();

        switch (customerChoice) {
            case 1:
                placeYourOrder(orderMenu);
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
        sc.close();
    }
    // itemNumber, itemName, itemPrice, itemQuantity
    public void placeYourOrder(ArrayList<HashMap<String, Object>> orderMenu) {
        System.out.println("Enter the item Number");
        Scanner sc = new Scanner(System.in);
        int itemNumber = sc.nextInt();
        boolean itemFound = false;

        // Check if itemNumber is present in the orderMenu
        for (HashMap<String, Object> orderItem1 : orderMenu) {
            // if itemnumber is present in the orderMenu
            if (orderItem1.containsValue(itemNumber)) {
                customersOrderList.add(orderItem1);
                itemFound = true;
                System.out.println("Item added to your order");
                break;
            }
        }
        if (!itemFound) {
            System.out.println("You choosed an item not listed in the menu");
            System.out.println("Please choose an item from the menu");
        }
        sc.close();
    }

    public void viewYourOrderedItems() {

    }

    public void deleteAnItemFromOrder() {

    }

    public void displayFinalBill() {

    }

    public static void main(String[] args) {
        Customers c = new Customers();
    }
}
