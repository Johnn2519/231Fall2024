
public class MinHeap {

	HeapElement heapContents[];
	int maxSize;
	int size;

	// Initialises a heap with a maximum of k spots
	public MinHeap(int k) {
		/*
		 * Initialise a heap of maxSize k (for storing the k most important words) + 1
		 * for the starting position of the array that is not used
		 */
		this.maxSize = k + 1;
		this.size = 0;
		this.heapContents = new HeapElement[this.maxSize];

	}

	public class HeapElement {
		String word;
		int importance;

		public HeapElement(String wordValue, int importanceValue) {

			this.word = wordValue;
			this.importance = importanceValue;
		}

	}

	public HeapElement getTop() {
		return this.heapContents[1];
	}

	public void insertMin(String wordValue, int importanceValue) {

		HeapElement newElement = new HeapElement(wordValue, importanceValue);
		this.size++;

		percolateUp(newElement);

	}

	public HeapElement deleteMin() {
		HeapElement minimumElement = this.heapContents[1];

		percolateDown(this.heapContents[maxSize - 1]);
		this.size--;

		return minimumElement;
	}

	public void percolateUp(HeapElement newElement) {

		HeapElement replacement = null;
		// Variable to track position being examined in the heap
		int pos = this.size;

		// If this is the first element to be placed in the heap, place at 1st spot
		if (pos == 1) {
			this.heapContents[1] = newElement;
		}

		// Repeat percolate up until spot was found to place the new element
		while (true) {
			// If the first spot was reached, no possible comparisons left, end percolate up
			if (pos == 1) {
				break;
			}
			// If parent has larger importance than its child swap them
			if (this.heapContents[pos / 2].importance > newElement.importance) {
				// Swap new element's parent with the new element
				replacement = this.heapContents[pos / 2];
				this.heapContents[pos / 2] = newElement;
				this.heapContents[pos] = replacement;
				// Update position by moving to where the switched element was
				pos /= 2;
			} else {
				// Found final position, end percolate up
				this.heapContents[pos] = newElement;
				break;
			}

		}

	}

	public void percolateDown(HeapElement savedElement) {
		int pos = 1;

		/*
		 * If heap contains only two positions: pos 0 that is unused and pos 1 for a
		 * single element
		 */
		if (this.maxSize == 2) {
			// Remove the only element in the heap
			this.heapContents[1] = null;
			return;
		}

		// while there's at least one child available for current element in the heap
		while (2 * pos < this.maxSize) {

			/*
			 * If exactly one child is left it means its the last element, so place saved
			 * element on current position, last spot in heap = null and terminate
			 * percolateDown
			 */
			if ((2 * pos) + 1 >= this.maxSize) {
				this.heapContents[pos] = savedElement;
				this.heapContents[this.maxSize - 1] = null;
				return;
			}

			/*
			 * If both children have more or equal importance than saved element's
			 * importance place saved element on current position, last spot in heap = null
			 * and terminate percolateDown
			 */
			if (this.heapContents[pos * 2].importance >= savedElement.importance
					&& this.heapContents[pos * 2 + 1].importance >= savedElement.importance) {

				this.heapContents[pos] = savedElement;
				this.heapContents[this.maxSize - 1] = null;
				return;
			}

			/*
			 * Check which child is smaller in importance, move it upwards and update
			 * current position in heap
			 */

			else if (this.heapContents[2 * pos].importance <= this.heapContents[(2 * pos) + 1].importance) {
				this.heapContents[pos] = this.heapContents[2 * pos];
				pos = 2 * pos;
			} else {
				this.heapContents[pos] = this.heapContents[(2 * pos) + 1];
				pos = (2 * pos) + 1;
			}
		}

		// Move saved element at new last position
		this.heapContents[this.maxSize - 2] = savedElement;
		// Set new empty spot at the end of the heap as null
		this.heapContents[this.maxSize - 1] = null;

	}

	public static void main(String args[]) {

		MinHeap m = new MinHeap(6);
		m.insertMin("pineapple", 3);
		m.insertMin("apple", 22);
		m.insertMin("banana", 27);
		m.insertMin("orange", 44);
		m.insertMin("mango", 26);
		m.insertMin("grapes", 28);

		System.out.println("Contents of heap: ");
		for (HeapElement h : m.heapContents) {
			if (h == null)
				continue;
			System.out.println(h.word + " " + h.importance);
		}

		m.deleteMin();
		System.out.println("Contents of heap after deleteMin: ");
		for (HeapElement h : m.heapContents) {
			if (h == null)
				continue;
			System.out.println(h.word + " " + h.importance);
		}
	}

}
