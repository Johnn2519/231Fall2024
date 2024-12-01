public class Trie {

	TrieNode root;

	static int ALPHABET_SIZE = 26;

	class TrieNode {

		int wordLength;
		TrieNode[] subTrieNodes;

		public TrieNode() {
			this.wordLength = 0;
			this.subTrieNodes = new TrieNode[ALPHABET_SIZE];
		}

	}

	public Trie() {

		this.root = new TrieNode();
	}

	public void insert(String word) {
		TrieNode temp = this.root;
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (temp.subTrieNodes[c - 'a'] == null)
				temp.subTrieNodes[c - 'a'] = new TrieNode(); // Create new Trie Node being referenced by the specific
																// slot representing the character
			temp = temp.subTrieNodes[c - 'a']; // Move downwards
		}
		temp.wordLength = word.length();
	}

	public boolean search(String word) {

		TrieNode temp = this.root;
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (temp.subTrieNodes[c - 'a'] == null) { // If a Trie Node representing a character of the word being
														// searched is null then the word doesn't exist
				return false;
			}
			temp = temp.subTrieNodes[c - 'a']; // Move to next Trie Node

		}

		return temp.wordLength != 0;

	}

}