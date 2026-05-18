package contact;

import tree.nodes.AvlTreeNode;
import trees.AvlTree;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ContactList {
    private final AvlTree<Contact> contacts;

    public ContactList() {
        this.contacts = new AvlTree<>();
    }

    public void insertContact(Contact c) {
        this.contacts.insert(c);
    }

    public Contact findContact(String name) {
        return findContact(this.contacts.getRoot(), name);
    }

    public void removeContact(String name) {
        Contact c = findContact(this.contacts.getRoot(), name);

        if (c != null) {
            this.contacts.remove(c);
        }
    }

    public List<Contact> getEveryContact() {
        return StreamSupport.stream(this.contacts.levelOrderTraverse().spliterator(), false).map(AvlTreeNode<Contact>::getValue).collect(Collectors.toList());
    }

    public int getAmountOfContacts() {
        return getEveryContact().size();
    }

    /**
     * We can perform a binary search because the name starts with the letter and is the main criteria for how the tree
     * is structured
     *
     * @param letter the letter we are using to query contacts
     * @return a list of contacts where their name starts with the given letter (case ignored)
     */
    public List<Contact> getEveryContactStartingWith(char letter) {
        List<Contact> contactList = new ArrayList<>();

        traverseAndCheckName(this.contacts.getRoot(), letter, contactList);

        return contactList;
    }


    /**
     * Traverse the entire tree as contact names could contain the segment at any point within their name
     * O(n)
     *
     * @param segment the string segment we are searching for
     * @return a list of contacts matching the segment
     */
    public List<Contact> getStringMatchingContacts(String segment) {
        return StreamSupport.stream(this.contacts.levelOrderTraverse().spliterator(), false).map(AvlTreeNode<Contact>::getValue).filter(c -> c.getName().contains(segment)).collect(Collectors.toList());

    }

    private Contact findContact(AvlTreeNode<Contact> root, String name) {

        // If root is null, no one was found.
        if (root == null) return null;

        Contact contact = root.getValue();

        if (contact.getName().compareToIgnoreCase(name) == 0) return contact;

        if (contact.getName().charAt(0) > name.charAt(0)) {
            return findContact(root.getLeftChild(), name);
        } else {
            return findContact(root.getRightChild(), name);
        }
    }

    /***
     * Recursively traverses the tree and adds contacts to a list when their name starts with the given letter c (case ignored)
     * @param root the root node we are examining
     * @param c the char that we are matching to the first char of the contacts name
     * @param contacts a list of contacts
     */
    private void traverseAndCheckName(AvlTreeNode<Contact> root, char c, List<Contact> contacts) {
        if (root == null) return;

        if (Character.toLowerCase(root.getValue().getName().charAt(0)) == Character.toLowerCase(c)) {
            contacts.add(root.getValue());

            // Traverse both sides
            traverseAndCheckName(root.getLeftChild(), c, contacts);
            traverseAndCheckName(root.getRightChild(), c, contacts);
        }

        if (Character.toLowerCase(root.getValue().getName().charAt(0)) < Character.toLowerCase(c)) {
            traverseAndCheckName(root.getRightChild(), c, contacts);
        }

        if (Character.toLowerCase(root.getValue().getName().charAt(0)) >= Character.toLowerCase(c)) {
            traverseAndCheckName(root.getLeftChild(), c, contacts);
        }
    }


}
