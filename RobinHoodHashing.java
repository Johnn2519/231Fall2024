import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RobinHoodHashing {

	Element table[];
	int capacity;
	int size;
	int maxProbeLength;

	Trie trie = new Trie();

	class Element {

		char key;
		int probeLength;
		Trie.TrieNode trieNode;

		public Element(char c) {
			this.key = c;
			this.probeLength = 0;
			this.trieNode = null;
		}

	}

	public RobinHoodHashing() {

		this.capacity = 5;
		this.size = 0;
		this.maxProbeLength = 0;
		this.table = new Element[capacity];

	}

	public RobinHoodHashing(int newCapacity) {

		this.capacity = newCapacity;
		this.size = 0;
		this.maxProbeLength = 0;
		this.table = new Element[capacity];

	}

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

			} // If the spot in the hash table is occupied by another element, Robinhood
				// hashing follows
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
					// Move to next spot of hash table and update probe length for the new element
					// being moved
					newElement.probeLength++;
					hashValue++;
				}
				/*
				 * If the new element is closer or as close to its original spot than the
				 * element already placed there, the new Element searches a new spot to be
				 * placed and the distance is updated.
				 */
				else {
					newElement.probeLength++;
					hashValue++;
				}
			}

			// If the end of the hash table was reached loop to the start
			if (hashValue == capacity) {
				hashValue = 0;
			}

			// Check if the current probe length surpasses maximum probe length
			if (newElement.probeLength > this.maxProbeLength) {
				this.maxProbeLength = newElement.probeLength;
			}
			// Check if the hash table needs rehashing (more that 90% of it is occupied)
			if (this.size > (this.capacity * 0.9)) {
				this.rehash();
			}

		}

	}

	// Function responsible for searching for a specific key and returning whether
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
				// If the end of the hash table was reached loop to the start
				if (hashValue == capacity) {
					hashValue = 0;
				}
			}
		}
		/*
		 * If the maximum amount of probes were made and the element was not found that
		 * means that the key doesn't exist in the hash table.
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

	// Function to visualise results of Robinhood hashing
	public String toString() {

		String s = "";
		for (int i = 0; i < capacity; i++) {
			if (this.table[i] == null) {
				s += " _ ";
			} else {
				s += " (" + this.table[i].key + ", " + this.table[i].probeLength + ") ";

			}
		}
		return s;
	}

	// Main function for testing class functionality
	public static void main(String[] args) {
		RobinHoodHashing ht = new RobinHoodHashing(); // Assuming you have a constructor like this

		Random random = new Random();
		Set<Character> insertedLetters = new HashSet<>(); // Set to track inserted letters

		// Insert 20 unique random letters (or however many you want)
		for (int i = 0; i < 26; i++) {
			// Generate a random letter between 'a' and 'z'
			char randomLetter;

			// Keep generating until we find a letter that hasn't been inserted yet
			do {
				randomLetter = (char) ('a' + random.nextInt(26));
			} while (insertedLetters.contains(randomLetter)); // Check if letter is already inserted

			// Insert the random letter into the hash table
			ht.insert(randomLetter);
			System.out.println(ht);

			// Add the letter to the set to track it
			insertedLetters.add(randomLetter);
		}
	}

}
