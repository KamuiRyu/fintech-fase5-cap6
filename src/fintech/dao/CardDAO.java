package fintech.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import fintech.Card;

public class CardDAO {
    private static final String URL = fintech.utils.EnvLoader.get("DB_URL");
    private static final String USER = fintech.utils.EnvLoader.get("DB_USER");
    private static final String PASSWORD = fintech.utils.EnvLoader.get("DB_PASSWORD");

    public List<Card> getAll() {
        List<Card> cards = new ArrayList<>();
        String sql = "SELECT * FROM cards ORDER BY id";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String issuer = rs.getString("issuer");
                String lastFourDigits = rs.getString("last_four_digits");
                String alias = rs.getString("alias");
                int ownerId = rs.getInt("owner_id");
                String shared = rs.getString("shared"); 
                Timestamp createdAt = rs.getTimestamp("created_at");
                Timestamp updatedAt = rs.getTimestamp("updated_at");
                
                Card card = new Card(id, issuer, lastFourDigits, alias, shared, ownerId, createdAt, updatedAt);
                cards.add(card);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar cards: " + e.getMessage());
        }
        return cards;
    }

    public Card getById(int id) {
        String sql = "SELECT * FROM cards WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int cardId = rs.getInt("id");
                    String issuer = rs.getString("issuer");
                    String lastFourDigits = rs.getString("last_four_digits");
                    String alias = rs.getString("alias");
                    int ownerId = rs.getInt("owner_id");
                    String shared = rs.getString("shared"); 
                    Timestamp createdAt = rs.getTimestamp("created_at");
                    Timestamp updatedAt = rs.getTimestamp("updated_at");
                    
                    
                    return new Card(cardId, issuer, lastFourDigits, alias, shared, ownerId, createdAt, updatedAt);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar card por id: " + e.getMessage());
        }
        return null;
    }

    public void insert(Card card) {
        String sql = "INSERT INTO cards (issuer, last_four_digits, alias, shared, owner_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
    
            stmt.setString(1, card.getIssuer());
            stmt.setString(2, card.getLastFourDigits());
            stmt.setString(3, card.getAlias());
            stmt.setString(4, card.isShared());
            stmt.setInt(5, card.getOwnerId());
            stmt.setTimestamp(6, card.getCreatedAt());
            stmt.setTimestamp(7, card.getUpdatedAt());
            stmt.executeUpdate();
            String selectSql = "SELECT id FROM cards WHERE issuer = ? AND last_four_digits = ? AND owner_id = ? AND created_at = ?";
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                selectStmt.setString(1, card.getIssuer());
                selectStmt.setString(2, card.getLastFourDigits());
                selectStmt.setInt(3, card.getOwnerId());
                selectStmt.setDate(4, new java.sql.Date(card.getCreatedAt().getTime()));
                try (ResultSet rs = selectStmt.executeQuery()) {
                    if (rs.next()) {
                        card.setId(rs.getInt("id"));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir card: " + e.getMessage());
        }
    }
}