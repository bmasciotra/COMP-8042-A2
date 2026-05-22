package contact;

public class Contact implements Comparable<Contact> {
    private String name;
    private String email;
    private String phone;

    public Contact(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String toString() {
        return "Name: " + name + ", Email: " + email + ", Phone: " + phone;
    }

    // for sorting by surname
    public static String extractSurname(String fullName) {
        int lastSpace = fullName.lastIndexOf(' ');
        return lastSpace == -1 ? fullName : fullName.substring(lastSpace + 1);
    }

    /**
     * Comparison function where the name then, email then phone will be evaluated for location in the tree
     */
    @Override
    public int compareTo(Contact o) {
        int bySurname = extractSurname(this.name).compareToIgnoreCase(extractSurname(o.getName()));
        if (bySurname != 0) return bySurname;

        int byName = this.name.compareToIgnoreCase(o.getName());
        if (byName != 0) return byName;

        int byEmail = this.email.compareTo(o.getEmail());
        if (byEmail != 0) return byEmail;

        return this.phone.compareTo(o.getPhone());
    }
}
