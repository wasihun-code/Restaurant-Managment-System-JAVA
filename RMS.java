import java.util.Scanner;

public class RMS {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.println("1. Admin Login");
                System.out.println("2. Customer Login");
                System.out.println("3. Exit");

                int choice = sc.nextInt();
                sc.nextLine(); // To consume the newline character

                switch (choice) {
                    case 1:
                        new Admin();
                        break;
                    case 2:
                        new Customers();
                        break;
                    case 3:
                        System.out.println("Thank you for using our service");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            }
        }
    }
}