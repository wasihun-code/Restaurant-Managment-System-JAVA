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

            // Clear sales and load it
            sales.clear();

            // Load the sales menu from the database when Admin logs in
            loadSales_DB();

            adminMainMenu();
            boolean goBackToMainMenu = false;

            // Validate user input with try-catch
            int choice = Utilities.validateAndReturnUserInput();

            if (choice == -1234) {
                continue;
            }

            Utilities.clearScreen();
            switch (choice) {
                case 1:
                    viewTotalSales();
                    break;
                case 2:
                    addItemToMenu();
                    break;
                case 3:
                    deleteItemFromMenu();
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
                    System.out.println(Utilities.ANSI_RED + "\n\t\t\t\t => Thank you for using our service"
                            + Utilities.ANSI_RESET);
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
        displaySalesMenu();

        // Intialize total sales to 0 and total items sold to 0
        int totalSales = 0;
        int totalItems = 0;

        // Loop through admin's sales list
        for (HashMap<String, Object> salesItem : sales) {

            // Extract the price of the item
            int item_Price = (int) salesItem.get("itemPrice");

            // Extract its quantity
            int item_Quantity = (int) salesItem.get("itemQuantity");

            // Calculate total sales and increment number of quantity
            totalSales += item_Price * item_Quantity;
            totalItems += item_Quantity;
        }

        // Print total items sold and total sales generated
        System.out.println(Utilities.ANSI_RED + "\t\t\t\t => Total Items Sold: " + totalItems + Utilities.ANSI_RESET);
        System.out.println(Utilities.ANSI_RED + "\t\t\t\t => Total Sales: " + totalSales + Utilities.ANSI_RESET);
        return;
    }

    public void addItemToMenu() {

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
        if (itemExistsInTheMenu(itemName.toUpperCase())) {
            System.out.println(Utilities.ANSI_RED + "\n\t\t\t\t => Item already exists!" + Utilities.ANSI_RESET);
            return;
        }

        System.out.print(Utilities.ANSI_CYAN + "\t\t\t\t => Enter item price: " + Utilities.ANSI_RESET);
        final int itemPrice = sc.nextInt();
        sc.nextLine(); // Consume new line character

        // Add item detail to the hashmap
        item = new HashMap<String, Object>() {
            {
                put("itemNumber", itemNumber);
                put("itemName", itemName.toUpperCase());
                put("itemPrice", itemPrice);
            }
        };
        // Add item to the menu
        menu.add(item);

        // Add the item to the menu database as well
        addItemToMenu_DB(item);

        // Print success message
        System.out.println(Utilities.ANSI_RED + "\n\t\t\t\t => Item added to the menu " + Utilities.ANSI_RESET);
    }

    public void addItemToMenu_DB(HashMap<String, Object> item) {
        // Create Strings for Connection

        // Create string for connection
        final String uname = "root";
        final String pass = "RMS.java";
        final String url = "jdbc:mysql://localhost:3306/restaurant";

        // Connect to the database and close it after you are done
        try (Connection con = DriverManager.getConnection(url, uname, pass)) {
            String query_insert_menu = "INSERT INTO ethiopians_menu " + "(ID, Name, Price) VALUES(?, ?, ?)";

            PreparedStatement pstmt = con.prepareStatement(query_insert_menu);
            pstmt.setInt(1, (int) item.get("itemNumber"));
            pstmt.setString(2, (String) item.get("itemName"));
            pstmt.setInt(3, (int) item.get("itemPrice"));

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error : " + ex.getMessage());
        }
    }

    public void deleteItemFromMenu() {

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
        sc.nextLine(); // Consume new line character

        // Loop through the menu items
        for (HashMap<String, Object> menuItem : menu) {

            // if what user chose is in the menu
            if (menuItem.containsValue(itemNumber)) {

                // Then remove it
                menu.remove(menuItem);

                // Display success message to admin and return
                System.out.println(
                        Utilities.ANSI_RED + "\n\t\t\t\t => Item deleted successfully." + Utilities.ANSI_RESET);
                return;
            }
        }

        // If what admin chose isn't in the menu. Prompt with an error message and
        // return
        System.out.println(Utilities.ANSI_RED + "\n\t\t\t\t => Item not found." + Utilities.ANSI_RESET);
        System.out.println(Utilities.ANSI_RED + "\t\t\t\t => Please Enter a valid number again" + Utilities.ANSI_RESET);
        return;
    }

    public void deleteItemFromMenu_DB() {
        // THIS NEEDS TO BE IMPLEMENTED

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
            f.format(Utilities.ANSI_CYAN + "%14s %14s %15s %10s %15s\n" + Utilities.ANSI_RESET, "",
                    item1.get("itemNumber"),
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

    // Method to load the sales from the database
    public void loadSales_DB() {


        // Connect to the database and close it after you are done
        try (Connection con = DriverManager.getConnection(Utilities.url, Utilities.uname, Utilities.pass)) {

            // Create statement
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM sales");

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
                sales.add(item);
            }

        } catch (SQLException ex) {
            System.out.println("Error : " + ex.getMessage());
        }

    }

    public void adminMainMenu() {
        System.out.println(Utilities.ANSI_YELLOW + "\n\t \t \t \t 1. View total sales" + Utilities.ANSI_RESET);
        System.out.println(
                Utilities.ANSI_YELLOW + "\t \t \t \t 2. Add new items in the order menu" + Utilities.ANSI_RESET);
        System.out.println(
                Utilities.ANSI_YELLOW + "\t \t \t \t 3. Delete items from the order menu" + Utilities.ANSI_RESET);
        System.out.println(Utilities.ANSI_YELLOW + "\t \t \t \t 4. Display order menu" + Utilities.ANSI_RESET);
        System.out.println(Utilities.ANSI_YELLOW + "\t \t \t \t 5. Display sales menu" + Utilities.ANSI_RESET);
        System.out.println(Utilities.ANSI_YELLOW + "\t \t \t \t 6. Go back to main menu" + Utilities.ANSI_RESET);
        System.out.println(Utilities.ANSI_YELLOW + "\t \t \t \t 7. Exit" + Utilities.ANSI_RESET);
    }
}
