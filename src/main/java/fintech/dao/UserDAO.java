package fintech.dao;

import fintech.models.User;
import factory.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public void insert(User user) throws utils.Exception {
        String sql = "INSERT INTO users (email, password, name, doc, phone, type, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, new String[] { "ID" })) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getName());
            stmt.setString(4, user.getDoc());
            stmt.setString(5, user.getPhone());
            stmt.setInt(6, user.getType());
            stmt.setTimestamp(7, user.getCreatedAt());
            stmt.setTimestamp(8, user.getUpdatedAt());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new utils.Exception("Falha ao inserir o usuário, nenhum registro afetado.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                } else {
                    throw new utils.Exception("Falha ao recuperar o ID do usuário inserido.");
                }
            }
        } catch (SQLException e) {
            throw new utils.Exception("Erro ao inserir usuário: " + e.getMessage());
        }
    }

    public User getById(int id) throws utils.Exception {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = factory.ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapUser(rs);
                } else {
                    throw new utils.Exception("Usuário não encontrado para o id: " + id);
                }
            }
        } catch (SQLException e) {
            throw new utils.Exception("Erro ao buscar usuário: " + e.getMessage());
        }
    }

    public List<User> getAll() throws utils.Exception {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = factory.ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(mapUser(rs));
            }
        } catch (SQLException e) {
            throw new utils.Exception("Erro ao buscar usuários: " + e.getMessage());
        }
        return users;
    }

    public User getByIdOrEmail(int id, String email) throws utils.Exception {
        User user = null;
        try {
            user = getById(id);
        } catch (utils.Exception e) {
            // Ignora, tenta pelo email
        }
        if (user == null && email != null && !email.isEmpty()) {
            user = getByEmail(email);
        }
        if (user == null) {
            throw new utils.Exception("Usuário não encontrado para o id ou email informado.");
        }
        return user;
    }

    public User getByEmail(String email) throws utils.Exception {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = factory.ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapUser(rs);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new utils.Exception("Erro ao buscar usuário por email: " + e.getMessage());
        }
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