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

	public void insert(char key) {

		// Create new element with the given key
		Element newElement = new Element(key);
		// Placeholder for the case that a cluster happens
		Element replacement;
		// Get ASCII value for key
		int value = key - 'a';

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

			if (newElement.probeLength > this.maxProbeLength) {
				this.maxProbeLength = newElement.probeLength;
			}
			if (this.size % this.capacity > 9) {
				// this.rehash(this.table);
			}

		}

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

	public static void main(String args[]) {

		RobinHoodHashing r = new RobinHoodHashing();
		r.insert('a');
		System.out.println(r);
		System.out.println();
		r.insert('a');
		System.out.println(r);
		System.out.println();
		r.insert('a');
		System.out.println(r);
		System.out.println();

	}

}
