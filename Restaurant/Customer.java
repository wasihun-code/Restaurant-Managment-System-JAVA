package Restaurant;

// Import necessary sql packages
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
// Import necessary util packages
import java.util.Formatter;
import java.util.HashMap;
import java.util.Scanner;

import Accounts.LoginToAccount;
// Import utilities module from Commons package
import Commmons.Utilities;

public class Customer extends AdminCustomers {

    // Scanner to read user input
    Scanner sc = new Scanner(System.in);

    public Customer() {
        Utilities.clearScreen();
        System.out.println("\n");
        System.out.println(Utilities.ANSI_RED + "\t \t\t\t\t Welcome Customer" +
                Utilities.ANSI_RESET);
        while (true) {

            // Clear cart and load it
            cart.clear();

            // Load the user cart from the database
            loadCartFromDB();

            customerMainMenu();

            // Store user choice of going back to main menu
            boolean goBackToMainMenu = false;

            // Validate user input with try-catch
            int choice = Utilities.validateUserInputTryCatch();

            if (choice == -1234) {
                continue;
            }

            Utilities.clearScreen();
            switch (choice) {
                case 1:
                    addItemToCart();
                    break;
                case 2:
                    viewCart();
                    break;
                case 3:
                    removeItemFromCart();
                    break;
                case 4:
                    showSubtotal();
                    break;
                case 5:
                    // Go back to main menu
                    goBackToMainMenu = true;
                    return;
                case 6:
                    System.out.println(Utilities.ANSI_RED + "\n\t\t\t\t => Thank you for usingour service"
                            + Utilities.ANSI_RESET);
                    System.exit(0);
                    break;
                default:
                    System.out.println(Utilities.ANSI_RED + "\n\t\t\t\t => Invalid choice" +
                            Utilities.ANSI_RESET);
                    break;
            }
            // If user wants to go back to main menu
            if (goBackToMainMenu) {
                break;
            }
        }
    }

    public void addItemToCart() {
        // Display menu to user to be able to select items
        displayMenu();

        // Get the item number from user
        System.out.print(Utilities.ANSI_CYAN + "\n\t\t\t\t => Enter the item Number: " + Utilities.ANSI_RESET);
        sc = new Scanner(System.in);
        int itemNumber = sc.nextInt();
        sc.nextLine();

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
                            System.out.println(Utilities.ANSI_RED + "\n\t \t \t \t => Item quantity increased**\n"
                                    + Utilities.ANSI_RESET);
                            // Add the item to users cart in the database as well
                            // This function will also increment the quantity if the item is already in the
                            // cart
                            addItemToCart_DB(menuItem, true);
                            return;
                        }
                    }
                }
                // If the item is not in the users cart make the quantity 1
                menuItem.put("itemQuantity", 1);

                // Then add the item to the users cart
                cart.add(menuItem);

                // Add the item to users cart in the database as well
                addItemToCart_DB(menuItem, false);

                // Also add the item to the sales menu as if the user has bought it
                sales.add(menuItem);

                // Print the item added to cart
                System.out.println(
                        Utilities.ANSI_RED + "\n\t \t \t \t => Item added to your Cart**\n" + Utilities.ANSI_RESET);
                return;
            }
        }

        // If the item is not found in the menu print the error message
        System.out.println(Utilities.ANSI_RED + "\t \t \t \t => You choosed an item not listed in the menu"
                + Utilities.ANSI_RESET);
        System.out.println(
                Utilities.ANSI_RED + "\t \t \t \t => Please choose an item from the menu" + Utilities.ANSI_RESET);
    }

    // Add the item to the user's cart in the database as well
    public void addItemToCart_DB(HashMap<String, Object> menuItem, boolean itemExists) {

        // Create Strings for the database connection
        String url = "jdbc:mysql://localhost:3306/restaurant";
        String uname = "root";
        String pass = "RMS.java";

        // Connect with the database using try-resource
        try (Connection con = DriverManager.getConnection(url, uname, pass)) {

            // Create a statement to execute the query

            if (itemExists) {
                // Store table name of users cart
                String table = "cart_" + LoginToAccount.UserId;

                // If the item is already in the cart just increase the quantity
                String query = "UPDATE " + table + " SET itemQuantity = itemQuantity + 1 WHERE itemName = ?";

                // Create a prepared statement to execute the query
                PreparedStatement st = con.prepareStatement(query);

                // Set the values for the prepared statement
                st.setString(1, (String) menuItem.get("itemName"));

                // Execute the query
                st.executeUpdate();

                return;
            }

            // Create a query to insert the item to the cart
            String query = "INSERT INTO " + "cart_" + LoginToAccount.UserId +
                    "(itemNumber, itemName, itemPrice, itemQuantity) VALUES(?,?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(query);

            // pstmt.setString(1, "cart_1009");
            pstmt.setInt(1, (int) menuItem.get("itemNumber"));
            pstmt.setString(2, (String) menuItem.get("itemName"));
            pstmt.setInt(3, (int) menuItem.get("itemPrice"));
            pstmt.setInt(4, (int) menuItem.get("itemQuantity"));

            // Execute the query
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
            return;
        }
    }

    public void viewCart() {

        // If the cart is empty
        if (cart.isEmpty()) {

            // Print the error message and return
            System.out.println(Utilities.ANSI_RED + "\t \t \t \t => Order Menu is empty" + Utilities.ANSI_RESET);
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
            f.format(Utilities.ANSI_CYAN + "%14s %14s %15s %10s %10s\n" + Utilities.ANSI_RESET, "",
                    item1.get("itemNumber"),
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

    public void removeItemFromCart() {

        // If the cart is empty
        if (cart.isEmpty()) {

            // Print the error message and return
            System.out.println(Utilities.ANSI_RED + "\t \t \t \t => Your Cart is empty\n\n" + Utilities.ANSI_RESET);
            return;
        }

        // Read the item number from the user
        System.out.print(Utilities.ANSI_CYAN + "\t\t\t\t => Enter the item Number" + Utilities.ANSI_RESET);
        sc = new Scanner(System.in);
        int itemNumber = sc.nextInt();
        sc.nextLine(); // consume the new line

        // Loop through the cart to find the itemNumber
        for (HashMap<String, Object> menuItem : cart) {

            // if itemnumber is present in the orderMenu
            if (menuItem.containsValue(itemNumber)) {

                // if itemQuantity is more than 1, decrease the quantity by 1
                if ((int) menuItem.get("itemQuantity") > 1) {

                    // Decrease the item quantity by 1
                    menuItem.put("itemQuantity", (int) menuItem.get("itemQuantity") - 1);

                    // Print the item quantity decreased message
                    System.out.println(
                            Utilities.ANSI_RED + "\n\t \t \t \t => Item quantity decreased" + Utilities.ANSI_RESET);
                    return;
                }
                // if itemQuantity is 1, remove the item from the list
                cart.remove(menuItem);
                sales.remove(menuItem);
                System.out.println(
                        Utilities.ANSI_RED + "\n\t \t \t \t => Item removed from your Cart" + Utilities.ANSI_RESET);
                return;
            }
        }

        // If itemNumber is not found in the cart after looping through it
        System.out.println(Utilities.ANSI_RED + "\t \t \t \t => You choosed an item not listed in Your Cart"
                + Utilities.ANSI_RESET);
        System.out.println(Utilities.ANSI_RED + "\t \t \t \t => Please choose an item listed in Your Cart\n\n"
                + Utilities.ANSI_RESET);
        return;
    }

    // Remove item from the database as well
    public void removeItemFromCart_DB() {
        // THIS NEEDS TO BE IMPLEmENTED

    }

    public void showSubtotal() {

        // if the cart is empty
        if (cart.isEmpty()) {

            // No items in the cart, print the error message and return
            System.out.println(Utilities.ANSI_RED + "\t \t \t \t => Your Cart is empty\n\n" + Utilities.ANSI_RESET);
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
        System.out.println(
                Utilities.ANSI_RED + "\t \t \t \t => Total Bill: " + totalBill + "\n\n" + Utilities.ANSI_RESET);
        return;
    }

    // Method to load the cart from the database
    public void loadCartFromDB() {
        // THIS NEEDS TO BE IMPLEMENTED
        // Create strings for connection
        String uname = "root";
        String pass = "RMS.java";
        final String url = "jdbc:mysql://localhost:3306/restaurant";

        try (Connection con = DriverManager.getConnection(url, uname, pass)) {

            String table = "cart_" + LoginToAccount.UserId;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + table);

            while (rs.next()) {
                final int itemNumber = (int) rs.getInt("itemNumber");
                final String itemName = (String) rs.getString("itemName");
                final int itemPrice = (int) rs.getInt("itemPrice");
                final int itemQuantity = (int) rs.getInt("itemPrice");

                item = new HashMap<String, Object>() {
                    {
                        put("itemNumber", itemNumber);
                        put("itemName", itemName);
                        put("itemPrice", itemPrice);
                        put("itemQuantity", itemQuantity);
                    }
                };
                cart.add(item);
            }

        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
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
