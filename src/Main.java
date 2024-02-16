import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        for (int i = 0; i < 1000; i++) {
            UserCreator userCreator = new UserCreator();
            String username = userCreator.generateUsername(6);
            String password = userCreator.generatePassword(12);
            User.registerUser(username, password);
        }
    }
}
