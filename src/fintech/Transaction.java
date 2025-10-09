package fintech;

import java.util.*;
import java.sql.*;
import fintech.dao.TransactionDAO;

public class Transaction {
    private int id;
    private String description;
    private double amount;
    private Timestamp ocurredAt;
    private Timestamp dueDate;
    private boolean isRecurring;
    private boolean isPaid;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    TransactionDAO transactionDAO = new TransactionDAO();


    public Transaction(){}

    public Transaction(int id, String description, double amount, Timestamp ocurredAt){
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.ocurredAt = ocurredAt;
    }

    public Transaction(int id, String description, double amount, Timestamp ocurredAt, Timestamp dueDate, boolean isRecurring, boolean isPaid, Timestamp createdAt, Timestamp updatedAt){
        this.id = id;
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
        transactionDAO.insert(this);
        return this;
    }
    public List<Transaction> getAll(){
        return transactionDAO.getAll();
    }

    public Transaction getById(int id){
        return transactionDAO.getById(id);
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

    public boolean isRecurring() {
        return isRecurring;
    }

    public void setRecurring(boolean recurring) {
        isRecurring = recurring;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

}
