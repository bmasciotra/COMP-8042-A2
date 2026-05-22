package bonus;

import contact.Contact;

public class ContactAndDistance {

    public Contact contact;
    public double weight;

    public ContactAndDistance(Contact c, double weight) {
        this.contact = c;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Contact : " + this.contact.getName() + " was a distance of: " + this.weight;
    }
}
