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

		// Calculate the new element's index in the hash table
		int hashValue = value % this.capacity;

		// Variable to mark whether the hashing process is finished
		boolean finishedRobinhoodHashing = false;

		while (!finishedRobinhoodHashing) {
			// If the spot is empty in the hash table, simply place the new element in that
			// spot
			if (this.table[hashValue] == null) {
				this.table[hashValue] = newElement;
				this.size++;
				finishedRobinhoodHashing = true;

			} // If the spot in the hash table is occupied by another element, Robinhood
				// hashing follows
			else {

				/*
				 * If the new element is further away from its intended spot than the element
				 * already placed there, the new Element is placed there and the previous
				 * element has to find a new spot to be placed.
				 */
				if (newElement.probeLength > this.table[hashValue].probeLength) {
					replacement = table[hashValue];
					this.table[hashValue] = newElement;
					newElement = replacement;
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

			// Reset the hash value to 0
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

	public boolean search(char key) {
		// Get ASCII value for key
		int value = key;

		// Calculate the new element's index in the hash table
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
		 * If after the maximum amount of probes were made and the element was not found
		 * that means that the key doesn't exist in the hash table.
		 */
		return false;
	}

	public void rehash() {

		RobinHoodHashing rehashed = null;
		if (this.capacity == 5) {
			rehashed = new RobinHoodHashing(11);
		} else if (this.capacity == 11) {
			rehashed = new RobinHoodHashing(19);
		} else if (this.capacity == 19) {
			rehashed = new RobinHoodHashing(29);
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
