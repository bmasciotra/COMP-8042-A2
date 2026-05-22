package graphs;

import contact.Contact;

import java.util.*;
import java.util.stream.IntStream;

public class FriendGraph {

    protected final int NOT_VISITED = -1;
    protected final int FRIEND = 1;
    protected final int INITIAL_DIST = 0;
    protected final int INITIAL_SIZE = 2;

    protected final ArrayList<Contact> contactsArr;

    // an adjacency matrix where a contact is represented by an index and their relationships by another int, 0 for no
    // relationship and 1 for relationship
    protected int[][] friends = new int[INITIAL_SIZE][INITIAL_SIZE];


    public FriendGraph() {
        contactsArr = new ArrayList<>();
    }


    /**
     * Add friend allows a main contact (ie the friend) to add a new friend relationship to the graph
     * this contact will now have a new edge for that friend, and it will be represented as 1 or 0 where 1 is there is a
     * relationship
     *
     * @param contact the contact adding the friendship edge
     * @param friend  the friend adding the contacts edge
     */
    public void addFriend(Contact contact, Contact friend) {

        // Add the users to the contact map OR grab their index
        int contactIndex = contactsArr.indexOf(contact);

        if (contactIndex == -1) {
            contactsArr.add(contact);
            contactIndex = contactsArr.size() - 1;
        }

        int friendIndex = contactsArr.indexOf(friend);

        if (friendIndex == -1) {
            contactsArr.add(friend);
            friendIndex = contactsArr.size() - 1;
        }

        // After we have added the users to the map, check the size of the friends array to resize if necessary
        if (friends.length <= contactsArr.size()) {
            resize();
        }

        // Map the new relationship
        friends[contactIndex][friendIndex] = FRIEND;
        friends[friendIndex][contactIndex] = FRIEND;
    }

    public void removeFriend(Contact contact, Contact friend) {
        int contactIndex = contactsArr.indexOf(contact);
        int friendIndex = contactsArr.indexOf(friend);

        if (contactIndex == -1 || friendIndex == -1) return;

        friends[contactIndex][friendIndex] = 0;
        friends[friendIndex][contactIndex] = 0;
    }

    /**
     * Displays a contacts entire friend network
     *
     * @param c the contact
     */
    public void displayAllFriends(Contact c) {

        int adjacencyIndex = contactsArr.indexOf(c);
        int[] friendsList = friends[adjacencyIndex];

        // find all the contacts friend/relationships
        Contact[] contactList = IntStream.range(0, contactsArr.size()).filter(i -> friendsList[i] == FRIEND).mapToObj(contactsArr::get).toArray(Contact[]::new);

        System.out.format("The following friends are \"%s's\" friends:\n", c.getName());

        for (Contact contact : contactList) {
            System.out.println(contact);
        }

        System.out.println();
    }

    /**
     * Displays the contact with the most friends-of-friends (people reachable
     * in exactly two hops in the friendship graph, excluding the contact
     * themselves and their direct friends).
     */
    public void displayMostFriends() {

        Contact bestContact = null;
        int bestCount = -1;

        for (int i = 0; i < this.contactsArr.size(); i++) {
            int[] relationships = this.friends[i];
            Set<Integer> fof = getFofCount(relationships, i);

            if (fof.size() > bestCount) {
                bestCount = fof.size();
                bestContact = contactsArr.get(i);
            }
        }

        System.out.println(bestContact);
    }


    /**
     * Utilizing a BFS style algorithm, traverses the adjacency matrix to find the shortest distance between
     * two people and returns the list (ordered)
     *
     * @param contact the contact we are starting from
     * @param friend  the friend we are traversing to from the contact
     * @return a linked list mapping out the shortest path traversal from contact to friend
     */
    public LinkedList<Contact> shortestPathBetweenTwoPeople(Contact contact, Contact friend) {

        LinkedList<Contact> queue = new LinkedList<>();
        HashMap<Contact, Integer> distances = new HashMap<>();
        HashMap<Contact, Contact> predecessor = new HashMap<>();

        for (Contact c : this.contactsArr) {
            // -1 is our infinity and the initial value to know that a contact has not been visited
            distances.put(c, NOT_VISITED);
        }

        if (contact == friend) {
            System.out.println("No distance");
            return new LinkedList<>() {
            };
        }

        queue.add(contact);
        distances.put(contact, INITIAL_DIST);

        while (!queue.isEmpty()) {
            Contact c = queue.poll(); // dequeue
            int index = contactsArr.indexOf(c);

            for (int i = 0; i < this.contactsArr.size(); i++) {
                Contact f = this.contactsArr.get(i);

                // contact is not a neighbor if adjacency shows 0
                if (friends[index][i] != FRIEND) continue;
                // if distances registers -1 that user has not been visited
                if (distances.get(f) != NOT_VISITED) continue;

                distances.put(f, distances.get(c) + 1);
                predecessor.put(f, c);
                queue.add(f); // enqueue only when newly discovered
            }
        }

        // Reconstruct the path by walking predecessors backwards from friend
        LinkedList<Contact> path = new LinkedList<>();

        if (distances.get(friend) == NOT_VISITED) {
            return path;                 // no path exists — return empty
        }

        for (Contact step = friend; step != null; step = predecessor.get(step)) {
            path.addFirst(step);
        }

        return path;
    }

    /**
     * Prints the given path of the traveral
     *
     * @param path a linked list represent the shortest path of a contact to another contact
     */
    public void printPath(LinkedList<Contact> path) {
        if (path.isEmpty()) {
            System.out.println("No path exists");
            return;
        }

        if (path.size() == 1) {
            System.out.println(path.getFirst().getName());
            return;
        }

        StringBuilder sb = new StringBuilder("through ");

        sb.append(path.get(0).getName()).append("'s friend ").append(path.get(1).getName());

        for (int i = 2; i < path.size(); i++) {
            sb.append(" who knows ").append(path.get(i).getName());
        }

        System.out.println(sb);
    }


    @Override
    public String toString() {
        int n = contactsArr.size();
        if (n == 0) return "(empty graph)\n";

        Contact[] byIndex = new Contact[n];

        for (int i = 0; i < this.contactsArr.size(); i++) {
            byIndex[i] = this.contactsArr.get(i);
        }

        int colWidth = 10;
        StringBuilder sb = new StringBuilder();

        sb.append(pad("", colWidth));

        for (int j = 0; j < n; j++) {
            sb.append(pad(byIndex[j].getName(), colWidth));
        }

        sb.append('\n');

        for (int i = 0; i < n; i++) {
            sb.append(pad(byIndex[i].getName(), colWidth));

            for (int j = 0; j < n; j++) {
                sb.append(pad(String.valueOf(friends[i][j]), colWidth));
            }

            sb.append('\n');
        }

        return sb.toString();
    }


    protected void resize() {
        int[][] resize = new int[contactsArr.size() * 2][contactsArr.size() * 2];

        for (int i = 0; i < friends.length; i++) {
            System.arraycopy(friends[i], 0, resize[i], 0, friends[i].length);
        }

        friends = resize;

    }

    private static String pad(String s, int width) {
        if (s.length() >= width) return s.substring(0, width - 1) + " ";
        return s + " ".repeat(width - s.length());
    }


    private Set<Integer> getFofCount(int[] relationships, int i) {
        Set<Integer> fof = new HashSet<>();

        for (int j = 0; j < relationships.length; j++) {
            // if no relationship skip
            if (relationships[j] != FRIEND) continue;

            // traverse the friends of the friend
            for (int k = 0; k < this.friends[j].length; k++) {
                // if the contact is not a friend, or the contact in focus or the focus contacts friend already
                if (this.friends[j][k] != FRIEND || k == i || relationships[k] == FRIEND) continue;

                fof.add(k);
            }
        }
        return fof;
    }
}
