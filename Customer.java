import java.util.Scanner;
// 1. Place your order
// 2. View your ordered items
// 3. Delete an item from order
// 4. Display final bill

public class Customer {
    
      public Customer() {
            System.out.println("Welcome Customer");
    
            System.out.println("1. Place your order");
            System.out.println("2. View your ordered items");
            System.out.println("3. Delete an item from order");
            System.out.println("4. Display final bill");
    
            Scanner sc = new Scanner(System.in);
            int customerChoice = sc.nextInt();
    
            switch (customerChoice) {
                case 1:
                    placeOrder();
                    break;
                case 2:
                    viewOrderedItems();
                    break;
                case 3:
                    deleteItemFromOrder();
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
    
        public void placeOrder() {
    
        }
    
        public void viewOrderedItems() {
    
        }
    
        public void deleteItemFromOrder() {
    
        }
    
        public void displayFinalBill() {
    
        }
    
        public static void main(String[] args) {
            Customer c = new Customer();
            
        }
}