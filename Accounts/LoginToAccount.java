package Accounts;

// Import necessary sql packages
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginToAccount {

    public boolean userIDAuthentication(String UserId) {
        String uname = "root";
        String pass = "RMS.java";
        String url = "jdbc:mysql://localhost:3306/restaurant";

        // Connect to the database and check if the user id exists
        try {
            Connection con = DriverManager.getConnection(url, uname, pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from accounts where UserId = '" + UserId + "'");

            // Extract the user id from the result set
            while (rs.next()) {
                String useridstored = rs.getString("UserId");

                // If the user id exists, return true
                if (useridstored.equals(UserId)) {
                    System.out.println("User ID exists");
                    return true;
                }
            }
        } catch (SQLException e) {
            // Display account not found message
            System.out.println("Account not found");
            return false;
        }
        return true;
    }

    public boolean userPasswordAuthentication(String UserId, String UserPassword) {
        String uname = "root";
        String pass = "RMS.java";
        String url = "jdbc:mysql://localhost:3306/restaurant";

        try {
            Connection con = DriverManager.getConnection(url, uname, pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select UserPassword from accounts where UserId = '" + UserId + "'");

            // Extract the user password from the result set
            while (rs.next()) {
                String userpasswordstored = rs.getString("UserPassword");

                // If the user password exists, return true
                if (userpasswordstored.equals(UserPassword)) {
                    System.out.println("Login Successful");
                    return true;
                }
            }

        } catch (SQLException e) {
            // Display account not found message
            System.out.println("Wrong password");
            return false;
        }
        return true;
    }



    // Test this class
    public static void main(String[] args) {
        LoginToAccount login = new LoginToAccount();
        login.userIDAuthentication("1000");
        login.userPasswordAuthentication("1000", "Wase1234@");
    }
}
