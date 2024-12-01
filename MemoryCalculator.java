
public class MemoryCalculator {

	public int staticMemoryCalculator(Trie staticTrie, Trie.TrieNode currentNode, int nodeMemory) {

		if (currentNode == null)
			return 0;

		int sum = nodeMemory;

		for (int i = 0; i < Trie.ALPHABET_SIZE; i++)
			sum += staticMemoryCalculator(staticTrie, currentNode.subTrieNodes[i], nodeMemory);

		return sum;
	}

}
