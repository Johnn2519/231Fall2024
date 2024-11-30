import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Tester {
	public static void main(String[] args) {
		RobinHoodTrie trie = new RobinHoodTrie();
		MinHeap minHeap = null;
		Scanner dictionary = null;
		Scanner importance = null;
		Scanner scan = new Scanner(System.in);
		String wordInput;
		String searchWord;

		try {
			dictionary = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			System.out.println("Error: the file given as dictionary doesn't exist!");
			System.exit(0);
		}

		try {
			importance = new Scanner(new File(args[1]));
		} catch (FileNotFoundException e) {
			System.out.println("Error: the file given as sample text doesn't exist!");
			System.exit(0);
		}

		while (dictionary.hasNextLine()) {
			wordInput = dictionary.nextLine();
			trie.insertWord(wordInput);
		}

		while (importance.hasNext()) {
			wordInput = importance.next();
			if (trie.searchWord(wordInput) == true)
				System.out.println("Successfully detected " + wordInput);
		}

		System.out.print("Search with (one) word: ");
		searchWord = scan.nextLine();

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
