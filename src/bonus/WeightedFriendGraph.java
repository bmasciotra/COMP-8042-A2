package bonus;

import contact.Contact;
import graphs.FriendGraph;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class WeightedFriendGraph extends FriendGraph {
    final double MAX_WEIGHT = 1;
    final double MIN_WEIGHT = 0;
    // using a double to represent corresponding weights from 0.0 - 1.0
    // where 1.0 is a very close friend and 0.0 is no friend
    public double[][] weights;

    public WeightedFriendGraph() {
        super();

        this.weights = new double[INITIAL_SIZE][INITIAL_SIZE];
    }


    public ContactAndDistance findFirstMatch(Contact contact, String substring) {
        // create a new hashmap containing each contact and their visited status
        HashMap<Contact, Boolean> visited = this.contactsArr
                .stream()
                .collect(Collectors.toMap(
                        c -> c,
                        c -> false,
                        (a, b) -> a,
                        HashMap::new
                ));

        // negatives represent infinite
        HashMap<Contact, Double> distances = this.contactsArr
                .stream()
                .collect(Collectors.toMap(c -> c, e -> -1.0, (a, b) -> a, HashMap::new));

        Contact next = contact;

        // Set the initial user as 0
        distances.put(contact, 0.0);

        while (next != null) {
            // Check for the user we are searching for
            if (next.getName().toLowerCase().startsWith(substring.toLowerCase())) {
                return new ContactAndDistance(next, distances.get(next));
            }

            int contactIndex = this.contactsArr.indexOf(next);

            // mark visited
            visited.put(next, true);

            // get all the relationships
            int[] friends = this.friends[contactIndex];

            for (int i = 0; i < friends.length; i++) {
                // if the user is a friend ie has an edge
                int isFriend = friends[i];

                if (isFriend == FRIEND) {
                    Contact friend = contactsArr.get(i);

                    // if the friend is marked as visited ignore
                    if (visited.get(friend)) continue;

                    // get the users weight between the friends
                    double cost = 1 - weights[contactIndex][i];
                    double calculatedWeight = distances.get(next) + cost;
                    double friendDistance = distances.get(friend);

                    // 1 - the weight to get the shortest distance
                    // ie the closer the friend, 1 - the weight will be the shortest
                    if (friendDistance == -1 || calculatedWeight < friendDistance) {
                        distances.put(friend, calculatedWeight);
                    }
                }
            }

            // traverse the distances finding a contact that hasn't been visited, has distance
            // and is the minimum value ie shortest path
            next = distances
                    .entrySet()
                    .stream()
                    .filter(e -> !visited.get(e.getKey()))
                    .filter(e -> e.getValue() != -1)
                    .min(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse(null);
        }

        return null;
    }

    public void addFriend(Contact contact, Contact friend, double weight) throws IllegalArgumentException {

        if (weight > MAX_WEIGHT || weight < MIN_WEIGHT)
            throw new IllegalArgumentException("Weight must be a valid double between 0 and 1");

        // use friend graph method to populate regular matrix
        this.addFriend(contact, friend);

        // set the weights
        int contactIndex = this.contactsArr.indexOf(contact);
        int friendIndex = this.contactsArr.indexOf(friend);

        // Resize if we need to
        if (weights.length <= contactsArr.size()) {
            resize();
        }

        this.weights[contactIndex][friendIndex] = weight;
        this.weights[friendIndex][contactIndex] = weight;
    }

    @Override
    public void resize() {
        super.resize();
        // Double the size if we have to resize
        double[][] resize = new double[this.contactsArr.size() * 2][this.contactsArr.size() * 2];

        for (int i = 0; i < weights.length; i++) {
            System.arraycopy(weights[i], 0, resize[i], 0, weights[i].length);
        }

        weights = resize;
    }
}
