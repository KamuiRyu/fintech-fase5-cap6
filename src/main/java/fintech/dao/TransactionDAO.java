package fintech.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import fintech.models.Transaction;
import factory.ConnectionFactory;

public class TransactionDAO {

    public List<Transaction> getAll() throws utils.Exception {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions ORDER BY id";
        try (Connection connect = ConnectionFactory.getConnection();
             PreparedStatement pstt = connect.prepareStatement(sql);
             ResultSet rs = pstt.executeQuery()) {

            while(rs.next()){
                int id = rs.getInt("id");
                int categoryId = rs.getInt("category_id");
                int cardId = rs.getInt("card_id");
                int userId = rs.getInt("user_id");
                String description = rs.getString("description");
                double amount = rs.getDouble("amount");
                Timestamp occurredAt = rs.getTimestamp("occurred_at");
                Timestamp dueDate = rs.getTimestamp("due_date");
                String isRecurring = rs.getString("is_recurring");
                String isPaid = rs.getString("is_paid");
                Timestamp createdAt = rs.getTimestamp("created_at");
                Timestamp updatedAt = rs.getTimestamp("updated_at");

                Transaction transaction = new Transaction(id, categoryId, cardId, userId, description, amount, occurredAt, dueDate, isRecurring, isPaid, createdAt, updatedAt);
                transactions.add(transaction);
            }
        } catch (SQLException e){
            throw new utils.Exception("Erro ao consultar transactions: " + e.getMessage());
        }
        return transactions;
    }

    public Transaction getById(int id) throws utils.Exception {
        String sql = "SELECT * FROM transactions WHERE id = ?";
        try (Connection connect = factory.ConnectionFactory.getConnection();
             PreparedStatement pstt = connect.prepareStatement(sql)) {
            pstt.setInt(1, id);
            try (ResultSet rs = pstt.executeQuery()){
                if (rs.next()){
                    int transactionId = rs.getInt("id");
                    int categoryId = rs.getInt("category_id");
                    int cardId = rs.getInt("card_id");
                    int userId = rs.getInt("user_id");
                    String description = rs.getString("description");
                    double amount = rs.getDouble("amount");
                    Timestamp occurredAt = rs.getTimestamp("occurred_at");
                    Timestamp dueDate = rs.getTimestamp("due_date");
                    String isRecurring = rs.getString("is_recurring");
                    String isPaid = rs.getString("is_paid");
                    Timestamp createdAt = rs.getTimestamp("created_at");
                    Timestamp updatedAt = rs.getTimestamp("updated_at");

                    return new Transaction(transactionId, categoryId, cardId, userId,description, amount, occurredAt, dueDate, isRecurring, isPaid, createdAt, updatedAt);
                } else {
                    throw new utils.Exception("Transaction não encontrada para o id: " + id);
                }
            }
        } catch(SQLException e){
            throw new utils.Exception("Erro ao buscar transaction pelo id: " + e.getMessage());
        }
    }

    public void insert(Transaction transaction) throws utils.Exception {
        String sql = "INSERT INTO transactions (category_id, card_id, user_id, description, amount, occurred_at, due_date, is_recurring, is_paid, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connect = factory.ConnectionFactory.getConnection();
             PreparedStatement pstt = connect.prepareStatement(sql, new String[] { "ID" })) {
            pstt.setInt(1, transaction.getCategoryId());
            pstt.setInt(2, transaction.getCardId());
            pstt.setInt(3, transaction.getUserId());
            pstt.setString(4, transaction.getDescription());
            pstt.setDouble(5, transaction.getAmount());
            pstt.setTimestamp(6, transaction.getOcurredAt());
            pstt.setTimestamp(7, transaction.getDueDate());
            pstt.setString(8, transaction.isRecurring());
            pstt.setString(9, transaction.isPaid());
            pstt.setTimestamp(10, transaction.getCreatedAt());
            pstt.setTimestamp(11, transaction.getUpdatedAt());
            int affectedRows = pstt.executeUpdate();
            if (affectedRows == 0) {
                throw new utils.Exception("Falha ao inserir a transaction, nenhum registro afetado.");
            }
            try (ResultSet generatedKeys = pstt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    transaction.setId(generatedKeys.getInt(1));
                } else {
                    throw new utils.Exception("Falha ao recuperar o ID da transaction inserida.");
                }
            }
        } catch (SQLException e) {
            throw new utils.Exception("Erro ao inserir a transaction: " + e.getMessage());
        }
    }

    public List<Transaction> getByUserId(int userId) throws utils.Exception {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE user_id = ? ORDER BY id";
        try (Connection connect = ConnectionFactory.getConnection();
             PreparedStatement pstt = connect.prepareStatement(sql)) {
            pstt.setInt(1, userId);
            try (ResultSet rs = pstt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int categoryId = rs.getInt("category_id");
                    int cardId = rs.getInt("card_id");
                    String description = rs.getString("description");
                    double amount = rs.getDouble("amount");
                    Timestamp occurredAt = rs.getTimestamp("occurred_at");
                    Timestamp dueDate = rs.getTimestamp("due_date");
                    String isRecurring = rs.getString("is_recurring");
                    String isPaid = rs.getString("is_paid");
                    Timestamp createdAt = rs.getTimestamp("created_at");
                    Timestamp updatedAt = rs.getTimestamp("updated_at");

                    Transaction transaction = new Transaction(id, categoryId, cardId, userId, description, amount, occurredAt, dueDate, isRecurring, isPaid, createdAt, updatedAt);
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            throw new utils.Exception("Erro ao consultar transactions por user_id: " + e.getMessage());
        }
        return transactions;
    }
}