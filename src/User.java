import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class User {

    private static final String FILE_PATH = "users.csv";

    public static void main(String[] args) throws NoSuchAlgorithmException {
        //loginUser("bill2","Bill!1llllllll");
        //registerUser("bill5547","Bill!5574*","I like videogames");
        changeBio("bill5547", "I like videogames too much");
        //loginUser("bill5547","Bill!5574*");
        
    }

    // store values in a file
    private static void storeUser(String username, String hashedPassword, String salt, String bio) {
        IoHandler.createFile(FILE_PATH);
        String user = username + "," + hashedPassword + "," + salt + "," + bio;
        IoHandler.writeToFile(FILE_PATH, user);
    }

    // register user
    public static void registerUser(String username, String password, String bio) throws NoSuchAlgorithmException {
        // check that password has at least 1 number and 1 special character and is at
        // least 8 characters long
        boolean hasSpace = Pattern.compile(" ").matcher(username).find();

        if (checkUsernameValidity(username) == false || checkPasswordValidity(password) == false || hasSpace == true) {
            System.out.println("Username is invalid.");
            return;
        }

        if (checkBioValidity(bio) == false) {
            System.out.println("Bio is invalid.");
            return;
        }

        String salt = Hash.generateSalt();
        String hashedPassword = Hash.hashAndSalt(password, salt);
        storeUser(username, hashedPassword, salt, bio);
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
        if (checkPasswordValidity(newPassword)==false) {
            System.out.println("New password is invalid.");
            return false;
        }
        String bio = IoHandler.extractBioFromLine(IoHandler.searchAndReturnLine(FILE_PATH, username));
        IoHandler.removeLineUsingUsername(FILE_PATH, username);
        registerUser(username, newPassword, bio);
        System.out.println("Password reset successful");
        return true;
    }

    public static boolean changeBio(String username, String newBio) {
        if (checkBioValidity(newBio) == false) {
            return false;
        }
        String oldLine = IoHandler.searchAndReturnLine(FILE_PATH, username);
        String[] userArray = oldLine.split(",");
        String newLine = userArray[0] + "," + userArray[1] + "," + userArray[2] + "," + newBio;
        IoHandler.replaceLine(FILE_PATH, oldLine, newLine);
        System.out.println("Bio changed successfully");
        return true;
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

    public static boolean checkBioValidity(String bio) {
        if (bio.length() > 140) {
            return false;
        }
        return true;
    }

}
