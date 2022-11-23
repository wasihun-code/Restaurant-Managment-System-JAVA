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

// Import necessary project packages
import Accounts.LoginToAccount;
import Commmons.Utilities;

public class Customer extends AdminCustomers {

    // Scanner to read user input
    Scanner sc = new Scanner(System.in);

    public Customer() {
        Utilities.clearScreen();
        System.out.println("\n");
        System.out.println(Utilities.ANSI_GREEN + "\n\t\t\t\t  Welcome Customer\n" +
                Utilities.ANSI_RESET);
        while (true) {

            // Clear cart and load it
            cart.clear();

            // Load the user cart from the database
            loadCartFrom_DB();

            customerMainMenu();

            // Store user choice of going back to main menu
            boolean goBackToMainMenu = false;

            // Validate user input with try-catch
            int choice = Utilities.validateAndReturnUserInput();

            if (choice == -1234) {
                continue;
            }

            Utilities.clearScreen();
            switch (choice) {
                case 1:
                    addItemToCartAndSales();
                    break;
                case 2:
                    viewCart();
                    break;
                case 3:
                    removeItemFromCartAndSales();
                    break;
                case 4:
                    showSubtotal();
                    break;
                case 5:
                    // Go back to main menu
                    goBackToMainMenu = true;
                    return;
                case 6:
                    System.out.println(Utilities.ANSI_GREEN + "\n\t\t\t\t => Thank you for using our service"
                            + Utilities.ANSI_RESET);
                    System.exit(0);
                    break;
                default:
                    System.out.println(Utilities.ANSI_GREEN + "\n\t\t\t\t => Invalid choice" +
                            Utilities.ANSI_RESET);
                    break;
            }
            // If user wants to go back to main menu
            if (goBackToMainMenu) {
                break;
            }
        }
    }

    // Add item to cart and sales arrayLists
    public void addItemToCartAndSales() {
        // Display menu to user to be able to select items
        displayMenu();

        // Get the item number from user
        System.out.print(Utilities.ANSI_VIOLET + "\n\t\t\t\t => Enter the item Number: " + Utilities.ANSI_RESET);
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
                            System.out.println(Utilities.ANSI_GREEN + "\n\t \t \t \t => Item quantity increased**\n"
                                    + Utilities.ANSI_RESET);
                            // Add the item to users cart in the database as well
                            // This function will also increment the quantity if the item is already in the
                            // cart
                            addItemToCartAndSales_DB(menuItem, true);
                            return;
                        }
                    }
                }
                // If the item is not in the users cart make the quantity 1
                menuItem.put("itemQuantity", 1);

                // Then add the item to the users cart
                cart.add(menuItem);

                // Add the item to users cart in the database as well
                addItemToCartAndSales_DB(menuItem, false);

                // Also add the item to the sales menu as if the user has bought it
                sales.add(menuItem);

                // Print the item added to cart
                System.out.println(
                        Utilities.ANSI_GREEN + "\n\t \t \t \t => Item added to your Cart**\n" + Utilities.ANSI_RESET);
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
    public void addItemToCartAndSales_DB(HashMap<String, Object> menuItem, boolean itemExists) {
        // Connect with the database using try-resource
        try (Connection con = DriverManager.getConnection(Utilities.url, Utilities.uname, Utilities.pass)) {

            // Create a statement to execute the query

            if (itemExists) {
                // Store table name of users cart
                String cart_table = "cart_" + LoginToAccount.UserId;

                // If the item is already in the cart just increase the quantity
                String query_cart = "UPDATE " + cart_table + " SET itemQuantity = itemQuantity + 1 WHERE itemName = ?";

                // Also add it to sales database
                String query_sales = "UPDATE sales " + "  SET itemQuantity = itemQuantity + 1 WHERE itemName = ?";

                // Create a prepared statement to execute the query for cart
                PreparedStatement st_cart = con.prepareStatement(query_cart);

                // Create a prepared statement to execute the query for sales
                PreparedStatement st_sales = con.prepareStatement(query_sales);

                // Set the values for the prepared statement
                st_cart.setString(1, (String) menuItem.get("itemName"));
                st_sales.setString(1, (String) menuItem.get("itemName"));

                // Execute the query
                st_cart.executeUpdate();
                st_sales.executeUpdate();

                return;
            }

            // Create a query to insert the item to the cart
            String query_cart = "INSERT INTO " + "cart_" + LoginToAccount.UserId +
                    "(itemNumber, itemName, itemPrice, itemQuantity) VALUES(?,?,?,?)";
            PreparedStatement pstmt_cart = con.prepareStatement(query_cart);

            // Create a query to insert the item to the sales
            String query_sales = "INSERT INTO sales" +
                    "(itemNumber, itemName, itemPrice, itemQuantity, userId) VALUES(?,?,?,?,?)";
            PreparedStatement pstmt_sales = con.prepareStatement(query_sales);

            pstmt_cart.setInt(1, (int) menuItem.get("itemNumber"));
            pstmt_cart.setString(2, (String) menuItem.get("itemName"));
            pstmt_cart.setInt(3, (int) menuItem.get("itemPrice"));
            pstmt_cart.setInt(4, 1);

            pstmt_sales.setInt(1, (int) menuItem.get("itemNumber"));
            pstmt_sales.setString(2, (String) menuItem.get("itemName"));
            pstmt_sales.setInt(3, (int) menuItem.get("itemPrice"));
            pstmt_sales.setInt(4, 1);
            pstmt_sales.setString(5, LoginToAccount.UserId);

            // Execute the query
            pstmt_cart.executeUpdate();
            pstmt_sales.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
            return;
        }
    }

    // Display the cart to the user
    public void viewCart() {

        // If the cart is empty
        if (cart.isEmpty()) {

            // Print the error message and return
            System.out.println(Utilities.ANSI_GREEN + "\t \t \t \t => Your Cart is empty \n\n" + Utilities.ANSI_RESET);
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

    // Remove the item from the cart and sales 
    public void removeItemFromCartAndSales() {

        // If the cart is empty
        if (cart.isEmpty()) {

            // Print the error message and return
            System.out.println(Utilities.ANSI_GREEN + "\t \t \t \t => Your Cart is empty\n\n" + Utilities.ANSI_RESET);
            return;
        }
        // Display the cart for the user to choose elements
        viewCart();

        // Read the item number from the user
        System.out.print(Utilities.ANSI_VIOLET + "\t\t\t\t => Enter the item Number: " + Utilities.ANSI_RESET);
        sc = new Scanner(System.in);
        int itemNumberUser = sc.nextInt();
        sc.nextLine(); // consume the new line

        // Loop through the cart to find the itemNumber
        for (HashMap<String, Object> cartItem : cart) {

            // if itemnumber is present in the orderMenu
            if (cartItem.containsValue(itemNumberUser)) {

                // if itemQuantity is more than 1, decrease the quantity by 1
                if ((int) cartItem.get("itemQuantity") > 1) {

                    // Decrease the item quantity by 1
                    System.out.println((String) cartItem.get("itemName") + " Number is greater than one");
                    cartItem.put("itemQuantity", (int) cartItem.get("itemQuantity") - 1);

                    // Decrease the item quantity from the database as well
                    removeItemFromCartAndSales_DB(itemNumberUser, true);

                    // Print the item quantity decreased message
                    System.out.println(
                            Utilities.ANSI_GREEN + "\n\t \t \t \t => Item quantity decreased" + Utilities.ANSI_RESET);
                } else {

                    // itemQuantity is 1, remove the item from the list
                    cart.remove(cartItem);
                    sales.remove(cartItem);

                    // Remove the item from the database as well
                    removeItemFromCartAndSales_DB(itemNumberUser, false);
                    System.out.println(
                            Utilities.ANSI_GREEN + "\n\t \t \t \t => Item removed from your Cart" + Utilities.ANSI_RESET);
                }
                return;
            }
        }

        // If itemNumber is not found in the cart after looping through it
        System.out.println(Utilities.ANSI_GREEN + "\t \t \t \t => You choosed an item not listed in Your Cart"
                + Utilities.ANSI_RESET);
        System.out.println(Utilities.ANSI_GREEN + "\t \t \t \t => Please choose an item listed in Your Cart\n\n"
                + Utilities.ANSI_RESET);
        return;
    }

    // Remove item from the cart and sales in the database table as well
    public void removeItemFromCartAndSales_DB(int itemNumberUser, boolean itemQuantityGreaterThanOne) {
        // THIS NEEDS TO BE IMPLEmENTED

        // Create a connection to the database
        try (Connection con = DriverManager.getConnection(Utilities.url, Utilities.uname, Utilities.pass)) {
            // If the item exists in the database table
            String cart_table = "cart_" + LoginToAccount.UserId;

            if (itemQuantityGreaterThanOne) {
                
                // Create update statement for cart database table
                String query_cart_update = "UPDATE " + cart_table + " SET itemQuantity = itemQuantity - 1 WHERE itemNumber = ?";

                // Create prepared statement for updating the cart database table
                PreparedStatement pstmt_cart_update = con.prepareStatement(query_cart_update);
                pstmt_cart_update.setInt(1, itemNumberUser);

                // Execute statement to decrease item quantity from cartdatabase table
                pstmt_cart_update.executeUpdate();


                // Create update statement for sales database table
                String query_sales_update = "UPDATE sales SET itemQuantity = itemQuantity - 1 WHERE itemNumber = ?";
                PreparedStatement pstmt_sales_update = con.prepareStatement(query_sales_update);
                pstmt_sales_update.setInt(1, itemNumberUser);

                // Execute statement to decrease item quantity from sales database table
                pstmt_sales_update.executeUpdate();

                // Display message for user
                System.out.println("Item Quantity decreased from the database successefully");
                return;
            }

            // Create a statement to remove from the sales table
            String query_sales_remove = "DELETE FROM sales WHERE itemNumber = ?";

            // Create prepared statement for updating the sales database table
            PreparedStatement pstmt_sales_remove = con.prepareStatement(query_sales_remove);
            pstmt_sales_remove.setInt(1, itemNumberUser);

            // Execute statement to remove item from sales database table
            pstmt_sales_remove.executeUpdate();

            // String query_cart_remove = "DELETE FROM ? WHERE itemNumber = ?";
            String query_cart_remove = "DELETE FROM " + cart_table + " WHERE itemNumber = ?";

            // Create prepared statement for updating the cart database table
            PreparedStatement pstmt_cart_remove = con.prepareStatement(query_cart_remove);
            pstmt_cart_remove.setInt(1, itemNumberUser);

            // Execute statement to remove item from cart database table
            pstmt_cart_remove.executeUpdate();

            return;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return;
        }

    }

    // Calculate the total bill amount for the cart
    public void showSubtotal() {

        // if the cart is empty
        if (cart.isEmpty()) {

            // No items in the cart, print the error message and return
            System.out.println(Utilities.ANSI_GREEN + "\t \t \t \t => Your Cart is empty\n\n" + Utilities.ANSI_RESET);
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
                Utilities.ANSI_GREEN + "\t \t \t \t => Total Bill: " + totalBill + "\n\n" + Utilities.ANSI_RESET);
        return;
    }

    // Method to load the cart from the database
    public void loadCartFrom_DB() {
        try (Connection con = DriverManager.getConnection(Utilities.url, Utilities.uname, Utilities.pass)) {

            String table = "cart_" + LoginToAccount.UserId;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + table);

            while (rs.next()) {
                final int itemNumber = (int) rs.getInt("itemNumber");
                final String itemName = (String) rs.getString("itemName");
                final int itemPrice = (int) rs.getInt("itemPrice");
                final int itemQuantity = (int) rs.getInt("itemQuantity");

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
