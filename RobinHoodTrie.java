/**
 * Represents a Robin Hood Trie data structure
 */
public class RobinHoodTrie {

	/**
	 * Root node of the Trie
	 */
	RobinHoodTrieNode root;

	/**
	 * Default constructor for the Trie Initialises the root
	 */
	RobinHoodTrie() {
		this.root = new RobinHoodTrieNode();
	}

	/**
	 * Inserts a word into the trie
	 * 
	 * @param word the word to be inserted
	 */
	public void insertWord(String word) {
		RobinHoodTrieNode currentNode = this.root;
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);

			if (currentNode.hashTable == null) {
				currentNode.hashTable = new RobinHoodTrieNode.RobinHoodHashing();
			}

			if (!currentNode.hashTable.search(c)) {
				currentNode.hashTable.insert(c);
			}

			for (RobinHoodTrieNode.RobinHoodHashing.Element element : currentNode.hashTable.table) {
				if (element != null && element.key == c) {
					if (element.robinHoodTrieNode == null) {
						element.robinHoodTrieNode = new RobinHoodTrieNode();
					}
					currentNode = element.robinHoodTrieNode;
					break;
				}
			}
		}

		currentNode.wordLength = word.length();
	}

	/**
	 * Represents a node in the trie
	 */
	private static class RobinHoodTrieNode {
		int importance;
		int wordLength;
		RobinHoodHashing hashTable;

		/**
		 * Default constructor for a trie node
		 */
		public RobinHoodTrieNode() {
			this.wordLength = 0;
			this.importance = 0;
			this.hashTable = null;
		}

		/**
		 * Represents a Robin Hood Hashing structure
		 */
		private static class RobinHoodHashing {
			Element[] table;
			int capacity;
			int size;
			int maxProbeLength;

			/**
			 * Represents an element stored in the hash table
			 */
			private static class Element {
				char key;
				int probeLength;
				RobinHoodTrieNode robinHoodTrieNode;

				/**
				 * Constructor for an element with a given key
				 * 
				 * @param c the character key to store
				 */
				public Element(char c) {
					this.key = c;
					this.probeLength = 0;
					this.robinHoodTrieNode = null;
				}
			}

			/**
			 * Default constructor for a Robin Hood Hashing table with default capacity
			 */
			public RobinHoodHashing() {
				this.capacity = 5;
				this.size = 0;
				this.maxProbeLength = 0;
				this.table = new Element[capacity];
			}

			/**
			 * Constructor for a Robin Hood Hashing table with a specified capacity
			 * 
			 * @param newCapacity the initial capacity of the hash table
			 */
			public RobinHoodHashing(int newCapacity) {
				this.capacity = newCapacity;
				this.size = 0;
				this.maxProbeLength = 0;
				this.table = new Element[capacity];
			}

			/**
			 * Inserts a character into the hash table
			 * 
			 * @param key the character to insert
			 */
			public void insert(char key) {
				Element newElement = new Element(key);
				Element replacement;
				int value = key;
				int hashValue = value % this.capacity;

				boolean finishedRobinhoodHashing = false;

				while (!finishedRobinhoodHashing) {
					if (this.table[hashValue] == null) {
						this.table[hashValue] = newElement;
						this.size++;
						finishedRobinhoodHashing = true;
					} else {
						if (newElement.probeLength > this.table[hashValue].probeLength) {
							replacement = table[hashValue];
							this.table[hashValue] = newElement;
							newElement = replacement;
							newElement.probeLength++;
							hashValue++;
						} else {
							newElement.probeLength++;
							hashValue++;
						}
					}

					if (hashValue == capacity) {
						hashValue = 0;
					}

					if (newElement.probeLength > this.maxProbeLength) {
						this.maxProbeLength = newElement.probeLength;
					}

					if (this.size > (this.capacity * 0.9)) {
						this.rehash();
					}
				}
			}

			/**
			 * Searches for a character in the hash table
			 * 
			 * @param key the character to search for
			 * @return true if the character exists in the table, false otherwise
			 */
			public boolean search(char key) {
				int value = key;
				int hashValue = value % this.capacity;

				for (int i = 0; i <= this.maxProbeLength; i++) {
					if (this.table[hashValue] == null) {
						continue;
					}
					if (this.table[hashValue].key == key) {
						return true;
					} else {
						hashValue++;
						if (hashValue == capacity) {
							hashValue = 0;
						}
					}
				}
				return false;
			}

			/**
			 * Rehashes the hash table to a larger capacity when needed.
			 */
			public void rehash() {
				RobinHoodHashing rehashed = null;

				switch (this.capacity) {
				case 5:
					rehashed = new RobinHoodHashing(11);
					break;
				case 11:
					rehashed = new RobinHoodHashing(19);
					break;
				case 19:
					rehashed = new RobinHoodHashing(29);
					break;
				}

				for (int i = 0; i < this.capacity; i++) {
					if (this.table[i] != null) {
						rehashed.insert(this.table[i].key);
					}
				}

				this.table = rehashed.table;
				this.capacity = rehashed.capacity;
				this.size = rehashed.size;
				this.maxProbeLength = rehashed.maxProbeLength;
			}
		}
	}

	/**
	 * Searches for a given word in the trie and if its found, increase its importance
	 * 
	 * @param word
	 * @return
	 */
	public boolean searchWord(String word) {
		RobinHoodTrieNode currentNode = this.root;
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			/* If the word's characters lead to an uninitialised hash table
			 * then the word doesn't exist
			 */
			if (currentNode.hashTable == null) {
				return false;
			}
			/* If even a single character of the word doesn't exist in the Trie then
			 * the word being searched doesn't exist
			 */
			if (!currentNode.hashTable.search(c)) {
				return false;
			}
			/* Iterate through the hash table until the element containing the character
			 * of the word is found and then set the current node 
			 * to the node that that element points to
			 */
			for (RobinHoodTrieNode.RobinHoodHashing.Element element : currentNode.hashTable.table) {
				if (element != null && element.key == c) {
					currentNode = element.robinHoodTrieNode;
					break;
				}
			}
		}
		// Increase word's importance
		currentNode.importance++;
		System.out.println(word + " Importance: " + currentNode.importance 
						+ "\n" + word + " Word Length: " + currentNode.wordLength);

		// Word exists if it passed all the checks above for every character of the word
		return true;
	}

	/**
	 * NOT WORKING PROPERLY
	 * 
	 * Thelei na allaxtei gia tes odigies tou lab
	 * 
	 * @param prefix
	 * @return
	 */
	public void autocomplete(String subString) {
		RobinHoodTrieNode currentNode = this.root;

		// Traverse the trie to the end of the prefix
		for (int i = 0; i < subString.length(); i++) {
			char c = subString.charAt(i);
			if (currentNode.hashTable != null && currentNode.hashTable.search(c)) {

				for (RobinHoodTrieNode.RobinHoodHashing.Element element : currentNode.hashTable.table) {
					if (element != null && element.key == c) {
						currentNode = element.robinHoodTrieNode;
						break;
					}
				}
			}
		}

		// Recursively gather all words from the current node
		prefixCriteria(currentNode, subString, true);
		System.out.println();

	}

	/**
	 * (temporary usage until minHeap is implemented) Recursively runs the trie
	 * starting from the given node and prints all valid words following the prefix
	 * criteria.
	 *
	 * @param node
	 * @param subString
	 * @param currentlyOnPrefix boolean to track if node is the end of the prefix to
	 *                          avoid printing it
	 */

	private void prefixCriteria(RobinHoodTrieNode node, String subString, boolean currentlyOnPrefix) {

		if (node.wordLength > 0 && !currentlyOnPrefix) {
			System.out.println(subString);
		}
		if (node.hashTable != null) {

			for (RobinHoodTrieNode.RobinHoodHashing.Element element : node.hashTable.table) {
				if (element != null && element.robinHoodTrieNode != null) {
					prefixCriteria(element.robinHoodTrieNode, subString + element.key, false);
				}
			}
		}
	}

}