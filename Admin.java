import java.util.Formatter;
import java.util.HashMap;
import java.util.Scanner;

public class Admin extends AdminCustomers {
    Scanner sc = new Scanner(System.in);

    Admin() {
        clearScreen();
        System.out.println(ANSI_CYAN + "\n \t \t \t \t Welcome Admin" + ANSI_RESET);

        while (true) {
            adminMainMenu();
            int adminChoice;
            boolean goBackToMainMenu = false;

            // Validate user input with try-catch
            try {
                System.out.print(ANSI_CYAN + "\n\t\t\t\t =>Enter your choice: " + ANSI_RESET);
                adminChoice = sc.nextInt();
                sc.nextLine(); // To consume the newline character
            } catch (Exception e) {
                clearScreen();
                System.out.println(ANSI_RED + "\n\t\t\t\t =>Invalid input" + ANSI_RESET);
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
                    System.out.println(ANSI_RED + "\n\t\t\t\t =>Thank you for using our service" + ANSI_RESET);
                    System.exit(0);
                    break;
                default:
                    System.out.println(ANSI_RED + "\n\t\t\t\t =>Invalid choice" + ANSI_RESET);
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
            System.out.println(ANSI_RED + "\n\t\t\t\t =>No sales yet" + ANSI_RESET);
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
        System.out.println(ANSI_RED + "\n\t\t\t\t =>Total Items Sold: " + totalItems + ANSI_RESET);
        System.out.println(ANSI_RED + "\n\t\t\t\t =>Total Sales: " + totalSales + ANSI_RESET);
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

        System.out.print(ANSI_CYAN + "\n\t\t\t\t =>Enter item name: " + ANSI_RESET);
        final String itemName = sc.nextLine();

        // Check if item is already present in the menu
        if (itemExists(itemName)) {
            return;
        }

        System.out.print(ANSI_CYAN + "\t\t\t\t =>Enter item price: " + ANSI_RESET);
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
            System.out.println(ANSI_RED + "\n\t\t\t\t =>Menu is already empty." + ANSI_RESET);
            return;
        }
        displayMenu();
        System.out.print(ANSI_CYAN + "\n\t\t\t\t =>Enter item number to delete: " + ANSI_RESET);
        int itemNumber = sc.nextInt();

        // Loop through the menu items
        for (HashMap<String, Object> menuItem : menu) {

            // if what user chose is in the menu
            if (menuItem.containsValue(itemNumber)) {

                // Then remove it
                menu.remove(menuItem);
                System.out.println(ANSI_RED + "\n\t\t\t\t =>Item deleted successfully." + ANSI_RESET);
                return;
            }
        }
        // if what admin chose isn't in the menu. Prompt with an error message
        System.out.println(ANSI_RED + "\n\t\t\t\t =>Item not found." + ANSI_RESET);
        System.out.println(ANSI_RED + "\n\t\t\t\t => Please Enter a valid number again" + ANSI_RESET);
        return;
    }

    public void displaySalesMenu() {
        if (menu.isEmpty()) {
            System.out.println(ANSI_RED + "\t \t \t \t =>Order Menu is empty" + ANSI_RESET);
            return;
        }

        // Create a table to display the menu
        Formatter f = new Formatter();

        f.format(ANSI_GREEN + "%15s %15s %15s %15s %15s\n" + ANSI_RESET, "",
        "", "SALES MENU", "", "");

        f.format(ANSI_GREEN + "%15s %15s %15s %15s %15s\n" + ANSI_RESET, "",
        "---------", "-------------", "------------", "-------------");
        // format the table header
        f.format(ANSI_PURPLE + "%15s %15s %15s %15s %15s\n" + ANSI_RESET, "", "Number",
                "Item Sold", "Price Sold", "Quantity Sold");

        f.format(ANSI_GREEN + "%15s %15s %15s %15s %15s\n" + ANSI_RESET, "",
                "---------", "-------------", "------------", "-------------");
        // loop through the menu
        for (HashMap<String, Object> item1 : sales) {
            // format the table body
            f.format(ANSI_CYAN +"%14s %14s %15s %10s\n" + ANSI_RESET, "", item1.get("itemNumber"),
                    item1.get("itemName"), item1.get("itemPrice"), item1.get("itemQuantity"));
            f.format(ANSI_GREEN + "%15s %15s %15s %15s %15s\n" + ANSI_RESET, "",
                    "---------", "-------------", "------------", "-------------");
        }

        // display the table menu
        System.out.println("\n\n");
        System.out.println(ANSI_GREEN + f + ANSI_RESET);
        f.close();
    }

    public void adminMainMenu() {
        System.out.println(ANSI_YELLOW + "\n\t \t \t \t 1. View total sales" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "2. Add new items in the order menu" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "\t \t \t \t 3. Delete items from the order menu" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "\t \t \t \t 4. Display order menu" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "\t \t \t \t 5. Display sales menu" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "\t \t \t \t 6. Go back to main menu" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "\t \t \t \t 7. Exit" + ANSI_RESET);
    }
}
