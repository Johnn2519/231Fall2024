import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Robin Hood Trie data structure
 */
public class RobinHoodTrie {

    /**
     * Root node of the Trie
     */
    RobinHoodTrieNode root;

    /**
     * Default constructor for the Trie
     * Initialises the root
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

        currentNode.importance++;
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
     * Serches for a given word in the trie
     * 
     * @param word
     * @return
     */
    public boolean search(String word) {
        RobinHoodTrieNode currentNode = this.root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (currentNode.hashTable == null) {
                return false;
            }
            if (!currentNode.hashTable.search(c)) {
                return false;
            }
            for (RobinHoodTrieNode.RobinHoodHashing.Element element : currentNode.hashTable.table) {
                if (element != null && element.key == c) {
                    currentNode = element.robinHoodTrieNode;
                    break;
                }
            }
        }
        return currentNode.importance > 0;
    }

    /**
     * NOT WORKING PROPERLY
     * 
     * Thelei na allaxtei gia tes odigies tou lab
     * 
     * @param prefix
     * @return
     */
    public List<String> autocomplete(String prefix) {
        RobinHoodTrieNode currentNode = this.root;
        List<String> results = new ArrayList<>();

        // Traverse the trie to the end of the prefix
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (currentNode.hashTable == null || !currentNode.hashTable.search(c)) {
                return results; // Return empty list if prefix doesn't exist
            }
            for (RobinHoodTrieNode.RobinHoodHashing.Element element : currentNode.hashTable.table) {
                if (element != null && element.key == c) {
                    currentNode = element.robinHoodTrieNode;
                    break;
                }
            }
        }

        // Recursively gather all words from the current node
        gatherWords(currentNode, prefix, results);
        return results;
    }

    /**
     * Recursively runs the trie starting from the given node and gathers all
     * valid words into the results list.
     *
     * @param node
     * @param prefix
     * @param results
     */

    private void gatherWords(RobinHoodTrieNode node, String prefix, List<String> results) {
        if (node.importance > 0) {
            results.add(prefix);
        }
        if (node.hashTable == null) {
            return;
        }
        for (RobinHoodTrieNode.RobinHoodHashing.Element element : node.hashTable.table) {
            if (element != null && element.robinHoodTrieNode != null) {
                gatherWords(element.robinHoodTrieNode, prefix + element.key, results);
            }
        }
    }

}
