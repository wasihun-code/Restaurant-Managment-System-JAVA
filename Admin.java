import java.util.LinkedList;
import java.util.Scanner;

// 1. View total sales
// 2. Add new items in the order menu
// 3. Delete items from the order menu
// 4. Display order menu

public class Admin {
    LinkedList<String> orderMenu = new LinkedList<String>();

    Admin() {
        System.out.println("Welcome Admin");

        System.out.println("1. View total sales");
        System.out.println("2. Add new items in the order menu");
        System.out.println("3. Delete items from the order menu");
        System.out.println("4. Display order menu");

        Scanner sc = new Scanner(System.in);
        int adminChoice = sc.nextInt();

        switch (adminChoice) {
            case 1:
                viewTotalSales();
                break;
            case 2:
                addnewItemsToMenu();
                break;
            case 3:
                deleteItemsToMenu();
                break;
            case 4:
                displayOrderMenu();
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
        sc.close();
    }

    public void viewTotalSales() {

    }

    public void addnewItemsToMenu() {

    }

    public void deleteItemsToMenu() {

    }

    public void displayOrderMenu() {

    }

    public static void main(String[] args) {
        new Admin();
    }
}
