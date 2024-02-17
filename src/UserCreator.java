import java.util.Random;

public class UserCreator {

    public static void main(String[] args) {
        UserCreator userCreator = new UserCreator();
        String username = userCreator.generateUsername(6);
        String password = userCreator.generatePassword(12);
    }

    public String generateUsername(int suffixLength) {
        String name = getName();

        String usernameSuffix = "0123456789_.";
        Random random = new Random();
        String username = name.replaceAll("\\s+", ""); // Remove spaces from the name

        StringBuilder usernameBuilder = new StringBuilder(username);
        //int specialCharCount = random.nextInt(6); // Generate a random count of special characters (0-5)
        for (int i = 0; i <= suffixLength; i++) {
            int index = random.nextInt(usernameSuffix.length());
            char specialChar = usernameSuffix.charAt(index);
            usernameBuilder.append(specialChar);
        }
        username = usernameBuilder.toString();
        System.out.println("Generated username: " + username);
        

        return username;
    }

    public String generatePassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        StringBuilder password = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        System.out.println("Generated password: " + password.toString());

        return password.toString();
    }

    private String getName() {
        String line = IoHandler.extractRandomLine("boysNames.csv");
        if (line != null) {
            String[] userArray = line.split(",");
            String name = userArray[1];
            return name;
        }
        return null;
    }


    
    
}
