package graphs;

import contact.Contact;

import java.util.Arrays;
import java.util.HashMap;

public class FriendGraph {

    // the contact map represents a hashmap for a user and their index in the friends list
    private final HashMap<Contact, Integer> contactMap;

    // an adjacency matrix where a contact is represented by an index and their relationships by another int, 0 for no
    // relationship and 1 for relationship
    private int[][] friends = new int[2][2];

    public FriendGraph() {
        contactMap = new HashMap<>();
    }


    /**
     * Add friend allows a main contact (ie the friend) to add a new friend relationship to the graph
     * <p>
     * this contact will now have a new edge for that friend and it will be represented as 1 or 0 where 1 is there is a
     * relationship
     *
     * @param contact
     * @param friend
     */
    public void addFriend(Contact contact, Contact friend) {

        // Add the users to the contact map OR grab their index
        Integer contactIndex = contactMap.computeIfAbsent(contact, k -> contactMap.size());
        Integer friendIndex = contactMap.computeIfAbsent(friend, k -> contactMap.size());

        // After we have added the users to the map, check the size of the friends array to resize if necessary
        if (friends.length < contactMap.size()) {
            int[][] resize = new int[contactMap.size()][contactMap.size()];

            for (int i = 0; i < friends.length; i++) {
                System.arraycopy(friends[i], 0, resize[i], 0, friends[i].length);
            }

            friends = resize;
        }

        // Map the new relationship
        friends[contactIndex][friendIndex] = 1;
        friends[friendIndex][contactIndex] = 1;
    }

    public void removeFriend() {

    }

    public void displayAllFriends() {

    }

    public void displayMostFriends() {

    }

    public void shortestPathBetweenTwoPeople() {

    }

    @Override
    public String toString() {
        int n = contactMap.size();
        if (n == 0) return "(empty graph)\n";

        // Invert contactMap so we can label rows/columns by index.
        Contact[] byIndex = new Contact[n];
        for (Contact c : contactMap.keySet()) {
            byIndex[contactMap.get(c)] = c;
        }

        int colWidth = 10;
        StringBuilder sb = new StringBuilder();

        // Header: blank corner cell, then a column per contact.
        sb.append(pad("", colWidth));
        for (int j = 0; j < n; j++) {
            sb.append(pad(byIndex[j].getName(), colWidth));
        }
        sb.append('\n');

        // Body: row label, then the 0/1 cells for that row.
        for (int i = 0; i < n; i++) {
            sb.append(pad(byIndex[i].getName(), colWidth));
            for (int j = 0; j < n; j++) {
                sb.append(pad(String.valueOf(friends[i][j]), colWidth));
            }
            sb.append('\n');
        }

        return sb.toString();
    }

    private static String pad(String s, int width) {
        if (s.length() >= width) return s.substring(0, width - 1) + " ";
        return s + " ".repeat(width - s.length());
    }
}
