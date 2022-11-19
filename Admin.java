import java.util.HashMap;
import java.util.Scanner;

// 1. View total sales
// 2. Add new items in the order menu
// 3. Delete items from the order menu
// 4. Display order menu

public class Admin extends AdminCustomers {
    Scanner sc = new Scanner(System.in);

    Admin() {
        clearScreen();
        System.out.println("\n \t \t \t \t Welcome Admin");

        while (true) {
            adminMainMenu();
            int adminChoice;
            boolean goBackToMainMenu = false;

            // Validate user input with try-catch
            try { 
                System.out.print("\n\t\t\t\t =>Enter your choice: ");
                adminChoice = sc.nextInt();
                sc.nextLine(); // To consume the newline character
            } catch (Exception e) {
                clearScreen();
                System.out.println("\n\t\t\t\t =>Invalid input");
                sc.nextLine(); // To consume the newline character
                continue;
            }

            clearScreen();
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
                case 5:
                    displaySalesMenu();
                    break;
                case 6:
                    // Go back to main menu
                    goBackToMainMenu = true;
                    break;
                case 7:
                    System.out.println("\n\t\t\t\t =>Thank you for using our service");
                    System.exit(0);
                    break;
                default:
                    System.out.println("I\n\t\t\t\t =>nvalid choice");
                    break;
            }
            // if user wants to go back to main menu
            if (goBackToMainMenu) {
                break;
            }
        }
    }

    public void viewTotalSales() {
        // Return if no user has items in their cart
        if (sales.isEmpty()) {
            System.out.println("\n\t\t\t\t =>No sales yet");
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
        System.out.println("\n\t\t\t\t =>Total Items Sold: " + totalItems);
        System.out.println("\n\t\t\t\t =>Total Sales: " + totalSales);
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
        final int itemNumber = tempitemNumber;

        System.out.print("\n\t\t\t\t =>Enter item name: ");
        final String itemName = sc.nextLine();

        // Check if item is already present in the menu
        if (itemExists(itemName)) {
            return;
        }

        System.out.print("\t\t\t\t =>Enter item price: ");
        final int itemPrice = sc.nextInt();
        sc.nextLine(); // Consume new line character

        // Add item detail to the hashmap
        item = new HashMap<String, Object>() {
            {
                put("itemNumber", itemNumber);
                put("itemName", itemName);
                put("itemPrice", itemPrice);
            }
        };
        // Add item to the menu
        menu.add(item);
    }

    public void deleteFromMenu() {

        // return if menu doesn't contain items
        if (menu.isEmpty()) {
            System.out.println("\n\t\t\t\t =>Menu is already empty.");
            return;
        }
        displayMenu();
        System.out.print("\n\t\t\t\t =>Enter item number to delete: ");
        int itemNumber = sc.nextInt();

        // Loop through the menu items
        for (HashMap<String, Object> menuItem : menu) {

            // if what user chose is in the menu
            if (menuItem.containsValue(itemNumber)) {

                // Then remove it
                menu.remove(menuItem);
                System.out.println("\n\t\t\t\t =>Item deleted successfully.");
                return;
            }
        }
        // if what admin chose isn't in the menu. Prompt with an error message
        System.out.println("\n\t\t\t\t =>Item not found.");
        System.out.println("\n\t\t\t\t => Please Enter a valid number again");
        return;
    }

    public void displaySalesMenu() {
        if (sales.isEmpty()) {
            System.out.println("\n\t\t\t\t =>No sales yet");
            return;
        }
        // *** use FORmatter ***
        System.out.println("Item Number\tItem Name\tItem Price\tItem Quantity");
        for (HashMap<String, Object> salesItem : sales) {
            System.out.println(salesItem.get("itemNumber") + "\t\t"
                    + salesItem.get("itemName") + "\t\t"
                    + salesItem.get("itemPrice"));
            System.out.println();
        }
    }

    public void adminMainMenu() {
        System.out.println("\n\t \t \t \t 1. View total sales");
        System.out.println("\t \t \t \t 2. Add new items in the order menu");
        System.out.println("\t \t \t \t 3. Delete items from the order menu");
        System.out.println("\t \t \t \t 4. Display order menu");
        System.out.println("\t \t \t \t 5. Display sales menu");
        System.out.println("\t \t \t \t 6. Go back to main menu");
        System.out.println("\t \t \t \t 7. Exit");
    }
}
