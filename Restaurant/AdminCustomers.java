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
    
    // Item representation is as hashmap that looks like this
    // => {"itemNumber"=1, "itemName"="Burger", "itemPrice"=100, "itemQuantity"=1}
    public static HashMap<String, Object> item;

    // An order is represented as an ArrayList of items
    public static ArrayList<HashMap<String, Object>> menu = new ArrayList<HashMap<String, Object>>();

    // A customers cart is represented as an ArrayList of items chosen
    public static ArrayList<HashMap<String, Object>> cart = new ArrayList<HashMap<String, Object>>();

    // An Admins sales is represented as an ArrayList of items sold
    public static ArrayList<HashMap<String, Object>> sales = new ArrayList<HashMap<String, Object>>();
    
    // Store the table to be loaded and then modified or whatever
    static String tableToLoad;

    // LOAD MENU FROM DATABASE
    public AdminCustomers() {

        // print the countries list to the console
        printCountriesList();

        // Enter country to load the menu
        int choice = Utilities.validateAndReturnUserInput();

        // Print four countries
        switch (choice) {
            case 1:
                tableToLoad = "ethiopians_menu";
                break;
            case 2:
                tableToLoad = "indians_menu";
                break;
            case 3:
                tableToLoad = "syrians_menu";
                break;
            case 4:
                tableToLoad = "brasilians_menu";
                break;
            default:
                System.out.println(Utilities.ANSI_RED + "\t\t\t\t => Invalid Choice" + Utilities.ANSI_RESET);
                System.out.println(Utilities.ANSI_RED + "\t\t\t\t => Exiting..." + Utilities.ANSI_RESET);
                System.exit(1);
                return;
        }
        
        // Load the menu from the database when user logs in
        loadMenu_From_DB(tableToLoad);
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
    public boolean itemExistsInTheMenu(String itemName) {

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
    public void loadMenu_From_DB(String tableToLoad) {

        try {
            // Connect to the database
            Connection con = DriverManager.getConnection(Utilities.url, Utilities.uname, Utilities.pass);

            // Create a statement
            Statement st = con.createStatement();

            // Execute the query and save the result in a ResultSet
            ResultSet rs = st.executeQuery("select * from " + tableToLoad);

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
                if (!itemExistsInTheMenu(item.get("itemName").toString())) {

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

    // Print the countries list for the user to choose from
    public void printCountriesList() {

        // Clear the screen for the next menu
        Utilities.clearScreen();
        
        // Print the countries list
        System.out.println(Utilities.ANSI_VIOLET + "\n\t\t\t\t => 1. ETHIOPIA" + Utilities.ANSI_RESET);
        System.out.println(Utilities.ANSI_VIOLET + "\t\t\t\t => 2. INDIA" + Utilities.ANSI_RESET);
        System.out.println(Utilities.ANSI_VIOLET + "\t\t\t\t => 3. SYRIA" + Utilities.ANSI_RESET);
        System.out.println(Utilities.ANSI_VIOLET + "\t\t\t\t => 4. BRAZIL" + Utilities.ANSI_RESET);
    }
}