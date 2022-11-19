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

        while (true) {
            System.out.println("1. View total sales");
            System.out.println("2. Add new items in the order menu");
            System.out.println("3. Delete items from the order menu");
            System.out.println("4. Display order menu");
            System.out.println("5. Display sales menu");
            System.out.println("6. Go back to main menu");
            System.out.println("7. Exit");

            int adminChoice = sc.nextInt();
            boolean goBackToMainMenu = false;
            sc.nextLine();
            switch (adminChoice) {
                case 1:
                    displayMenu();
                    break;
                case 2:
                    addToMenu();
                    break;
                case 3:
                    deleteFromMenu();
                    break;
                case 4:
                    viewTotalSales();
                    break;
                case 5:
                    displaySalesMenu();
                    break;
                case 6:
                    // Go back to main menu
                    goBackToMainMenu = true;
                    break;
                case 7:
                    System.out.println("Thank you for using our service");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
            // if user wants to go back to main menu
            if (goBackToMainMenu) {
                break;
            }
        }
    }

    public void viewTotalSales() {
        displayMenu();

        // Return if no user has items in their cart
        if (sales.isEmpty()) {
            System.out.println("No sales yet");
            return;
        }

        int totalSales = 0;
        int totalItems = 0;
        // Loop through admin's sales list
        for (HashMap<String, Object> salesItem : sales) {

            // Extract the price of the item
            int itemPrice = (int) salesItem.get("itemPrice");

            // Extract its quantity
            int itemQuantity = (int) salesItem.get("itemPrice");

            // Calculate total sales and increment number of quantity
            totalSales += (itemPrice * itemQuantity);
            totalItems += itemQuantity;
        }

        // Print total items sold and total sales generated
        System.out.println("Total Items Sold: " + totalItems);
        System.out.println("Total Sales: " + totalSales);
        return;
    }

    public void addToMenu() {

        int tempitemNumber = 0;

        // Get the last itemNumber from the menu
        for (HashMap<String, Object> menuItem : menu) {
            tempitemNumber = (int) menuItem.get("itemNumber");
        }

        // Increment the itemNumber by 1
        tempitemNumber++;
        System.out.println(tempitemNumber);
        final int itemNumber = tempitemNumber;

        System.out.println("Enter item name");
        final String itemName = sc.nextLine();

        // Check if item is already present in the menu
        if (itemExists(itemName)) {
            return;
        }

        System.out.println("Enter item price");
        final int itemPrice = sc.nextInt();
        sc.nextLine();
        item = new HashMap<String, Object>() {
            {
                put("itemNumber", itemNumber);
                put("itemName", itemName);
                put("itemPrice", itemPrice);
            }
        };
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

    public void displaySalesMenu() {
        if (sales.isEmpty()) {
            System.out.println("No sales yet");
            return;
        }
        System.out.println("Item Number\tItem Name\tItem Price\tItem Quantity");
        for (HashMap<String, Object> salesItem : sales) {
            System.out.println(salesItem.get("itemNumber") + "\t\t"
                    + salesItem.get("itemName") + "\t\t"
                    + salesItem.get("itemPrice"));
            System.out.println();
        }
    }

}
