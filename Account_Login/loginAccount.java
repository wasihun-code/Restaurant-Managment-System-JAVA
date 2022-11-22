import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class loginAccount extends loginAbstract {

    public boolean userIDAuthentication(String fileToOpen, String ID) {
        Matcher idMatcher = null;
        Pattern idPattern = Pattern.compile("^ID: ([0-9]{4})");
        return searchString(fileToOpen, idPattern, idMatcher, ID);
    }

    public boolean userPasswordAuthentication(String fileToOpen, String password) {
        Matcher passwordMatcher = null;
        Pattern passwordPattern = Pattern.compile("^Password: ([0-9]{4})");

        return searchString(fileToOpen, passwordPattern, passwordMatcher, password);
    }

    public boolean searchString(String fileToOpen, Pattern pattern,
            Matcher matcher, String toSearch) {

        // Open the file to be searched using try resource for easy exception handling
        try (BufferedReader reader = new BufferedReader(new FileReader(fileToOpen))) {
            String line = reader.readLine();

            // Scan through the end of file for the regex pattern: idPattern
            while (line != null) {
                line = reader.readLine();
                // System.out.println(line);

                if (line == null)
                    return false;
                matcher = pattern.matcher(line);

                // Check if any match is found
                if (matcher.find()) {

                    // Extract the string from the match
                    String extractedString = matcher.group(1);

                    // Compare the extracted string and the string to be seached
                    if (extractedString.equals(toSearch))
                        return true;
                }
            }
        } catch (IOException ex) {
            System.out.println("Account couldn't be found! Try again letter");
        }
        return false;
    }
}
