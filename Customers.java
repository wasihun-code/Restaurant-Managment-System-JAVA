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
        // OrderMenu should be populated from admins orderMenu
        orderMenu = createInitialOrderMenu();
        clearScreen();
        System.out.println("\n");
        System.out.println("\t \t\t\t\t Welcome Customer");
        while (true) {
            System.out.println("\t \t \t \t 1. Place Your Order");
            System.out.println("\t \t \t \t 2. View Your Ordered Items");
            System.out.println("\t \t \t \t 3. Delete an item from order");
            System.out.println("\t \t \t \t 4. Display final bill");
            try {
                // Scanner class to read
                sc = new Scanner(System.in);
                System.out.print("\n\t\t\t\t =>Enter your choice: ");
                int choice = sc.nextInt();
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
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            } catch (Exception ex) {
                System.out.println("\n\n\n\t\t\t\t =>Program Forced to exit. Please Try again");
                break;
            }

        }

    }

    /**
     * @param orderMenu
     * @return void
     */

    /**
     * @name addToCart
     * @param orderMenu
     * @return void
     */
    public void addToCart() {
        displayMenu();
        System.out.print("\n\t\t\t\t =>Enter the item Number: ");
        sc = new Scanner(System.in);
        int itemNumber = sc.nextInt();

        // Check if itemNumber is present in the orderMenu
        for (HashMap<String, Object> orderItem1 : orderMenu) {
            // if itemnumber is present in the orderMenu
            if (orderItem1.containsValue(itemNumber)) {
                for (HashMap<String, Object> customersListItem : customersList) {
                    if (customersListItem.containsValue(itemNumber)) {
                        customersListItem.put("itemQuantity", (int) customersListItem.get("itemQuantity") + 1);
                        System.out.println("\n\t \t \t \t =>Item quantity increased**\n");
                        return;
                    }
                }
                customersList.add(orderItem1);
                System.out.println("\n\t \t \t \t =>Item added to your order**\n");
                return;
            }
        }
        System.out.println("\t \t \t \t =>You choosed an item not listed in the menu");
        System.out.println("\t \t \t \t =>Please choose an item from the menu");
    }

    /**
     * @name viewCart
     * @param customersList
     * @return void
     */
    public void viewCart() {
        if (customersList.isEmpty()) {
            System.out.println("\t \t \t \t =>Your order is empty\n\n");
        } else {
            System.out.println("\t \t \t \t Number \t Food \t Price \t Quantity");
            for (HashMap<String, Object> orderItem1 : customersList) {
                System.out.print("\t \t \t \t" + orderItem1.get("itemNumber") + " \t\t");
                System.out.print(orderItem1.get("itemName") + " \t  ");
                System.out.print(orderItem1.get("itemPrice") + " \t\t  ");
                System.out.print(orderItem1.get("itemQuantity") + " \t ");
                System.out.println();
            }
        }
    }

    /**
     * @name removeFromCart
     * @param customersList
     * @return void
     */
    public void removeFromCart() {
        if (customersList.isEmpty()) {
            System.out.println("\t \t \t \t =>Your order is empty\n\n");
            return;
        }
        sc = new Scanner(System.in);
        System.out.print("\t\t\t\t =>Enter the item Number");
        int itemNumber = sc.nextInt();

        // Loop through the customersList to find the itemNumber
        for (HashMap<String, Object> orderItem1 : customersList) {
            // if itemnumber is present in the orderMenu
            if (orderItem1.containsValue(itemNumber)) {
                // if itemQuantity is more than 1, decrease the quantity by 1
                if ((int) orderItem1.get("itemQuantity") > 1) {
                    orderItem1.put("itemQuantity", (int) orderItem1.get("itemQuantity") - 1);
                    System.out.println("\n\t \t \t \t =>Item quantity decreased");
                    return;
                }
                // if itemQuantity is 1, remove the item from the list
                customersList.remove(orderItem1);
                System.out.println("\n\t \t \t \t =>Item removed from your order");
                return;
            }
        }
        // If itemNumber is not found in the customersList after looping through it
        System.out.println("\t \t \t \t =>You choosed an item not listed in Your Order");
        System.out.println("\t \t \t \t =>Please choose an item listed in Your Order\n\n");
        return;
    }

    /**
     * @name showSubtotal
     * @param customersList
     * @return void
     */
    public void showSubtotal() {
        if (customersList.isEmpty()) {
            System.out.println("\t \t \t \t =>Your order is empty\n\n");
            return;
        }
        int totalBill = 0;
        viewCart();
        for (HashMap<String, Object> orderItem1 : customersList) {
            int itemPrice = (int) orderItem1.get("itemPrice");
            int itemQuantity = (int) orderItem1.get("itemQuantity");
            totalBill += itemPrice * itemQuantity;
        }
        System.out.println("\t \t \t \t =>Total Bill: " + totalBill +"\n\n");
    }


    // Test this java class
    public static void main(String[] args) {
        new Customers();
    }

    public void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
