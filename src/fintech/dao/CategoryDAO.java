package fintech.dao;

import fintech.Category;
import factory.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    // ============================
    // MÉTODO PARA INSERIR CATEGORIA
    // ============================
    public void insert(Category category) {
        String sql = "INSERT INTO categories (name, description, type, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstt = conn.prepareStatement(sql, new String[]{"id"})) {

            pstt.setString(1, category.getName());
            pstt.setString(2, category.getDescription());
            pstt.setShort(3, category.getType());
            pstt.setTimestamp(4, new Timestamp(category.getCreatedAt().getTime()));
            pstt.setTimestamp(5, new Timestamp(category.getUpdatedAt().getTime()));

            pstt.executeUpdate();

            try (ResultSet rs = pstt.getGeneratedKeys()) {
                if (rs.next()) {
                    category.setId(rs.getInt(1));
                }
            }

            System.out.println("Categoria inserida com sucesso: " + category.getName());

        } catch (SQLException e) {
            System.err.println("Erro ao inserir categoria: " + e.getMessage());
        }
    }

    // ============================
    // MÉTODO PARA BUSCAR TODAS AS CATEGORIAS
    // ============================
    public List<Category> getAll() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstt = conn.prepareStatement(sql);
             ResultSet rs = pstt.executeQuery()) {

            while (rs.next()) {
                Category c = new Category(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getShort("type"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );
                categories.add(c);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao consultar categorias: " + e.getMessage());
        }

        return categories;
    }

    // ============================
    // MÉTODO PARA BUSCAR POR ID
    // ============================
    public Category getById(int id) {
        String sql = "SELECT * FROM categories WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstt = conn.prepareStatement(sql)) {

            pstt.setInt(1, id);
            try (ResultSet rs = pstt.executeQuery()) {
                if (rs.next()) {
                    return new Category(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getShort("type"),
                            rs.getTimestamp("created_at"),
                            rs.getTimestamp("updated_at")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar categoria por ID: " + e.getMessage());
        }

        return null;
    }

    // ============================
    // MÉTODO PARA BUSCAR POR TIPO
    // ============================
    public List<Category> getByType(short type) {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories WHERE type = ? ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstt = conn.prepareStatement(sql)) {

            pstt.setShort(1, type);
            try (ResultSet rs = pstt.executeQuery()) {
                while (rs.next()) {
                    Category c = new Category(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getShort("type"),
                            rs.getTimestamp("created_at"),
                            rs.getTimestamp("updated_at")
                    );
                    categories.add(c);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar categorias por tipo: " + e.getMessage());
        }

        return categories;
    }

    // Métodos de conveniência
    public List<Category> getRendas() { return getByType((short) 0); }
    public List<Category> getGastos() { return getByType((short) 1); }
    public List<Category> getInvestimentos() { return getByType((short) 2); }
}
