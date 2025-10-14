package fintech.models;

import java.util.*;
import java.sql.*;
import fintech.dao.TransactionDAO;

public class Transaction {
    private int id;
    private int categoryId;
    private int cardId;
    private int userId;
    private String description;
    private double amount;
    private Timestamp ocurredAt;
    private Timestamp dueDate;
    private String isRecurring;
    private String isPaid;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    TransactionDAO transactionDAO = new TransactionDAO();


    public Transaction(){}

    public Transaction(int id, int categoryId, int cardId, int userId, String description, double amount, Timestamp ocurredAt, Timestamp dueDate, String isRecurring, String isPaid, Timestamp createdAt, Timestamp updatedAt){
        this.id = id;
        this.categoryId = categoryId;
        this.cardId = cardId;
        this.userId = userId;
        this.description = description;
        this.amount = amount;
        this.ocurredAt = ocurredAt;
        this.dueDate = dueDate;
        this.isRecurring = isRecurring;
        this.isPaid = isPaid;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Transaction create(){
        try {
            transactionDAO.insert(this);
        } catch (Exception e) {
            throw new RuntimeException("Failed to insert transaction", e);
        }
        return this;
    }
    public List<Transaction> getAll(){
        try {
            return transactionDAO.getAll();
        } catch (utils.Exception e) {
            throw new RuntimeException("Failed to get all transactions", e);
        }
        
    }

    public List<Transaction> getByUserId(int userId) {
        try {
            return transactionDAO.getByUserId(userId);
        } catch (utils.Exception e) {
            throw new RuntimeException("Failed to get transactions by user id", e);
        }
    }


    public Transaction getById(int id){
        try {
            return transactionDAO.getById(id);
        } catch (utils.Exception e) {
            throw new RuntimeException("Failed to get transaction by id", e);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Timestamp getOcurredAt() {
        return ocurredAt;
    }

    public void setOcurredAt(Timestamp ocurredAt) {
        this.ocurredAt = ocurredAt;
    }

    public Timestamp getDueDate() {
        return dueDate;
    }

    public void setDueDate(Timestamp dueDate) {
        this.dueDate = dueDate;
    }

    public String isRecurring() {
        return isRecurring;
    }

    public void setRecurring(String recurring) {
        isRecurring = recurring;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String isPaid() {
        return isPaid;
    }

    public void setPaid(String paid) {
        isPaid = paid;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    public int getCardId() {
        return cardId;
    }
    public void setCardId(int cardId) {
        this.cardId = cardId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

}
