public class Tester {
    public static void main(String[] args) {
        RobinHoodTrie trie = new RobinHoodTrie();

        trie.insertWord("apple");
        trie.insertWord("app");
        trie.insertWord("banana");
        trie.insertWord("band");
        trie.insertWord("bandana");

        System.out.println("Search for 'apple': " + trie.search("apple")); // Expected: true
        System.out.println("Search for 'app': " + trie.search("app")); // Expected: true
        System.out.println("Search for 'band': " + trie.search("band")); // Expected: true
        System.out.println("Search for 'bandana': " + trie.search("bandana")); // Expected: true
        System.out.println("Search for 'cat': " + trie.search("cat")); // Expected: false

    }
}
