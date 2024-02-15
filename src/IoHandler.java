import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class IoHandler {

    public static void createFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeToFile(String filePath, String content) {
        try {
            FileWriter writer = new FileWriter(filePath, true);
            writer.write(content + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String searchAndReturnLine(String filePath, String searchKeyword) {
        try {
            Scanner scanner = new Scanner(new File(filePath));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(searchKeyword)) {
                    return line;
                }
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean searchUser(String filePath, String searchKeyword) {
        try {
            Scanner scanner = new Scanner(new File(filePath));
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


        public static String extractRandomLine(String filePath) {
            try {
                List<String> lines = Files.readAllLines(Paths.get(filePath));
                if (!lines.isEmpty()) {
                    int randomIndex = new Random().nextInt(lines.size());
                    return lines.get(randomIndex);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
            
            
        }
    }
