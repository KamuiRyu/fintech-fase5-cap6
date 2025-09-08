package fintech;

public class Login{
    private String email;
    private String password;

    public Login(String email, String password){
       this.email = email;
       this.password = password;
    }

    public void doLogin() {
        System.out.println("Login successful!");
    }

    public void doLogout() {
        System.out.println("Logout successful!");
    }
}