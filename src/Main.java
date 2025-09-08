
import fintech.*;
import fintech.User;

public class Main {
    
    public static void main(String[] args) {
        User user = new User("Alice", "123456789", "555-1234", "1990-01-01");
        user.update();
        user.remove();
    }
}
