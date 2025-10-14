package fintech.models;

import java.sql.Timestamp;
import java.util.List;

import fintech.dao.TransactionSplitDAO;

public class TransactionSplit {
    private int id;
    private double amount;
    private int transactionId;
    private int userId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private TransactionSplitDAO transactionSplitDAO = new TransactionSplitDAO();


    public TransactionSplit(){}

    public TransactionSplit(int id, int transactionId, int userId,double amount, Timestamp createdAt, Timestamp updatedAt){
        this.id = id;
        this.amount = amount;
        this.transactionId = transactionId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public TransactionSplit create() {
        try {
            transactionSplitDAO.insert(this);
        } catch (Exception e) {
            throw new RuntimeException("Failed to insert transaction split", e);
        }
        return this;
    }

    public List<TransactionSplit> getByUserId(int userId) {
        try {
            return transactionSplitDAO.getByUserId(userId);
        } catch (utils.Exception e) {
            throw new RuntimeException("Failed to get transaction splits by user id", e);
        }
    }
}
