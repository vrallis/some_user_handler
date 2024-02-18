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
        //System.out.println("Generated username: " + username);
        

        return username;
    }

    public String generatePassword(int length) {
        String specialCharacters = "!@#$%^&*()";
        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
    
        StringBuilder password = new StringBuilder();
        Random random = new Random();
    
        password.append(specialCharacters.charAt(random.nextInt(specialCharacters.length())));
        password.append(upperCaseLetters.charAt(random.nextInt(upperCaseLetters.length())));
        password.append(numbers.charAt(random.nextInt(numbers.length())));
    
        String allPossibleCharacters = specialCharacters + upperCaseLetters + lowerCaseLetters + numbers;
        for (int i = 3; i < length; i++) {
            password.append(allPossibleCharacters.charAt(random.nextInt(allPossibleCharacters.length())));
        }
    
        char[] passwordChars = password.toString().toCharArray();
        for (int i = 0; i < passwordChars.length; i++) {
            int randomIndex = random.nextInt(passwordChars.length);
            char temp = passwordChars[i];
            passwordChars[i] = passwordChars[randomIndex];
            passwordChars[randomIndex] = temp;
        }
    
        String finalPassword = new String(passwordChars);
        //System.out.println("Generated password: " + finalPassword);
    
        return finalPassword;
    }

    private String getName() {
        String line = IoHandler.extractRandomLine("commonnames.csv");
        if (line != null) {
            String[] userArray = line.split(",");
            String name = userArray[1];
            return name;
        }
        return null;
    }


    
    
}
