package Restaurant;

// Import necessary util packages
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;

// Import necessary sql packages
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Import Utilities module from Commons package
import Commmons.Utilities;

public class AdminCustomers {
    // Item representation {"itemNumber"=1, "itemName"="Burger", "itemPrice"=100}
    public static HashMap<String, Object> item;

    // An order is represented as an ArrayList of items
    public static ArrayList<HashMap<String, Object>> menu = new ArrayList<HashMap<String, Object>>();

    // A customers cart is represented as an ArrayList of items chosen
    public static ArrayList<HashMap<String, Object>> cart = new ArrayList<HashMap<String, Object>>();

    // An Admins sales is represented as an ArrayList of items sold
    public static ArrayList<HashMap<String, Object>> sales = new ArrayList<HashMap<String, Object>>();

    // LOAD MENU FROM DATABASE
    public AdminCustomers() {

        // Load the menu from the database when user logs in
        dbLoadMenu();
        
        // Load the sales from the database when user logs in
        dbLoadSales();

        // Load the cart from the database when user logs in
        dbLoadCart();
    }

    // Method to display the menu in a tabular format
    public void displayMenu() {

        // If menu is empty
        if (menu.isEmpty()) {

            // Display message to admin and return
            System.out.println("\t \t \t \t => Order Menu is empty");
            return;
        }

        // Create a table to display the menu
        Formatter f = new Formatter();

        // Format the table title
        f.format(Utilities.ANSI_GREEN + "%15s %15s %15s %15s\n"
                + Utilities.ANSI_RESET, "", "", "ORDER MENU", "");

        // Format the table title separator
        f.format(Utilities.ANSI_GREEN + "%15s %15s %15s %15s\n"
                + Utilities.ANSI_RESET, "", "---------",
                "-------------", "------------");

        // Format the table header
        f.format(Utilities.ANSI_PURPLE + "%15s %15s %15s %15s\n"
                + Utilities.ANSI_RESET, "",
                "Number", "Item Name", "Item Price");

        // Format the table header separator
        f.format(Utilities.ANSI_GREEN + "%15s %15s %15s %15s\n"
                + Utilities.ANSI_RESET, "",
                "---------", "-------------", "------------");

        // Traverse through the menu
        for (HashMap<String, Object> item1 : menu) {

            // Format the table rows
            f.format(Utilities.ANSI_CYAN + "%14s %14s %15s %10s\n"
                    + Utilities.ANSI_RESET, "", item1.get("itemNumber"),
                    item1.get("itemName"), item1.get("itemPrice"));

            // Format the table row separator
            f.format(Utilities.ANSI_GREEN + "%15s %15s %15s %15s\n"
                    + Utilities.ANSI_RESET, "",
                    "---------", "-------------", "------------");
        }
        // Print the formatted table
        System.out.println(Utilities.ANSI_GREEN + f + Utilities.ANSI_RESET);
        f.close();
    }

    // Method to check if an item is already present in the menu
    public boolean itemExists(String itemName) {

        // If menu have a list of items
        if (!menu.isEmpty()) {

            // Loop through the menu items
            for (HashMap<String, Object> menuItem : menu) {

                // Convert the item name to upper case for comparison
                String itemNameUpper = itemName.toUpperCase();

                // if the name of the item is already present in the menu
                if (menuItem.containsValue(itemNameUpper)) {

                    // return true cause the item already exists
                    return true;
                }
            }
        }

        // Traversed through the menu and didn't find the item so return false
        return false;
    }

    // Method to load the menu from the database
    public void dbLoadMenu() {
        // Save database user name and password as constants
        final String uname = "root";
        final String password = "RMS.java";

        // Create url to connect to mysql database
        final String url = "jdbc:mysql://localhost:3306/restaurant";

        try {
            // Connect to the database
            Connection con = DriverManager.getConnection(url, uname, password);

            // Create a statement
            Statement st = con.createStatement();

            // Execute the query and save the result in a ResultSet
            ResultSet rs = st.executeQuery("select * from ethiopians_menu");

            // Loop through the result set
            while (rs.next()) {

                // Create a new HashMap item for each database table row
                item = new HashMap<String, Object>() {
                    {
                        put("itemNumber", rs.getInt("ID"));
                        put("itemName", rs.getString("Name"));
                        put("itemPrice", rs.getInt("Price"));
                    }
                };

                // Check if item already exists in the menu
                if (!itemExists(item.get("itemName").toString())) {

                    // Add the item to the menu if it doesn't exist
                    menu.add(item);
                } else {

                    // Skip the item if it already exists
                    continue;
                }
            }
            // close the connection
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    // Method to load the sales from the database
    public void dbLoadSales() {

    }

    // Method to load the cart from the database
    public void dbLoadCart() {

    }

}