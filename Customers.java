import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Customers extends AdminCustomers {

    // Scanner to read user input
    Scanner sc = new Scanner(System.in);

    /**
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    public Customers() {
        clearScreen();
        System.out.println("\n");
        System.out.println("\t \t\t\t\t Welcome Customer");
        while (true) {
            System.out.println("\t \t \t 1. Place Your Cart");
            System.out.println("\t \t \t 2. View Your Carted Items");
            System.out.println("\t \t \t 3. Delete an item from order");
            System.out.println("\t \t \t 4. Display sub total");
            System.out.println("\t \t \t 5. Go back to main menu");
            System.out.println("\t \t \t 6. Exit");

            int choice;
            boolean goBackToMainMenu = false;

            // Validate user input with try-catch
            try {
                System.out.print("\n\t\t\t\t =>Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine(); // To consume the newline character
            } catch (Exception e) {
                clearScreen();
                System.out.println("Invalid choice");
                sc.nextLine(); // To consume the newline character
                continue;
            }

            clearScreen();
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

    /**
     * @name addToCart
     * @param menu
     * @return void
     */
    public void addToCart() {
        displayMenu();
        System.out.print("\n\t\t\t\t =>Enter the item Number: ");
        sc = new Scanner(System.in);
        int itemNumber = sc.nextInt();

        // Check if itemNumber is present in the orderMenu
        for (HashMap<String, Object> menuItem : menu) {
            // if itemnumber is present in the orderMenu
            if (menuItem.containsValue(itemNumber)) {
                for (HashMap<String, Object> cartItem : cart) {
                    if (cartItem.containsValue(itemNumber)) {
                        cartItem.put("itemQuantity", (int) cartItem.get("itemQuantity") + 1);
                        System.out.println("\n\t \t \t \t =>Item quantity increased**\n");
                        return;
                    }
                    cartItem.put("itemQuantity", 1);
                }
                cart.add(menuItem);
                sales.add(menuItem);
                System.out.println("\n\t \t \t \t =>Item added to your Cart**\n");
                return;
            }
        }
        System.out.println("\t \t \t \t =>You choosed an item not listed in the menu");
        System.out.println("\t \t \t \t =>Please choose an item from the menu");
    }

    /**
     * @name viewCart
     * @param cart
     * @return void
     */
    public void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("\t \t \t \t =>Your Cart is empty\n\n");
        } else {
            System.out.println("\t \t \t \t Number \t Food \t Price \t Quantity");
            for (HashMap<String, Object> menuItem : cart) {
                System.out.print("\t \t \t \t" + menuItem.get("itemNumber") + " \t\t");
                System.out.print(menuItem.get("itemName") + " \t  ");
                System.out.print(menuItem.get("itemPrice") + " \t\t  ");
                System.out.print(menuItem.get("itemQuantity") + " \t ");
                System.out.println();
            }
        }
    }

    /**
     * @name removeFromCart
     * @param cart
     * @return void
     */
    public void removeFromCart() {
        if (cart.isEmpty()) {
            System.out.println("\t \t \t \t =>Your Cart is empty\n\n");
            return;
        }
        sc = new Scanner(System.in);
        System.out.print("\t\t\t\t =>Enter the item Number");
        int itemNumber = sc.nextInt();

        // Loop through the cart to find the itemNumber
        for (HashMap<String, Object> menuItem : cart) {
            // if itemnumber is present in the orderMenu
            if (menuItem.containsValue(itemNumber)) {
                // if itemQuantity is more than 1, decrease the quantity by 1
                if ((int) menuItem.get("itemQuantity") > 1) {
                    menuItem.put("itemQuantity", (int) menuItem.get("itemQuantity") - 1);
                    System.out.println("\n\t \t \t \t =>Item quantity decreased");
                    return;
                }
                // if itemQuantity is 1, remove the item from the list
                cart.remove(menuItem);
                sales.remove(menuItem);
                System.out.println("\n\t \t \t \t =>Item removed from your Cart");
                return;
            }
        }
        // If itemNumber is not found in the cart after looping through it
        System.out.println("\t \t \t \t =>You choosed an item not listed in Your Cart");
        System.out.println("\t \t \t \t =>Please choose an item listed in Your Cart\n\n");
        return;
    }

    /**
     * @name showSubtotal
     * @param cart
     * @return void
     */
    public void showSubtotal() {
        if (cart.isEmpty()) {
            System.out.println("\t \t \t \t =>Your Cart is empty\n\n");
            return;
        }
        int totalBill = 0;
        viewCart();
        for (HashMap<String, Object> menuItem : cart) {
            int itemPrice = (int) menuItem.get("itemPrice");
            int itemQuantity = (int) menuItem.get("itemQuantity");
            totalBill += itemPrice * itemQuantity;
        }
        System.out.println("\t \t \t \t =>Total Bill: " + totalBill + "\n\n");
    }
}
