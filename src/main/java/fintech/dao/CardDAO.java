package fintech.dao;

import java.sql.*;
import factory.ConnectionFactory;
import java.util.ArrayList;
import java.util.List;
import fintech.models.Card;

public class CardDAO {

    public List<Card> getAll() throws utils.Exception {
        List<Card> cards = new ArrayList<>();
        String sql = "SELECT * FROM cards ORDER BY id";
        try (Connection conn = ConnectionFactory.getConnection();
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
            throw new utils.Exception("Erro ao consultar cards: " + e.getMessage());
        }
        return cards;
    }

    public Card getById(int id) throws utils.Exception {
        String sql = "SELECT * FROM cards WHERE id = ?";
        try (Connection conn = factory.ConnectionFactory.getConnection();
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
                } else {
                    throw new utils.Exception("Cartão não encontrado para o id: " + id);
                }
            }
        } catch (SQLException e) {
            throw new utils.Exception("Erro ao buscar card por id: " + e.getMessage());
        }
    }

    public void insert(Card card) throws utils.Exception {
        String sql = "INSERT INTO cards (issuer, last_four_digits, alias, shared, owner_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = factory.ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[] { "ID" })) {
    
            stmt.setString(1, card.getIssuer());
            stmt.setString(2, card.getLastFourDigits());
            stmt.setString(3, card.getAlias());
            stmt.setString(4, card.isShared());
            stmt.setInt(5, card.getOwnerId());
            stmt.setTimestamp(6, card.getCreatedAt());
            stmt.setTimestamp(7, card.getUpdatedAt());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new utils.Exception("Falha ao inserir o cartão, nenhum registro afetado.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    card.setId(generatedKeys.getInt(1));
                } else {
                    throw new utils.Exception("Falha ao recuperar o ID do cartão inserido.");
                }
            }
        } catch (SQLException e) {
            throw new utils.Exception("Erro ao inserir card: " + e.getMessage());
        }
    }
}