import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class RandomWordGenerator {

    // Generate word length based on a lognormal distribution between 1 and 26
    private static int generateWordLength(double mean, double stddev) {
        Random random = new Random();

        // Generate a lognormal distributed number
        double logNormal = Math.exp(mean + stddev * random.nextGaussian());

        // Scale the length to be between 1 and 26 (approximately)
        int wordLength = (int) Math.round(logNormal);

        // Ensure that the word length is within the bounds of 1 to 26 characters
        if (wordLength < 1) {
            wordLength = 1;
        } else if (wordLength > 26) {
            wordLength = 26;
        }

        return wordLength;
    }

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

    public static void generateWordsToFile(int m, double mean, double stddev, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (int i = 0; i < m; i++) {
                // Get word length based on lognormal distribution
                int wordLength = generateWordLength(mean, stddev);
                String word = generateWord(wordLength, wordLength);
                writer.write(word);
                writer.newLine();
            }
            System.out.println("Words successfully written to file: " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        int m = 100000;  // Number of words
        double mean = 3.0;  // Mean of the underlying normal distribution
        double stddev = 1.0; // Standard deviation of the underlying normal distribution
        String filePath = "100000.txt";

        // Generate words and write to file
        generateWordsToFile(m, mean, stddev, filePath);
    }
}
