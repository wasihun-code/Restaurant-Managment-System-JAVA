// package CountriesTextFile;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class createAccount extends loginAbstract {
    Scanner in = new Scanner(System.in);

    // receive new account DETAILS
    public void readAccountDetail() {

        // Create the phoneNumber, accountNumber and fullName pattern to match to
        Pattern phoneNumberPattern = Pattern.compile("^[0-9]{2}$");
        Pattern accountNumberPattern = Pattern.compile("^[0-9]{2}$");
        Pattern fullNamePattern = Pattern.compile("^[a-zA-Z]{2,10} [a-zA-z]{2,10}$");
        Pattern passwordPattern = Pattern.compile(
                "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{6,8}$");

        // Create matcher to find if the pattern exists in the user input
        // for phoneNumber, accountNumber and fullName
        Matcher phoneMatcher = null, accounMatcher = null, nameMatcher = null, passwordMatcher = null;
        String prompt;

        ownerName = verifyUserInputREGEX(fullNamePattern, nameMatcher,
                ownerName, "\t Full name('firstname lastname(minimum 2 characters each'): ");
        phoneNumber = verifyUserInputREGEX(phoneNumberPattern, phoneMatcher,
                phoneNumber, "\t Phone Number(10 digits): ");
        bankAccount = verifyUserInputREGEX(accountNumberPattern, accounMatcher,
                bankAccount, "\t Acccount Number(10 digits): ");

        prompt = "\nMinimum 6 and maximum 8 characters\n";
        prompt += "at least one uppercase letter, one lowercase letter\n";
        prompt += "one number and one special character\n";
        prompt += "\t Enter Password: ";
        password = verifyUserInputREGEX(passwordPattern, passwordMatcher,
                password, prompt);

    }

    // Write userid, phonenumber, password, bankaccount to file
    public void writeOnFile(String fileToOpen) {
        try (BufferedWriter filewrite = new BufferedWriter(
                new FileWriter(fileToOpen, true))) {
            filewrite.newLine();
            int userId = Integer.parseInt(ID) + 1;
            ID = userId + "";
            filewrite.append("ID: " + userId + "\n");
            filewrite.append("Password: " + password + "\n");
            filewrite.append("Full Name: " + ownerName + "\n");
            filewrite.append("Phone Number: " + phoneNumber + "\n");
            filewrite.append("Bank Account: " + bankAccount + "\n");
            filewrite.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // Displaying detail after creating a new account
    public void displayAccountDetail() {
        System.out.println("\nAccount Created successfully!\n");
        System.out.println("YOUR DETAILS:");
        System.out.println("         ID: " + ID);
        System.out.println("   PassWord: " + password);
        System.out.println("       Name: " + ownerName);
        System.out.println("    PhoneNo: " + phoneNumber);
        System.out.println("BankAccount: " + bankAccount);
    }

    // Use regular expression to match user inputs: phone, name and accountnumber
    public String verifyUserInputREGEX(Pattern pattern, Matcher matcher,
            String string, String prompt) {
        do {
            System.out.print(prompt);
            string = in.nextLine();
            matcher = pattern.matcher(string);
        } while (!(matcher.find()));
        return string;
    }

}
