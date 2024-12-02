//function to calculate the memory used by the program
public class MemoryCalculator {

	public int staticMemoryCalculator(Trie staticTrie, Trie.TrieNode currentNode, int nodeMemory) {

		if (currentNode == null)
			return 0;// if the node is null

		int sum = nodeMemory;

		for (int i = 0; i < Trie.ALPHABET_SIZE; i++)
			sum += staticMemoryCalculator(staticTrie, currentNode.subTrieNodes[i], nodeMemory);
			//recursively add up the memory used by all the children
		return sum;
	}

}
