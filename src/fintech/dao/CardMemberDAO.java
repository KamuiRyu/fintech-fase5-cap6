package fintech.dao;

import fintech.CardMember;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardMemberDAO {
    private static final String URL = fintech.utils.EnvLoader.get("DB_URL");
    private static final String USER = fintech.utils.EnvLoader.get("DB_USER");
    private static final String PASSWORD = fintech.utils.EnvLoader.get("DB_PASSWORD");

    public void insert(CardMember cardMember) {
        String sql = "INSERT INTO card_members (card_id, user_id, can_manage, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, cardMember.getCardId());
            stmt.setInt(2, cardMember.getUserId());
            stmt.setBoolean(3, cardMember.isCanManage());
            stmt.setTimestamp(4, cardMember.getCreatedAt());
            stmt.setTimestamp(5, cardMember.getUpdatedAt());
            stmt.executeUpdate();
            String selectSql = "SELECT id FROM card_members WHERE card_id = ? AND user_id = ? AND created_at = ?";
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                selectStmt.setInt(1, cardMember.getCardId());
                selectStmt.setInt(2, cardMember.getUserId());
                selectStmt.setTimestamp(3, cardMember.getCreatedAt() != null ? new java.sql.Timestamp(cardMember.getCreatedAt().getTime()) : null);
                try (ResultSet rs = selectStmt.executeQuery()) {
                    if (rs.next()) {
                        cardMember.setId(rs.getInt("id"));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir card member: " + e.getMessage());
        }
    }

    public List<CardMember> getAll() {
        List<CardMember> members = new ArrayList<>();
        String sql = "SELECT * FROM card_members ORDER BY id";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                members.add(mapCardMember(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar card members: " + e.getMessage());
        }
        return members;
    }

    public CardMember getById(int id) {
        String sql = "SELECT * FROM card_members WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapCardMember(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar card member por id: " + e.getMessage());
        }
        return null;
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
}