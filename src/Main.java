import bst.BSTChecker;
import contact.Contact;
import contact.ContactList;
import graphs.FriendGraph;
import reader.DotFileReader;
import trees.GenericTree;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {

        // Problem 1: BST Checker
        String path = "external/test4.dot";
        GenericTree<Integer> tree = DotFileReader.createTreeFromDotFile(path, Integer::parseInt);
        BSTChecker bstChecker = new BSTChecker();

        boolean isBst = bstChecker.isBinarySearchTree(tree);
        System.out.println(isBst);

        // Problem 2: AVL Tree
        ContactList contacts = new ContactList();

        // Dummy test data
        contacts.insertContact(new Contact("John Smith", "john.smith@example.com", "604-555-0101"));
        contacts.insertContact(new Contact("Alice Anderson", "alice.a@example.com", "604-555-0102"));
        contacts.insertContact(new Contact("Mark Johnson", "mjohnson@example.com", "604-555-0103"));
        contacts.insertContact(new Contact("Wei Chen", "wei.chen@example.com", "604-555-0104"));
        contacts.insertContact(new Contact("Sarah Brown", "sbrown@example.com", "604-555-0105"));
        contacts.insertContact(new Contact("Jane Smith", "jane.smith@example.com", "604-555-0106"));
        contacts.insertContact(new Contact("Raj Patel", "raj.patel@example.com", "604-555-0107"));
        contacts.insertContact(new Contact("Tom Anderson", "tom.a@example.com", "604-555-0108"));
        contacts.insertContact(new Contact("Lucy Williams", "lwilliams@example.com", "604-555-0109"));
        contacts.insertContact(new Contact("Emma Johansson", "emma.j@example.com", "604-555-0110"));
        contacts.insertContact(new Contact("Li Zhang", "li.zhang@example.com", "604-555-0111"));
        contacts.insertContact(new Contact("David Brown", "dbrown@example.com", "604-555-0112"));
        contacts.insertContact(new Contact("Ian Macdonald", "ian.mac@example.com", "604-555-0113"));
        contacts.insertContact(new Contact("Mary OConnor", "moconnor@example.com", "604-555-0114"));
        contacts.insertContact(new Contact("Arjun Singh", "arjun.s@example.com", "604-555-0115"));
        contacts.insertContact(new Contact("Satrah Brown", "sbrown@example.com", "604-555-0105"));

        // --- Test each ContactList method ---

        System.out.println("\n[getEveryContact - should print 15 contacts in alphabetical order]");
        contacts.getEveryContact().forEach(System.out::println);

        System.out.println("\n[findContact 'Raj Patel']");
        System.out.println(contacts.findContact("Raj Patel"));

        System.out.println("\n[findContact 'Doesnt Exist' - should be null]");
        System.out.println(contacts.findContact("Doesnt Exist"));

        System.out.println("\n[getEveryContactStartingWith 'S' - expect Sarah Brown]");
        contacts.getEveryContactStartingWith('S').forEach(System.out::println);

        System.out.println("\n[getEveryContactStartingWith 'A' - expect Arjun Singh]");
        contacts.getEveryContactStartingWith('A').forEach(System.out::println);

        System.out.println("\n[getStringMatchingContacts 'Joh' - expect Johansson, Johnson]");
        contacts.getStringMatchingContacts("Joh").forEach(System.out::println);

        System.out.println("\n[getStringMatchingContacts 'son' - expect Anderson x2, Johansson, Johnson]");
        contacts.getStringMatchingContacts("son").forEach(System.out::println);

        System.out.println("\n[removeContact 'John Smith' then list again]");
        contacts.removeContact("John Smith");
        contacts.getEveryContact().forEach(System.out::println);

        System.out.println();

        // Problem 3: Friend Graph
        FriendGraph fg = new FriendGraph();

        Contact john = new Contact("John Smith", "john.smith@example.com", "604-555-0101");
        Contact alice = new Contact("Alice Anderson", "alice.a@example.com", "604-555-0102");
        Contact arjun = new Contact("Arjun Singh", "arjun.s@example.com", "604-555-0115");
        Contact david = new Contact("David Brown", "dbrown@example.com", "604-555-0112");
        Contact lucy = new Contact("Lucy Williams", "lwilliams@example.com", "604-555-0109");

        // Add friends
        fg.addFriend(john, alice);
        fg.addFriend(arjun, john);
        fg.addFriend(david, alice);
        fg.addFriend(lucy, john);
        fg.addFriend(lucy, alice);

        // remove friends
        fg.removeFriend(arjun, john);

        System.out.println(fg);
    }
}