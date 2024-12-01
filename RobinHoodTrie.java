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
	 * Searches for a given word in the trie and if its found, increase its
	 * importance
	 * 
	 * @param word
	 * @return
	 */
	public boolean searchWord(String word) {
		RobinHoodTrieNode currentNode = this.root;
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			/*
			 * If the word's characters lead to an uninitialised hash table then the word
			 * doesn't exist
			 */
			if (currentNode.hashTable == null) {
				return false;
			}
			/*
			 * If even a single character of the word doesn't exist in the Trie then the
			 * word being searched doesn't exist
			 */
			if (!currentNode.hashTable.search(c)) {
				return false;
			}
			/*
			 * Iterate through the hash table until the element containing the character of
			 * the word is found and then set the current node to the node that that element
			 * points to
			 */
			for (RobinHoodTrieNode.RobinHoodHashing.Element element : currentNode.hashTable.table) {
				if (element != null && element.key == c) {
					currentNode = element.robinHoodTrieNode;
					break;
				}
			}
		}
		// If ended on a node with 0 length then the word doesn't exist
		if (currentNode.wordLength == 0)
			return false;
		// Increase word's importance
		currentNode.importance++;
		System.out.println(word + " Importance: " + currentNode.importance + "\n" + word + " Word Length: "
				+ currentNode.wordLength);

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
	public void autocomplete(String stringGiven, MinHeap minHeap) {
		// Start from the root of the Trie
		RobinHoodTrieNode currentNode = this.root;
		// Create MinHeap to store top k words with most importance

		// Call criteria methods to collect all valid recommendations
		prefixCriteria(minHeap, currentNode, stringGiven, true);
		sameLengthCriteria(minHeap, currentNode, stringGiven, "", 0);
		//differentLengthCriteria(minHeap, currentNode, stringGiven, "");
		for (MinHeap.HeapElement element : minHeap.heapContents) {
			if (element != null)
				System.out.println(element.word + " " + element.importance);
		}

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

	private void prefixCriteria(MinHeap minHeap, RobinHoodTrieNode node, String subString, boolean currentlyOnPrefix) {

		// Descend Trie only if we're currently still on the prefix within the Trie
		if (currentlyOnPrefix) {
			// Traverse the trie until the end of the prefix
			for (int i = 0; i < subString.length(); i++) {
				char c = subString.charAt(i);

				// Check if the current node contains the current letter of the prefix
				if (node.hashTable != null && node.hashTable.search(c)) {
					/*
					 * Search for element in the hash table holding the current character of the
					 * prefix
					 */
					for (RobinHoodTrieNode.RobinHoodHashing.Element element : node.hashTable.table) {
						if (element != null && element.key == c) {
							node = element.robinHoodTrieNode;
							break;
						}
					}

				} // If the prefix doesn't exist in the Trie, no recommendations are available
				else {
					System.out.println("No words found starting with " + subString);
					return;
				}
			}
		}

		if (node.wordLength > 0 && !currentlyOnPrefix) {
			if (minHeap.size < minHeap.maxSize - 1)
				minHeap.insertMin(subString, node.importance);
			else if (minHeap.getTop().importance < node.importance) {
				minHeap.deleteMin();
				minHeap.insertMin(subString, node.importance);
			}
		}
		if (node.hashTable != null) {

			for (RobinHoodTrieNode.RobinHoodHashing.Element element : node.hashTable.table) {
				if (element != null && element.robinHoodTrieNode != null) {
					prefixCriteria(minHeap, element.robinHoodTrieNode, subString + element.key, false);
				}
			}
		}
	}

	/**
	 * @param minHeap a heap used to store words with the most importance
	 * 
	 */
	private void sameLengthCriteria(MinHeap minHeap, RobinHoodTrieNode node, String wordGiven, String currentWord,
			int differences) {

		if (node.hashTable != null && currentWord.length() < wordGiven.length() && !(differences > 2)) {
			for (RobinHoodTrieNode.RobinHoodHashing.Element element : node.hashTable.table) {

				if (element != null && element.robinHoodTrieNode != null) {
					if (wordGiven.charAt(currentWord.length()) == element.key) {
						sameLengthCriteria(minHeap, element.robinHoodTrieNode, wordGiven, currentWord + element.key,
								differences);
					} else {
						sameLengthCriteria(minHeap, element.robinHoodTrieNode, wordGiven, currentWord + element.key,
								differences + 1);
					}
				}
			}
		}
		if (node.wordLength == wordGiven.length() && differences > 0 && differences <= 2) {
			if (minHeap.size < minHeap.maxSize - 1)
				minHeap.insertMin(currentWord, node.importance);
			else if (minHeap.getTop().importance < node.importance) {
				minHeap.deleteMin();
				minHeap.insertMin(currentWord, node.importance);
			}
		}
	}

	private void differentLengthCriteria(MinHeap minHeap, RobinHoodTrieNode node, String wordGiven,
			String currentWord) {

		if (node.hashTable != null) {
			// If currently on valid word and difference in length of two words isn't
			// greater than 3
			if (node.wordLength > 0 && Math.abs(wordGiven.length() - currentWord.length()) < 3) {
				// Track which letter of the word given and the current word is examined
				int longerWordIndex = 0, shorterWordIndex = 0;
				// Track current differences to exit as soon as two strikes are found
				int strikes = 0;
				// Sets the upper limit of different characters allowed
				int maxStrikes = 0;
				// Holds the longer word out of word given and current word
				String longerWord = null;
				// Holds the shorter word out of word given and current word
				String shorterWord = null;
				// If currently on a word that is one character shorter
				if (wordGiven.length() - currentWord.length() == 1) {

					longerWord = wordGiven;
					shorterWord = currentWord;
					maxStrikes = 1;
				} // If currently on a word with maximum 2 characters longer
				else if ((currentWord.length() - wordGiven.length() > 0)
						&& (currentWord.length() - wordGiven.length() <= 2)) {

					longerWord = currentWord;
					shorterWord = wordGiven;
					maxStrikes = 2;
				}
				// Check if current word is "hidden" inside word given
				while (strikes <= maxStrikes && longerWordIndex < longerWord.length()) {
					if (longerWord.charAt(longerWordIndex) == shorterWord.charAt(shorterWordIndex))
						shorterWordIndex++;
					else
						strikes++;

					longerWordIndex++;
				}
				// If after comparing the two words, maximum strikes were not surpassed, insert
				// current word in the heap if its importance is higher than that of the top
				// element's
				if (strikes < maxStrikes) {
					if (minHeap.size < minHeap.maxSize - 1)
						minHeap.insertMin(currentWord, node.importance);
					else if (minHeap.getTop().importance < node.importance) {
						minHeap.deleteMin();
						minHeap.insertMin(currentWord, node.importance);
					}
				}
			}

			for (RobinHoodTrieNode.RobinHoodHashing.Element element : node.hashTable.table) {
				if (element != null && element.robinHoodTrieNode != null) {
					differentLengthCriteria(minHeap, element.robinHoodTrieNode, wordGiven, currentWord + element.key);
				}
			}
		}
	}

}