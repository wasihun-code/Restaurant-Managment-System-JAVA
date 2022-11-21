package Restaurant;
import java.io.IOException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Scanner;
import Commmons.Utilities;
public class Customers extends AdminCustomers {

    // Scanner to read user input
    Scanner sc = new Scanner(System.in);

    /**
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    public Customers() {
        Utilities.clearScreen();
        System.out.println("\n");
        System.out.println(Utilities.ANSI_CYAN + "\t \t\t\t\t Welcome Customer" + Utilities.ANSI_RESET);
        while (true) {
            customerMainMenu();

            int choice;
            boolean goBackToMainMenu = false;

            // Validate user input with try-catch
            try {
                System.out.print(Utilities.ANSI_CYAN + "\n\t\t\t\t =>Enter your choice: " + Utilities.ANSI_RESET);
                choice = sc.nextInt();
                sc.nextLine(); // To consume the newline character
            } catch (Exception e) {
                Utilities.clearScreen();
                System.out.println(Utilities.ANSI_RED + "\n\t\t\t\t =>Invalid Input. Please Try again" + Utilities.ANSI_RESET);
                sc.nextLine(); // To consume the newline character
                continue;
            }

            Utilities.clearScreen();
            switch (choice) {
                case 1:
                    addToCart();
                    break;
                case 2:
                    viewCart();
                    break;
                case 3:
                    removeFromCart();
                    break;
                case 4:
                    showSubtotal();
                    break;
                case 5:
                    // Go back to main menu
                    goBackToMainMenu = true;
                    return;
                case 6:
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

    public void addToCart() {
        displayMenu();
        System.out.print(Utilities.ANSI_CYAN + "\n\t\t\t\t =>Enter the item Number: " + Utilities.ANSI_RESET);
        sc = new Scanner(System.in);
        int itemNumber = sc.nextInt();

        // loop through the menu to find the item
        for (HashMap<String, Object> menuItem : menu) {

            // if item choosen by user is found in the menu
            if (menuItem.containsValue(itemNumber)) {

                // if the user cart is not empty
                if (!cart.isEmpty()) {

                    // Loop through the users cart
                    for (HashMap<String, Object> cartItem : cart) {

                        // If already item is in users cart
                        if (cartItem.containsValue(itemNumber)) {

                            // Just increase the item quantity and return without again adding it to cart
                            cartItem.put("itemQuantity", (int) cartItem.get("itemQuantity") + 1);
                            System.out.println(Utilities.ANSI_RED + "\n\t \t \t \t =>Item quantity increased**\n" + Utilities.ANSI_RESET);
                            return;
                        }
                    }
                }
                // If the item is not in the users cart make the quantity 1
                menuItem.put("itemQuantity", 1);

                // Then add the item to the users cart
                cart.add(menuItem);

                // Also add the item to the sales menu as if the user has bought it
                sales.add(menuItem);

                // Print the item added to cart
                System.out.println(Utilities.ANSI_RED + "\n\t \t \t \t =>Item added to your Cart**\n" + Utilities.ANSI_RESET);
                return;
            }
        }
        
        // If the item is not found in the menu print the error message
        System.out.println(Utilities.ANSI_RED + "\t \t \t \t =>You choosed an item not listed in the menu" + Utilities.ANSI_RESET);
        System.out.println(Utilities.ANSI_RED + "\t \t \t \t =>Please choose an item from the menu" + Utilities.ANSI_RESET);
    }

    public void viewCart() {

        // If the cart is empty 
        if (cart.isEmpty()) {

            // Print the error message and return
            System.out.println(Utilities.ANSI_RED + "\t \t \t \t =>Order Menu is empty" + Utilities.ANSI_RESET);
            return;
        }

        // Create a table to display the menu
        Formatter f = new Formatter();

        // Format the table title
        f.format(Utilities.ANSI_GREEN + "%15s %15s %15s %15s %15s\n" + Utilities.ANSI_RESET, "",
        "", "\t CART", "", "");

        // Format the table title separator
        f.format(Utilities.ANSI_GREEN + "%15s %15s %15s %15s %15s\n" + Utilities.ANSI_RESET, "",
        "---------", "-------------", "------------", "-------------");

        // Format the table header
        f.format(Utilities.ANSI_PURPLE + "%15s %15s %15s %15s %15s\n" + Utilities.ANSI_RESET, "", "Number",
                "Item Name", "Item Price", "Item Quantity");

        // Format the table header separator
        f.format(Utilities.ANSI_GREEN + "%15s %15s %15s %15s %15s\n" + Utilities.ANSI_RESET, "",
                "---------", "-------------", "------------", "-------------");
            
        // Loop through the users cart
        for (HashMap<String, Object> item1 : cart) {

            // Format the table body
            f.format(Utilities.ANSI_CYAN +"%14s %14s %15s %10s %10s\n" + Utilities.ANSI_RESET, "", item1.get("itemNumber"),
                    item1.get("itemName"), item1.get("itemPrice"), item1.get("itemQuantity"));
            
            // Format the table body separator
            f.format(Utilities.ANSI_GREEN + "%15s %15s %15s %15s %15s\n" + Utilities.ANSI_RESET, "",
                    "---------", "-------------", "------------", "-------------");
        }

        // display the table menu
        System.out.println("\n\n");
        System.out.println(Utilities.ANSI_GREEN + f + Utilities.ANSI_RESET);
        f.close();
    }

    public void removeFromCart() {

        // If the cart is empty
        if (cart.isEmpty()) {

            // Print the error message and return
            System.out.println(Utilities.ANSI_RED + "\t \t \t \t =>Your Cart is empty\n\n" + Utilities.ANSI_RESET);
            return;
        }

        // Read the item number from the user
        sc = new Scanner(System.in);
        System.out.print(Utilities.ANSI_CYAN + "\t\t\t\t =>Enter the item Number" + Utilities.ANSI_RESET);
        int itemNumber = sc.nextInt();

        // Loop through the cart to find the itemNumber
        for (HashMap<String, Object> menuItem : cart) {

            // if itemnumber is present in the orderMenu
            if (menuItem.containsValue(itemNumber)) {

                // if itemQuantity is more than 1, decrease the quantity by 1
                if ((int) menuItem.get("itemQuantity") > 1) {

                    // Decrease the item quantity by 1
                    menuItem.put("itemQuantity", (int) menuItem.get("itemQuantity") - 1);

                    // Print the item quantity decreased message
                    System.out.println(Utilities.ANSI_RED + "\n\t \t \t \t =>Item quantity decreased" + Utilities.ANSI_RESET);
                    return;
                }
                // if itemQuantity is 1, remove the item from the list
                cart.remove(menuItem);
                sales.remove(menuItem);
                System.out.println(Utilities.ANSI_RED + "\n\t \t \t \t =>Item removed from your Cart" + Utilities.ANSI_RESET);
                return;
            }
        }

        // If itemNumber is not found in the cart after looping through it
        System.out.println(Utilities.ANSI_RED + "\t \t \t \t =>You choosed an item not listed in Your Cart" + Utilities.ANSI_RESET);
        System.out.println(Utilities.ANSI_RED + "\t \t \t \t =>Please choose an item listed in Your Cart\n\n" + Utilities.ANSI_RESET);
        return;
    }

    public void showSubtotal() {

        // if the cart is empty
        if (cart.isEmpty()) {

            // No items in the cart, print the error message and return
            System.out.println(Utilities.ANSI_RED + "\t \t \t \t =>Your Cart is empty\n\n" + Utilities.ANSI_RESET);
            return;
        }

        // Display the cart for user to see
        viewCart();
        

        int totalBill = 0;
        // Loop through the cart
        for (HashMap<String, Object> menuItem : cart) {

            // Get the item price and quantity
            int itemPrice = (int) menuItem.get("itemPrice");
            int itemQuantity = (int) menuItem.get("itemQuantity");

            // Calculate the total bill and increase it
            totalBill += itemPrice * itemQuantity;
        }

        // Print the total bill
        System.out.println(Utilities.ANSI_RED + "\t \t \t \t =>Total Bill: " + totalBill + "\n\n" + Utilities.ANSI_RESET);
        return;
    }

    // This method is used to show customers main menu
    public void customerMainMenu() {

        // Display customers main menu in yellow color
        System.out.println(Utilities.ANSI_YELLOW + "\t \t \t \t 1. Add to Cart" + Utilities.ANSI_RESET);
        System.out.println(Utilities.ANSI_YELLOW + "\t \t \t \t 2. View Cart" + Utilities.ANSI_RESET);
        System.out.println(Utilities.ANSI_YELLOW + "\t \t \t \t 3. Delete From Cart" + Utilities.ANSI_RESET);
        System.out.println(Utilities.ANSI_YELLOW + "\t \t \t \t 4. Display Sub total" + Utilities.ANSI_RESET);
        System.out.println(Utilities.ANSI_YELLOW + "\t \t \t \t 5. Go back to main menu" + Utilities.ANSI_RESET);
        System.out.println(Utilities.ANSI_YELLOW + "\t \t \t \t 6. Exit" + Utilities.ANSI_RESET);
    }
}
