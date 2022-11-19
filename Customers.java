import java.util.Scanner;

// 1. Place your order
// 2. View your ordered items
// 3. Delete an item from order
// 4. Display final bill
// 5. Back To Main Menu

public class Customers {
    Customers() {
        System.out.println("Welcome Customer");

        System.out.println("1. Place Your Order");
        System.out.println("2. View Your Ordered Items");
        System.out.println("3. Delete an item from order");
        System.out.println("4. Display final bill");

        Scanner sc = new Scanner(System.in);
        int customerChoice = sc.nextInt();

        switch (customerChoice) {
            case 1:
                placeYourOrder();
                break;
            case 2:
                viewYourOrderedItems();
                break;
            case 3:
                deleteAnItemFromOrder();
                break;
            case 4:
                displayFinalBill();
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
        sc.close();
    }

    public void placeYourOrder() {

    }

    public void viewYourOrderedItems() {

    }

    public void deleteAnItemFromOrder() {

    }

    public void displayFinalBill() {

    }

    public static void main(String[] args) {
        Customers c = new Customers();
    }
}
