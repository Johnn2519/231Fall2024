


public class MemoryCalculator {

	private static long calcStaticTrie(Trie.TrieNode node) {
		// Null node consumes no memory
		if (node == null)
			return 0;
		// Add memory for the current node
		long sum = 216;
		for (int i = 0; i < Trie.ALPHABET_SIZE; i++)
			if (node.subTrieNodes[i] != null)
				// Add memory for each child recursively
				sum += (10 + calcStaticTrie(node.subTrieNodes[i]));
		return sum;
	}

	private static long calcRobinHoodTrie(RobinHoodTrie.RobinHoodTrieNode node) {
		// Null node consumes no memory
		if (node == null)
			return 0;
		// Add memory for the current node
		long sum = 16 + calcRobinHoodTable(node.hashTable);
		for (int i = 0; i < node.hashTable.capacity; i++)
			if (node.hashTable.table[i] != null)
				// Add memory for the hash table and each child recursively
				sum += (14 + calcRobinHoodTrie(node.hashTable.table[i].robinHoodTrieNode));
		return sum;
	}

	private static long calcRobinHoodTable(RobinHoodTrie.RobinHoodTrieNode.RobinHoodHashing table) {
		// Null table consumes no memory
		if (table == null)
			return 0;
		// Memory for the hash table elements
		return (long) table.capacity * 8 + 12;
	}

	public static long calcStaticMemory(Trie trie) {
		// Null trie consumes no memory
		if (trie == null)
			return 8;
		return 8 + calcStaticTrie(trie.root);
	}

	public static long calcRobinHoodMemory(RobinHoodTrie trie) {
		// Null trie consumes no memory
		if (trie == null)
			return 8;
		return 8 + calcRobinHoodTrie(trie.root);
	}
}