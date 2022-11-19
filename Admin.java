import java.util.HashMap;
import java.util.Scanner;

// 1. View total sales
// 2. Add new items in the order menu
// 3. Delete items from the order menu
// 4. Display order menu

public class Admin extends AdminCustomers {
    Scanner sc = new Scanner(System.in);

    Admin() {
        System.out.println("Welcome Admin");

        ;
        while (true) {
            System.out.println("1. View total sales");
            System.out.println("2. Add new items in the order menu");
            System.out.println("3. Delete items from the order menu");
            System.out.println("4. Display order menu");

            int adminChoice = sc.nextInt();
            sc.nextLine();
            switch (adminChoice) {
                case 1:
                    viewTotalSales();
                    break;
                case 2:
                    addToMenu();
                    break;
                case 3:
                    deleteFromMenu();
                    break;
                case 4:
                    displayMenu();
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }

    public void viewTotalSales() {
        displayMenu();

        if (sales.isEmpty()) {
            System.out.println("No sales yet");
            return;
        }
        ;
        int totalSales = 0;
        int totalItems = 0;
        for (HashMap<String, Object> salesItem : sales) {
            int itemPrice = (int) salesItem.get("itemPrice");
            int itemQuantity = (int) salesItem.get("itemPrice");
            totalSales += (itemPrice * itemQuantity);
            totalItems += itemQuantity;
        }
        System.out.println("Total Items Sold: " + totalItems);
        System.out.println("Total Sales: " + totalSales);
    }

    public void addToMenu() {

        int tempitemNumber = 0;

        // Get the last itemNumber from the menu
        for (HashMap<String, Object> menuItem : menu) {
            int keyNumber = (int) menuItem.get("itemNumber");
            if (keyNumber > tempitemNumber) {
                tempitemNumber = (int) menuItem.get("itemNumber");
            }
        }

        // Increment the itemNumber by 1
        int itemNumber = tempitemNumber + 1;
        item.put("itemNumber", itemNumber);

        System.out.println("Enter item name");
        String itemName = sc.nextLine();

        // Check if item is already present in the menu
        for (HashMap<String, Object> menuItem : menu) {

            // Convert the item name to capital case
            String tempitemName = itemName.substring(0, 1).toUpperCase();
            tempitemName += itemName.substring(1).toLowerCase();
            System.out.println(tempitemName);
            // if item is present in the menu then return
            if (menuItem.containsValue(tempitemName)) {
                System.out.println("Item already exists");
                return;
            }
        }
        item.put("itemName", itemName);

        System.out.println("Enter item price");
        int itemPrice = sc.nextInt();
        sc.nextLine(); // To consume the new line character
        item.put("itemPrice", itemPrice);

        System.out.println("Enter item quantity");
        int itemQuantity = sc.nextInt();
        item.put("itemQuantity", itemQuantity);

        menu.add(item);

    }

    public void deleteFromMenu() {
        if (menu.isEmpty()) {
            System.out.println("Menu is already empty.");
            return;
        }
        System.out.print("Enter item number to delete: ");
        int itemNumber = sc.nextInt();
        for (HashMap<String, Object> menuItem : menu) {
            if (menuItem.containsValue(itemNumber)) {
                menu.remove(menuItem);
                System.out.println("Item deleted successfully.");
                return;
            }
        }
        System.out.println("Item not found.");
        System.out.println("Enter valid number");
        return;
    }

    // Test admin class
    public static void main(String[] args) {
        new Admin();
    }
}
