import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Hash {
    //private static final String ALGORITHM = "AES";
    private static final String HASH_ALGORITHM = "SHA-256";
    private static final int SALT_LENGTH = 16;

    // salts the hashed input and returns the hashed and salted input
    public static String hashAndSalt(String input, String salt) throws NoSuchAlgorithmException {
        String saltedInput = salt + input;
        byte[] hashedBytes = hash(saltedInput.getBytes());
        return Base64.getEncoder().encodeToString(hashedBytes);
    }
    // checks if hash matches the password
    public static boolean verifyHash(String input, String salt, String hashedInput) throws NoSuchAlgorithmException {
        String saltedInput = salt + input;
        byte[] hashedBytes = hash(saltedInput.getBytes());
        String hashedInputToCompare = Base64.getEncoder().encodeToString(hashedBytes);
        return hashedInput.equals(hashedInputToCompare);
    }

    // generates a random salt
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // hashes the input
    private static byte[] hash(byte[] input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
        return digest.digest(input);
    }

    // public static String encrypt(String input, String key) throws Exception {
    //     SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
    //     Cipher cipher = Cipher.getInstance(ALGORITHM);
    //     cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    //     byte[] encryptedBytes = cipher.doFinal(input.getBytes());
    //     return Base64.getEncoder().encodeToString(encryptedBytes);
    // }

    // public static String decrypt(String encryptedInput, String key) throws Exception {
    //     SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
    //     Cipher cipher = Cipher.getInstance(ALGORITHM);
    //     cipher.init(Cipher.DECRYPT_MODE, secretKey);
    //     byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedInput));
    //     return new String(decryptedBytes);
    // }
}
