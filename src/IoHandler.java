import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class IoHandler {

    public static void createFile(String FILE_PATH) {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeToFile(String FILE_PATH, String content) {
        try {
            FileWriter writer = new FileWriter(FILE_PATH, true);
            writer.write(content + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String searchAndReturnLine(String FILE_PATH, String searchKeyword) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(searchKeyword)) {
                    return line;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean searchUser(String FILE_PATH, String searchKeyword) {
        try {
            Scanner scanner = new Scanner(new File(FILE_PATH));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(searchKeyword)) {
                    return true;
                }
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String extractRandomLine(String FILE_PATH) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            if (!lines.isEmpty()) {
                int randomIndex = new Random().nextInt(lines.size());
                return lines.get(randomIndex);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static boolean replaceLine(String FILE_PATH, String oldLine, String newLine) {
        try {
            List<String> fileContent = Files.readAllLines(Paths.get(FILE_PATH));
            for (int i = 0; i < fileContent.size(); i++) {
                if (fileContent.get(i).equals(oldLine)) {
                    fileContent.set(i, newLine);
                    Files.write(Paths.get(FILE_PATH), fileContent);
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String extractUsernameFromLine(String line) {
        String[] userArray = line.split(",");
        String username = userArray[0];
        return username;
    }

    public static String extractHashedPasswordFromLine(String line) {
        String[] userArray = line.split(",");
        String hashedPassword = userArray[1];
        return hashedPassword;
    }

    public static String extractSaltFromLine(String line) {
        String[] userArray = line.split(",");
        String salt = userArray[2];
        return salt;
    }
        
}

