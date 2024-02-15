import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class User {

    private static String filePath = "users.csv";

    public static void main(String[] args) throws NoSuchAlgorithmException {
        //loginUser("andrei", "andreiissexy");
        loginUser("andrei2005", "andreiissexy1&");
    }

    // store values in a file
    private static void storeUser(String username, String hashedPassword, String salt) {
        IoHandler.createFile(filePath);
        String user = username + "," + hashedPassword + "," + salt;
        IoHandler.writeToFile(filePath, user);
    }

    // register user
    public static void registerUser(String username, String password) throws NoSuchAlgorithmException {
        if (IoHandler.searchUser(filePath, username) == true) {
            System.out.println("Username already exists.");
            return;
        }
        // check that username is at least 4 characters long
        if (username.length() < 4) {
            System.out.println("Username is needs to be at least 4 characters long.");
            return;
        }
        // check that password has at least 1 number and 1 special character and is at
        // least 8 characters long
        boolean hasNumber = Pattern.compile("[0-9]").matcher(password).find();
        boolean hasSpecialChar = Pattern.compile("[^a-zA-Z0-9]").matcher(password).find();
        boolean isLongEnough = password.length() >= 8;

        if (hasNumber && hasSpecialChar && isLongEnough) {
            System.out.println("Password is valid.");
        } else {
            System.out.println("Password is not valid.");
            return;
        }

        String salt = Hash.generateSalt();
        String hashedPassword = Hash.hashAndSalt(password, salt);
        storeUser(username, hashedPassword, salt);
        System.out.println("User registered successfully.");
    }

    // login user
    public static boolean loginUser(String username, String password) throws NoSuchAlgorithmException {
        String user = IoHandler.searchAndReturnLine(filePath, username);
        if (user != null) {
            String[] userArray = user.split(",");
            String salt = userArray[2];
            String hashedPassword = userArray[1];
            if (Hash.verifyHash(password, salt, hashedPassword) == true) {
                System.out.println("Login successful");
                return true;
            }
        }
        System.out.println("Login failed");
        return false;
    }
}
