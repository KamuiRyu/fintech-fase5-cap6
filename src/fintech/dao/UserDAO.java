package fintech.dao;

import fintech.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final String URL = fintech.utils.EnvLoader.get("DB_URL");
    private static final String USER = fintech.utils.EnvLoader.get("DB_USER");
    private static final String PASSWORD = fintech.utils.EnvLoader.get("DB_PASSWORD");

    public void insert(User user) {
        String sql = "INSERT INTO users (email, password, name, doc, phone, type, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getName());
            stmt.setString(4, user.getDoc());
            stmt.setString(5, user.getPhone());
            stmt.setInt(6, user.getType());
            stmt.setTimestamp(7, user.getCreatedAt()); 
            stmt.setTimestamp(8, user.getUpdatedAt());
    
            stmt.executeUpdate();
            User userFromDb = getByEmail(user.getEmail());
            if (userFromDb != null) {
                user.setId(userFromDb.getId());
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir usuário: " + e.getMessage());
        }
    }

    public User getById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapUser(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário: " + e.getMessage());
        }
        return null;
    }

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(mapUser(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuários: " + e.getMessage());
        }
        return users;
    }

    public User getByIdOrEmail(int id, String email) {
        User user = getById(id);
        if (user == null && email != null && !email.isEmpty()) {
            user = getByEmail(email);
        }
        return user;
    }
    
    public User getByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapUser(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário por email: " + e.getMessage());
        }
        return null;
    }

    private User mapUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("doc"),
                rs.getString("phone"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getInt("type"),
                rs.getTimestamp("created_at"),
                rs.getTimestamp("updated_at"));
    }
}