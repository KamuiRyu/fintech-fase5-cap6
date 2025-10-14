package fintech.dao;

import fintech.models.TransactionSplit;
import factory.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionSplitDAO {

    public void insert(TransactionSplit split) throws utils.Exception {
        String sql = "INSERT INTO transaction_splits (transaction_id, user_id, amount, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[] { "ID" })) {
            stmt.setInt(1, split.getTransactionId());
            stmt.setInt(2, split.getUserId());
            stmt.setDouble(3, split.getAmount());
            stmt.setTimestamp(4, split.getCreatedAt());
            stmt.setTimestamp(5, split.getUpdatedAt());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new utils.Exception("Falha ao inserir o split, nenhum registro afetado.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    split.setId(generatedKeys.getInt(1));
                } else {
                    throw new utils.Exception("Falha ao recuperar o ID do split inserido.");
                }
            }
        } catch (SQLException e) {
            throw new utils.Exception("Erro ao inserir split: " + e.getMessage());
        }
    }

    public TransactionSplit getById(int id) throws utils.Exception {
        String sql = "SELECT * FROM transaction_splits WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapSplit(rs);
                } else {
                    throw new utils.Exception("Split não encontrado para o id: " + id);
                }
            }
        } catch (SQLException e) {
            throw new utils.Exception("Erro ao buscar split por id: " + e.getMessage());
        }
    }

    public List<TransactionSplit> getAll() throws utils.Exception {
        List<TransactionSplit> splits = new ArrayList<>();
        String sql = "SELECT * FROM transaction_splits ORDER BY id";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                splits.add(mapSplit(rs));
            }
        } catch (SQLException e) {
            throw new utils.Exception("Erro ao consultar splits: " + e.getMessage());
        }
        return splits;
    }

    private TransactionSplit mapSplit(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int transactionId = rs.getInt("transaction_id");
        int userId = rs.getInt("user_id");
        double amount = rs.getDouble("amount");
        Timestamp createdAt = rs.getTimestamp("created_at");
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        return new TransactionSplit(id, transactionId, userId, amount, createdAt, updatedAt);
    }

    public List<TransactionSplit> getByUserId(int userId) throws utils.Exception {
        List<TransactionSplit> splits = new ArrayList<>();
        String sql = "SELECT * FROM transaction_splits WHERE user_id = ? ORDER BY id";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    splits.add(mapSplit(rs));
                }
            }
        } catch (SQLException e) {
            throw new utils.Exception("Erro ao consultar splits por user_id: " + e.getMessage());
        }
        return splits;
    }
}