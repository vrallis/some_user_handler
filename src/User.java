import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class User {

    private static final String FILE_PATH = "users.csv";

    public static void main(String[] args) throws NoSuchAlgorithmException {
        loginUser("bill2","Bill!1llllllll");
        registerUser("bill5","Bill!5*");
        
    }

    // store values in a file
    private static void storeUser(String username, String hashedPassword, String salt) {
        IoHandler.createFile(FILE_PATH);
        String user = username + "," + hashedPassword + "," + salt;
        IoHandler.writeToFile(FILE_PATH, user);
    }

    // register user
    public static void registerUser(String username, String password) throws NoSuchAlgorithmException {
        // check that password has at least 1 number and 1 special character and is at
        // least 8 characters long
        boolean hasSpace = Pattern.compile(" ").matcher(username).find();

        if (checkUsernameValidity(username) == false || checkPasswordValidity(password) == false || hasSpace == true) {
            System.out.println("Username is invalid.");
            return;
        }

        String salt = Hash.generateSalt();
        String hashedPassword = Hash.hashAndSalt(password, salt);
        storeUser(username, hashedPassword, salt);
        //System.out.println("User registered successfully.");
    }

    // login user
    public static boolean loginUser(String username, String password) throws NoSuchAlgorithmException {
        String user = IoHandler.searchAndReturnLine(FILE_PATH, username);
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

    public static boolean resetForgottenPassword(String username, String newPassword) throws NoSuchAlgorithmException {
        if (checkUsernameValidity(username)==false || checkPasswordValidity(newPassword)==false) {
            return false;
        }

        IoHandler.


        String user = IoHandler.searchAndReturnLine(FILE_PATH, username);
        if (user!=null) {
            String[] userArray = user.split(",");
            String salt = userArray[2];
            String hashedPassword = Hash.hashAndSalt(newPassword, salt);
            String newUser = username + "," + hashedPassword + "," + salt;
            IoHandler.replaceLine(FILE_PATH, user, newUser);
            return true;
        }
    }

    public static boolean checkUsernameValidity(String username) {
        boolean hasSpace = Pattern.compile(" ").matcher(username).find();
        if (username.length() <= 3 || IoHandler.searchUser(FILE_PATH, username) == true || hasSpace == true) {
            return false;
        }
        return true;

    }

    public static boolean checkPasswordValidity(String password) {
        boolean hasNumber = Pattern.compile("[0-9]").matcher(password).find();
        boolean hasSpecialChar = Pattern.compile("[^a-zA-Z0-9]").matcher(password).find();
        boolean isLongEnough = password.length() >= 8;
        boolean hasSpace = Pattern.compile(" ").matcher(password).find();
        
        if (hasNumber && hasSpecialChar && isLongEnough && !hasSpace) {
            return true;
        }
        return false;
    }

}
