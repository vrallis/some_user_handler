import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        long startTime = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            UserCreator userCreator = new UserCreator();
            String username = userCreator.generateUsername(6);
            String password = userCreator.generatePassword(12);
            User.registerUser(username, password);
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime); // 1000000;

        double seconds = (double)duration / 1_000_000_000.0; // convert to seconds
        System.out.println("Execution time in seconds: " + seconds);
    }
}
