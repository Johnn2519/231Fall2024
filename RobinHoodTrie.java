
// Main data structure
public class RobinHoodTrie {

	// Root of the Trie
	RobinHoodTrieNode root;
	
	// Default constructor for Trie
	RobinHoodTrie() {
		this.root = new RobinHoodTrieNode();
	}

	public void insertWord(String word) {
		RobinHoodTrieNode tmp = new RobinHoodTrieNode();
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			
			// To be implemented...
		}

	}
	
	// Inner class, Trie is made from TrieNodes
	private static class RobinHoodTrieNode {

		// The amount of times a word has appeared 
		int importance;
		// Length of existing word formed up until that TrieNode
		int wordLength;
		// Hash Table containing the elements/characters
		RobinHoodHashing hashTable;

		// Default constructor for TrieNode
		public RobinHoodTrieNode() {
			this.wordLength = 0;
			this.importance = 0;
			this.hashTable = null;
		}

		// Inner class, TrieNode contains Robinhood hash tables
		private static class RobinHoodHashing {

			// Array of elements/characters the TrieNode stores
			Element table[];
			
			// Total space the hash table has
			int capacity;
			
			// Amount of elements occupying the hash table
			int size;
			
			// Maximum spots an element was placed away from its intended spot
			int maxProbeLength;

			// Inner class, Robinhood hash table contains elements
			private static class Element {

				// Value an element holds
				char key;
				
				// Amount of spots an element was placed away from its intended spot
				int probeLength;
				
				// Trie node the element leads to
				RobinHoodTrieNode robinHoodTrieNode;

				// Constructor for Element with key of value c
				public Element(char c) {
					
					this.key = c;
					this.probeLength = 0;
					this.robinHoodTrieNode = null;
				}

			}

			// Default constructor for Robinhood hash table
			public RobinHoodHashing() {

				this.capacity = 5;
				this.size = 0;
				this.maxProbeLength = 0;
				this.table = new Element[capacity];

			}

			// Constructor for Robinhood hash table with different starting capacity
			public RobinHoodHashing(int newCapacity) {

				this.capacity = newCapacity;
				this.size = 0;
				this.maxProbeLength = 0;
				this.table = new Element[capacity];

			}

			// Method responsible for inserting elements inside a hash table using Robinhood hashing
			public void insert(char key) {

				// Create new element with the given key
				Element newElement = new Element(key);
				// Placeholder for the case that a cluster happens
				Element replacement;
				// Get ASCII value for key
				int value = key;

				// Calculate the new element's index in the hash table with hash function
				int hashValue = value % this.capacity;

				// Variable to mark whether the hashing process is finished
				boolean finishedRobinhoodHashing = false;

				while (!finishedRobinhoodHashing) {
					// If the spot is empty in the hash table, simply place the new element in that
					// spot and mark the Robinhood hashing process as finished.
					if (this.table[hashValue] == null) {
						this.table[hashValue] = newElement;
						this.size++;
						finishedRobinhoodHashing = true;

					} // If the spot in the hash table is occupied by another element, Robinhood hashing follows
					else {
						/*
						 * If the new element is further away from its intended spot than the element
						 * already placed there, the new Element is placed in its place and the previous
						 * element has to find a new spot to be placed.
						 */
						if (newElement.probeLength > this.table[hashValue].probeLength) {
							// Replace old element with new one
							replacement = table[hashValue];
							this.table[hashValue] = newElement;
							newElement = replacement;
							// Move to next spot of hash table and update probe length for the new element being moved
							newElement.probeLength++;
							hashValue++;
						}
						/*
						 * If the new element is closer or as close to its original spot as the
						 * element already placed there, the new element searches a new spot to be
						 * placed in and the distance is updated.
						 */
						else {
							newElement.probeLength++;
							hashValue++;
						}
					}

					// If the end of the hash table was reached, loop to the start to not miss any potential available spots
					if (hashValue == capacity) {
						hashValue = 0;
					}
					
					// Check if current probe length surpasses maximum probe length 
					if (newElement.probeLength > this.maxProbeLength) {
						this.maxProbeLength = newElement.probeLength;
					}
					// Check if the hash table needs rehashing (more that 90% of it is occupied)
					if (this.size > (this.capacity * 0.9)) {
						this.rehash();
					}

				}

			}
			
			// Function responsible for searching for a specific key and determining whether
			// it exists or not
			public boolean search(char key) {
				// Get ASCII value for key
				int value = key;

				// Calculate the new element's index in the hash table with hash function
				int hashValue = value % this.capacity;

				// Search for a maximum amount of maxProbeLength
				for (int i = 0; i <= this.maxProbeLength; i++) {
					// If the slot being examined is null, skip it and go to next iteration
					if (this.table[hashValue] == null) {
						continue;
					}
					// If the key was found return true
					if (this.table[hashValue].key == key) {
						return true;
					}
					// The key was not in the original spot so the hash value gets updated
					else {
						hashValue++;
						/* If the end of the hash table was reached, loop to the start 
						 * to not miss any potential available spots
						 */
						if (hashValue == capacity) {
							hashValue = 0;
						}
					}
				}
				/*
				 * If the maximum amount of probes were made and the element was not found
				 * that means that the key doesn't exist in the hash table.
				 */
				return false;
			}

			// Function responsible for modification of hashtable when a specific percentage
			// of space occupied is surpassed
			public void rehash() {

				// Initialise new instance of a robinhood hash table to replace the old one
				RobinHoodHashing rehashed = null;
				/*
				 * According to current capacity of the hash table, the new one gets initialised
				 * with a specific new capacity (5->11->19->29)
				 */
				switch (this.capacity) {
					
				case 5:  rehashed = new RobinHoodHashing(11);
						break;
				case 11: rehashed = new RobinHoodHashing(19);
						break;
				case 19: rehashed = new RobinHoodHashing(29);
						break;
				}
				
				// Insert all elements from the previous hash table to the new one
				for (int i = 0; i < this.capacity; i++) {
					if (this.table[i] != null) {
						rehashed.insert(this.table[i].key);
					}
				}
				// Assign all the fields with the new values
				this.table = rehashed.table;
				this.capacity = rehashed.capacity;
				this.size = rehashed.size;
				this.maxProbeLength = rehashed.maxProbeLength;
			}

		}

	}

}
