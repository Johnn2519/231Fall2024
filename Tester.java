import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Tester {
	public static void main(String[] args) {
		Trie statTrie = new Trie();
		RobinHoodTrie trie = new RobinHoodTrie();
		MinHeap minHeap = null;
		Scanner dictionary = null;
		Scanner importance = null;
		Scanner scan = new Scanner(System.in);
		String wordInput;
		String searchWord;

		try {
			dictionary = new Scanner(new File("dictionary.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Error: the file given as dictionary doesn't exist!");
			System.exit(0);
		}

		try {
			importance = new Scanner(new File("importance.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Error: the file given as sample text doesn't exist!");
			System.exit(0);
		}

		while (dictionary.hasNextLine()) {
			wordInput = dictionary.nextLine().toLowerCase().trim();
			if (wordInput.isEmpty() || !wordInput.matches("^[a-z]+$")) { 
				continue;
			}
			System.out.println("Inserting: " + wordInput);
			trie.insertWord(wordInput);
		}
		
		while (importance.hasNext()) {
			wordInput = importance.next().toLowerCase().trim();
			if (wordInput.isEmpty() || !wordInput.matches("^[a-z]+$")) { 
				continue;
			}
			System.out.println("Searching for: " + wordInput);
			if (trie.searchWord(wordInput)) {
				System.out.println("Successfully detected " + wordInput);
			} else {
				System.out.println("Not found: " + wordInput);
			}
		}
		

		System.out.print("Search with (one) word: ");
		searchWord = scan.nextLine();
		searchWord = searchWord.toLowerCase();

		System.out.print("Choose (>0) amount of suggested words for " + searchWord + ": ");
		int k = scan.nextInt();
		while (k <= 0) {
			System.out.println("Invalid amount given!\n Try again: ");
			k = scan.nextInt();
		}
		minHeap = new MinHeap(k);
		System.out.println("Checking for top " + k + " reccomended words for " + searchWord + ":");
		trie.autocomplete(searchWord, minHeap);

		scan.close();
	}
}