package Restaurant;

// Import necessary util packages
import java.util.Formatter;
import java.util.HashMap;
import java.util.Scanner;

// Import utilities module from Commons package
import Commmons.Utilities;

public class Admin extends AdminCustomers {

    // Scanner object to read user input
    Scanner sc = new Scanner(System.in);

    public Admin() {
        Utilities.clearScreen();

        // Display welcome message to admin
        System.out.println(Utilities.ANSI_RED + "\n \t \t \t \t WELCOME ADMIN" + Utilities.ANSI_RESET);

        while (true) {
            adminMainMenu();
            int adminChoice;
            boolean goBackToMainMenu = false;

            // Validate user input with try-catch
            try {
                System.out.print(Utilities.ANSI_CYAN + "\n\t\t\t\t => Enter your choice: " + Utilities.ANSI_RESET);
                adminChoice = sc.nextInt();
                sc.nextLine(); // To consume the newline character
            } catch (Exception e) {
                Utilities.clearScreen();
                System.out.println(Utilities.ANSI_RED + "\n\t\t\t\t => Invalid input" + Utilities.ANSI_RESET);
                sc.nextLine(); // To consume the newline character
                continue;
            }

            Utilities.clearScreen();
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
                    System.out.println(Utilities.ANSI_RED + "\n\t\t\t\t => Thank you for using our service" + Utilities.ANSI_RESET);
                    System.exit(0);
                    break;
                default:
                    System.out.println(Utilities.ANSI_RED + "\n\t\t\t\t => Invalid choice" + Utilities.ANSI_RESET);
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
            System.out.println(Utilities.ANSI_RED + "\n\t\t\t\t => No sales yet" + Utilities.ANSI_RESET);
            return;
        }

        // First display menu
        displayMenu();

        // Intialize total sales to 0 and total items sold to 0
        int totalSales = 0;
        int totalItems = 0;

        // Loop through admin's sales list
        for (HashMap<String, Object> salesItem : sales) {

            // Extract the price of the item
            int itemPrice = (int) salesItem.get("itemPrice");

            // Extract its quantity
            int itemQuantity = (int) salesItem.get("itemQuantity");

            // Calculate total sales and increment number of quantity
            totalSales += (itemPrice * itemQuantity);
            totalItems += itemQuantity;
        }

        // Print total items sold and total sales generated 
        System.out.println(Utilities.ANSI_RED + "\n\t\t\t\t => Total Items Sold: " + totalItems + Utilities.ANSI_RESET);
        System.out.println(Utilities.ANSI_RED + "\n\t\t\t\t => Total Sales: " + totalSales + Utilities.ANSI_RESET);
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

        System.out.print(Utilities.ANSI_CYAN + "\n\t\t\t\t => Enter item name: " + Utilities.ANSI_RESET);
        final String itemName = sc.nextLine();

        // Check if item is already present in the menu
        if (itemExists(itemName)) {
            return;
        }

        System.out.print(Utilities.ANSI_CYAN + "\t\t\t\t => Enter item price: " + Utilities.ANSI_RESET);
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

        // if menu doesn't have any items
        if (menu.isEmpty()) {

            // Display message to admin and return
            System.out.println(Utilities.ANSI_RED + "\n\t\t\t\t => Menu is already empty." + Utilities.ANSI_RESET);
            return;
        }

        // Menu is not empty so display it
        displayMenu();

        // Get item number to delete
        System.out.print(Utilities.ANSI_CYAN + "\n\t\t\t\t => Enter item number to delete: " + Utilities.ANSI_RESET);
        int itemNumber = sc.nextInt();

        // Loop through the menu items
        for (HashMap<String, Object> menuItem : menu) {

            // if what user chose is in the menu
            if (menuItem.containsValue(itemNumber)) {

                // Then remove it
                menu.remove(menuItem);

                // Display success message to admin and return
                System.out.println(Utilities.ANSI_RED + "\n\t\t\t\t => Item deleted successfully." + Utilities.ANSI_RESET);
                return;
            }
        }

        // If what admin chose isn't in the menu. Prompt with an error message and return
        System.out.println(Utilities.ANSI_RED + "\n\t\t\t\t => Item not found." + Utilities.ANSI_RESET);
        System.out.println(Utilities.ANSI_RED + "\t\t\t\t => Please Enter a valid number again" + Utilities.ANSI_RESET);
        return;
    }

    public void displaySalesMenu() {

        // If no user has items in their cart then sales is empty
        if (sales.isEmpty()) {

            // Display message to admin and return
            System.out.println(Utilities.ANSI_RED + "\t \t \t \t => Sales Menu is empty" + Utilities.ANSI_RESET);
            return;
        }

        // Users have some items in their cart so display the sales menu
        // Create a table to display the menu
        Formatter f = new Formatter();

        // Format the table title
        f.format(Utilities.ANSI_GREEN + "%15s %15s %15s %15s %15s\n" + Utilities.ANSI_RESET, "",
        "", "SALES MENU", "", "");

        // Format the table title separator
        f.format(Utilities.ANSI_GREEN + "%15s %15s %15s %15s %15s\n" + Utilities.ANSI_RESET, "",
        "---------", "-------------", "------------", "-------------");

        // format the table header
        f.format(Utilities.ANSI_PURPLE + "%15s %15s %15s %15s %15s\n" + Utilities.ANSI_RESET, "", "Number",
                "Item Sold", "Price Sold", "Quantity Sold");

        // Format the table header separator
        f.format(Utilities.ANSI_GREEN + "%15s %15s %15s %15s %15s\n" + Utilities.ANSI_RESET, "",
                "---------", "-------------", "------------", "-------------");

        // Traverse through the sales menu
        for (HashMap<String, Object> item1 : sales) {

            // Format the sales menu rows
            f.format(Utilities.ANSI_CYAN +"%14s %14s %15s %10s %15s\n" + Utilities.ANSI_RESET, "", item1.get("itemNumber"),
                    item1.get("itemName"), item1.get("itemPrice"), item1.get("itemQuantity"));

            // Format the sales menu rows separator
            f.format(Utilities.ANSI_GREEN + "%15s %15s %15s %15s %15s\n" + Utilities.ANSI_RESET, "",
                    "---------", "-------------", "------------", "-------------");
        }

        // display the table menu
        System.out.println("\n\n");
        System.out.println(Utilities.ANSI_GREEN + f + Utilities.ANSI_RESET);
        f.close();
    }

    public void adminMainMenu() {
        System.out.println(Utilities.ANSI_YELLOW + "\n\t \t \t \t 1. View total sales" + Utilities.ANSI_RESET);
        System.out.println(Utilities.ANSI_YELLOW + "\t \t \t \t 2. Add new items in the order menu" + Utilities.ANSI_RESET);
        System.out.println(Utilities.ANSI_YELLOW + "\t \t \t \t 3. Delete items from the order menu" + Utilities.ANSI_RESET);
        System.out.println(Utilities.ANSI_YELLOW + "\t \t \t \t 4. Display order menu" + Utilities.ANSI_RESET);
        System.out.println(Utilities.ANSI_YELLOW + "\t \t \t \t 5. Display sales menu" + Utilities.ANSI_RESET);
        System.out.println(Utilities.ANSI_YELLOW + "\t \t \t \t 6. Go back to main menu" + Utilities.ANSI_RESET);
        System.out.println(Utilities.ANSI_YELLOW + "\t \t \t \t 7. Exit" + Utilities.ANSI_RESET);
    }
}
