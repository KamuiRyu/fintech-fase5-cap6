package fintech;
public class User {
    private String name;
    private String doc;
    private String phone;
    private String dateOfBirth;

    public User(String name, String doc, String tel, String dateOfBirth) {
        this.name = name;
        this.doc = doc;
        this.phone = tel;
        this.dateOfBirth = dateOfBirth;
    }

    public void update() {
        System.out.println("User name updated successfully!");
    }

    public void remove() {
        System.out.println("User data removed successfully!");
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDoc() {
        return doc;
    }

    public String getPhone() {
        return phone;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
