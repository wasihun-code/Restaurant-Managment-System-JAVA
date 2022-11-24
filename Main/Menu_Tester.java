package Main;
import Restaurant.AdminCustomers;
// import AdminCutsomers.*;



public class Menu_Tester{
    public void display(){
        System.out.println("Hello");
        AdminCustomers.displayMenu();
    }
    public static void main(String[] args){
        Menu_Tester mt = new Menu_Tester();
        
       new AdminCustomers();
       mt.display();
     
        
    }
    
}
