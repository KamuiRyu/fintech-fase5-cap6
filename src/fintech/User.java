package fintech;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fintech.dao.UserDAO;

public class User {
    private int id;
    private String name;
    private String doc;
    private String phone;
    private String email;
    private String password;
    private int type;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    UserDAO userDAO = new UserDAO();

    public User(int id, String name, String doc, String phone, String email, String password, int type,
            Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.name = name;
        this.doc = doc;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void update() {
        System.out.println("User name updated successfully!");
    }

    public void remove() {
        System.out.println("User data removed successfully!");
    }

    public Map<String, Object> create() {
        User userExistent = userDAO.getByEmail(email);
        Map<String, Object> response = new HashMap<>();
        if (userExistent != null) {
            response.put("success", false);
            response.put("code", "USER_EXISTS");
            response.put("message", "Usuário com este e-mail já existe.");
            return response;
        }
        userDAO.insert(this);
        //User userFromDb = userDAO.getByEmail(email);
        response.put("success", true);
        response.put("message", "Usuário criado com sucesso!");
        response.put("user", this);
        return response;
    }

    public List<User> getAll() {
        return userDAO.getAll();
    }

    public User getById(int id) {
        return userDAO.getById(id);
    }

    public User getByEmail(String email) {
        return userDAO.getByEmail(email);
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDoc() {
        return doc;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getType() {
        return type;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}