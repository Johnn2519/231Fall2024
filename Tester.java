import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Tester {
    public static void main(String[] args) {
        RobinHoodTrie trie = new RobinHoodTrie();
        Scanner dictionary  = null;
        String wordInput;
        try {
            dictionary = new Scanner(new File(args[0]));
        } catch (FileNotFoundException e) {
            System.out.println("Error: the file given as dictionary doesn't exist!");
            System.exit(0);
        }
        
        Scanner importance  = null;
        try {
        	importance = new Scanner(new File(args[1]));
        } catch (FileNotFoundException e) {
            System.out.println("Error: the file given as sample text doesn't exist!");
            System.exit(0);
        }
        
        while(dictionary.hasNextLine()) {
        	wordInput = dictionary.nextLine();
        	trie.insertWord(wordInput);
        }

        while(importance.hasNext()) {
        	wordInput = importance.next();
        	if(trie.searchWord(wordInput) == true)
        		System.out.println("Successfully detected " + wordInput);
        }
        System.out.println("Checking for words with prefix app:");
        trie.autocomplete("app");
    }
}
