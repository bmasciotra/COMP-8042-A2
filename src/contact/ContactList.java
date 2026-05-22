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
        return searchAndFind(this.contacts.getRoot(), name);
    }

    public void removeContact(String name) {
        Contact c = searchAndFind(this.contacts.getRoot(), name);

        if (c != null) {
            this.contacts.remove(c);
        }
    }

    public List<Contact> getEveryContact() {
        List<Contact> contactsAlphabeticalOrder = new ArrayList<>();
        inOrder(this.contacts.getRoot(), contactsAlphabeticalOrder);

        return contactsAlphabeticalOrder;
    }

    public int getAmountOfContacts() {
        return getEveryContact().size();
    }

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

    private void inOrder(AvlTreeNode<Contact> node, List<Contact> out) {
        if (node == null) return;
        inOrder(node.getLeftChild(), out);
        out.add(node.getValue());
        inOrder(node.getRightChild(), out);
    }

    private Contact searchAndFind(AvlTreeNode<Contact> root, String name) {
        if (root == null) return null;

        Contact contact = root.getValue();

        String thisSurname = Contact.extractSurname(contact.getName());
        String searchSurname = Contact.extractSurname(name);

        int comparison = thisSurname.compareToIgnoreCase(searchSurname);

        if (comparison == 0) {
            comparison = contact.getName().compareToIgnoreCase(name);
        }

        if (comparison == 0) return contact;
        if (comparison > 0) return searchAndFind(root.getLeftChild(), name);

        return searchAndFind(root.getRightChild(), name);
    }

    /***
     * Recursively traverses the tree and adds contacts to a list when their last name starts with the given letter c (case ignored)
     * @param root the root node we are examining
     * @param c the char that we are matching to the first char of the contacts name
     * @param contacts a list of contacts
     */
    private void traverseAndCheckName(AvlTreeNode<Contact> root, char c, List<Contact> contacts) {
        if (root == null) return;

        String surname = Contact.extractSurname(root.getValue().getName());

        char nodeFirstChar = Character.toLowerCase(surname.charAt(0));
        char target = Character.toLowerCase(c);

        if (nodeFirstChar == target) {
            contacts.add(root.getValue());
            // Traverse both sides — other matches could exist on either side
            traverseAndCheckName(root.getLeftChild(), c, contacts);
            traverseAndCheckName(root.getRightChild(), c, contacts);
            return;
        }

        if (nodeFirstChar < target) {
            // surname is "before" target — matches can only be on the right
            traverseAndCheckName(root.getRightChild(), c, contacts);
        } else {
            // surname is "after" target — matches can only be on the left
            traverseAndCheckName(root.getLeftChild(), c, contacts);
        }
    }
}
