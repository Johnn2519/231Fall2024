import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class RandomWordGenerator {

    private static String generateWord(int minLen, int maxLen) {
        Random random = new Random();
        int wordLength = random.nextInt(maxLen - minLen + 1) + minLen;
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < wordLength; i++) {
            char randomChar = (char) ('a' + random.nextInt(26));
            word.append(randomChar);
        }
        return word.toString();
    }

    public static void generateWordsToFile(int m, int x, int y, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (int i = 0; i < m; i++) {
                String word = generateWord(x, y);
                writer.write(word);
                writer.newLine();
            }
            System.out.println("Words successfully written to file: " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        int m = 100; // Num of words
        int x = 3;   // Min
        int y = 3;   // Max
        String filePath = "random_words.txt";

        // Generate words and write to file
        generateWordsToFile(m, x, y, filePath);
    }
}
