package ID1148307.ID1127046;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class RandomWordGenerator {

    // gnerate word length with lognormal dis
    private static int generateWordLength(double mean, double stddev) {
        Random random = new Random();
        double logNormal = Math.exp(mean + stddev * random.nextGaussian());// generate log
        int wordLength = (int) Math.round(logNormal);// round log to int
        if (wordLength < 1) {// check if its within bounds
            wordLength = 1;
        } else if (wordLength > 26) {
            wordLength = 26;
        }
        return wordLength;
    }

    // build the word
    private static String generateWord(int minLen, int maxLen) {
        Random random = new Random();
        int wordLength = random.nextInt(maxLen - minLen + 1) + minLen;
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < wordLength; i++) {
            char randomChar = (char) ('a' + random.nextInt(26));// pick a random char
            word.append(randomChar);
        }
        return word.toString();
    }

    // generates the word and saves it to a file
    public static void generateWordsToFile(int m, double mean, double stddev, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (int i = 0; i < m; i++) {
                int wordLength = generateWordLength(mean, stddev);// generatethe word length
                String word = generateWord(wordLength, wordLength);// build the word
                writer.write(word);
                writer.newLine();
            }
            System.out.println("written to: " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing to: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        int m = 300000; // num of words

        String filePath = "distributed-dictionary3.txt";

        // generate words and write to file
        generateWordsToFile(m, 3.0, 1.0, filePath);
    }
}