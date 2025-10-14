package fintech.models;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import fintech.dao.ReminderDAO;

public class Reminder {
    private int id;
    private int transactionId;
    private int userId;
    private Timestamp remindAt;
    private String notified;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    private ReminderDAO reminderDAO = new ReminderDAO();

    public Reminder() {
    }

    public Reminder(int id, int transactionId, int userId, Timestamp remindAt, String notified, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.transactionId = transactionId;
        this.userId = userId;
        this.remindAt = remindAt;
        this.notified = notified;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void sendReminder() {
        this.notified = "Y";
        setUpdatedAt(new Timestamp(new Date().getTime()));
        System.out.println("Lembrete (ID: " + getId() + ") enviado para o usuário (ID: " + getUserId() + ").");
    }

    public Reminder create() {
        try {
            reminderDAO.insert(this);
        } catch (Exception e) {
            throw new RuntimeException("Failed to insert reminder", e);
        }
        return this;
    }

    public List<Reminder> getAll() {
        try {
            return reminderDAO.getAll();
        } catch (utils.Exception e) {
            throw new RuntimeException("Failed to get all reminders", e);
        }
    }

    public List<Reminder> getByUserId(int userId) {
        try {
            return reminderDAO.getByUserId(userId);
        } catch (utils.Exception e) {
            throw new RuntimeException("Failed to get reminders by user id", e);
        }
    }

    public int getTransactionId() { return transactionId; }
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public Timestamp getRemindAt() { return remindAt; }
    public void setRemindAt(Timestamp remindAt) { this.remindAt = remindAt; }
    public String isNotified() { return notified; }
    public void setNotified(String notified) { this.notified = notified; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
}