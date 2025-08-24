

package src;

public class User {
    private String name;
    private String doc;
    private String phone;
    private String dateOfBirth;


    public User(String name, String doc, String tel, String dateOfBirth){
       this.name = name;
       this.doc = doc;
       this.phone = phone;
       this.dateOfBirth = dateOfBirth;
    }


    public void update() {
        System.out.println("User name updated successfully!");
    }

    public void remove() {
        System.out.println("User data removed successfully!");
    }

}


  