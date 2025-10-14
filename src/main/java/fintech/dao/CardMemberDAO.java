package fintech.dao;

import fintech.models.CardMember;
import factory.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardMemberDAO {

    public void insert(CardMember cardMember) throws utils.Exception {
        String sql = "INSERT INTO card_members (card_id, user_id, can_manage, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[] { "ID" })) {
            stmt.setInt(1, cardMember.getCardId());
            stmt.setInt(2, cardMember.getUserId());
            stmt.setBoolean(3, cardMember.isCanManage());
            stmt.setTimestamp(4, cardMember.getCreatedAt());
            stmt.setTimestamp(5, cardMember.getUpdatedAt());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new utils.Exception("Falha ao inserir o card member, nenhum registro afetado.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    cardMember.setId(generatedKeys.getInt(1));
                } else {
                    throw new utils.Exception("Falha ao recuperar o ID do card member inserido.");
                }
            }
        } catch (SQLException e) {
            throw new utils.Exception("Erro ao inserir card member: " + e.getMessage());
        }
    }

    public List<CardMember> getAll() throws utils.Exception {
        List<CardMember> members = new ArrayList<>();
        String sql = "SELECT * FROM card_members ORDER BY id";
        try (Connection conn = factory.ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                members.add(mapCardMember(rs));
            }
        } catch (SQLException e) {
            throw new utils.Exception("Erro ao consultar card members: " + e.getMessage());
        }
        return members;
    }

    public CardMember getById(int id) throws utils.Exception {
        String sql = "SELECT * FROM card_members WHERE id = ?";
        try (Connection conn = factory.ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapCardMember(rs);
                } else {
                    throw new utils.Exception("Card member não encontrado para o id: " + id);
                }
            }
        } catch (SQLException e) {
            throw new utils.Exception("Erro ao buscar card member por id: " + e.getMessage());
        }
    }

    private CardMember mapCardMember(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int cardId = rs.getInt("card_id");
        int userId = rs.getInt("user_id");
        boolean canManage = rs.getBoolean("can_manage");
        Timestamp createdAt = rs.getTimestamp("created_at");
        Timestamp updatedAt = rs.getTimestamp("updated_at");

        return new CardMember(id, cardId, userId, canManage, createdAt, updatedAt);
    }

    public List<CardMember> getByUserId(int userId) throws utils.Exception {
        List<CardMember> members = new ArrayList<>();
        String sql = "SELECT * FROM card_members WHERE user_id = ? ORDER BY id";
        try (Connection conn = factory.ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    members.add(mapCardMember(rs));
                }
            }
        } catch (SQLException e) {
            throw new utils.Exception("Erro ao buscar card members por card_id: " + e.getMessage());
        }
        return members;
    }
}