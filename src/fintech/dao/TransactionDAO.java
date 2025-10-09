package fintech.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import fintech.Transaction;

public class TransactionDAO {
    private static final String URL = fintech.utils.EnvLoader.get("DB_URL");
    private static final String USER = fintech.utils.EnvLoader.get("DB_USER");
    private static final String PASSWORD = fintech.utils.EnvLoader.get("DB_PASSWORD");

    public List<Transaction> getAll(){
        List<Transaction> transactions = new ArrayList<>();
        try (Connection connect = DriverManager.getConnection(URL,USER,PASSWORD);
            PreparedStatement pstt = connect.prepareStatement("SELECT * FROM transactions ORDER BY id");
            ResultSet rs = pstt.executeQuery()){

            while(rs.next()){
                int id = rs.getInt("id");
                String description = rs.getString("description");
                double amount = rs.getDouble("amount");
                Timestamp ocurredAt = rs.getTimestamp("ocurredAt");
                Timestamp dueDate = rs.getTimestamp("dueDate");
                boolean isRecurring = rs.getBoolean("isRecurring");
                boolean isPaid = rs.getBoolean("isPaid");
                Timestamp createdAt = rs.getTimestamp("createdAt");
                Timestamp updatedAt = rs.getTimestamp("updatedAt");

                Transaction transaction = new Transaction( id, description, amount, ocurredAt, dueDate, isRecurring, isPaid, createdAt, updatedAt);
                transactions.add(transaction);
            }
        } catch (SQLException e){
            System.err.println("Erro ao consultar transaction: " + e.getMessage());
        } return transactions;
    }

    public Transaction getById(int id){
        try (Connection connect = DriverManager.getConnection(URL,USER,PASSWORD);
             PreparedStatement pstt = connect.prepareStatement("SELECT * FROM transactions WHERE id = ?")){
            pstt.setInt(1,id);
            try (ResultSet rs = pstt.executeQuery()){
                if (rs.next()){
                    int TransactionId = rs.getInt("id");
                    String description = rs.getString("description");
                    double amount = rs.getDouble("amount");
                    Timestamp ocurredAt = rs.getTimestamp("ocurredAt");
                    Timestamp dueDate = rs.getTimestamp("dueDate");
                    boolean isRecurring = rs.getBoolean("isRecurring");
                    boolean isPaid = rs.getBoolean("isPaid");
                    Timestamp createdAt = rs.getTimestamp("createdAt");
                    Timestamp updatedAt = rs.getTimestamp("updatedAt");

                    return new Transaction(TransactionId, description, amount, ocurredAt, dueDate, isRecurring, isPaid, createdAt, updatedAt);
                }
            }
        }catch(SQLException e){
            System.err.println("Erro ao buscar transaction pelo id: " + e.getMessage());
        }
        return null;
    }

    public void insert(Transaction transaction){
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement pstt = connect.prepareStatement("INSERT INTO transactions (description, amount, ocurredAt, dueDate, isRecurring, isPaid, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")){
            pstt.setString(1, transaction.getDescription());
            pstt.setDouble(2, transaction.getAmount());
            pstt.setTimestamp(3, transaction.getOcurredAt());
            pstt.setTimestamp(4, transaction.getDueDate());
            pstt.setBoolean(5, transaction.isRecurring());
            pstt.setBoolean(6, transaction.isPaid());
            pstt.setTimestamp(7, transaction.getCreatedAt());
            pstt.setTimestamp(8, transaction.getUpdatedAt());
            pstt.executeUpdate();
            try(PreparedStatement selectPstt = connect.prepareStatement("SELECT id FROM transactions WHERE amount = ? AND ocurredAt = ? AND isPaid = ? AND createdAt = ?")){
                    selectPstt.setDouble(1, transaction.getAmount());
                    selectPstt.setTimestamp(2, transaction.getOcurredAt());
                    selectPstt.setBoolean(3,transaction.isPaid());
                    selectPstt.setTimestamp(4, transaction.getCreatedAt());
                    try (ResultSet rs = selectPstt.executeQuery()){
                        if (rs.next()) {
                            transaction.setId(rs.getInt("id"));
                        }
                    }
                }
            }catch (SQLException e) {
            System.err.println("Erro ao inserir a transaction: " + e.getMessage());
        }
    }
}
