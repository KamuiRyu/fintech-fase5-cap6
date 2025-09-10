package fintech;

import java.util.Date;

public class Reminder {
    private int id;
    private int transactionId;
    private int userId;
    private Date remindAt;
    private boolean notified;
    private Date createdAt;
    private Date updatedAt;

    public Reminder(int id, int transactionId, int userId, Date remindAt, boolean notified, Date createdAt, Date updatedAt) {
        this.id = id;
        this.transactionId = transactionId;
        this.userId = userId;
        this.remindAt = remindAt;
        this.notified = notified;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void sendReminder() {
        Date now = new Date();
        if (!this.notified && now.after(this.remindAt)) {
            System.out.println("Enviando lembrete para a transação ID: " + this.transactionId);
            this.notified = true;
            setUpdatedAt(now);
        } else if (this.notified) {
            System.out.println("Lembrete para a transação ID: " + this.transactionId + " já foi enviado.");
        } else {
            System.out.println("Ainda não é hora de enviar o lembrete para a transação ID: " + this.transactionId);
        }
    }

    public int getTransactionId() { return transactionId; }
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public Date getRemindAt() { return remindAt; }
    public void setRemindAt(Date remindAt) { this.remindAt = remindAt; }
    public boolean isNotified() { return notified; }
    public void setNotified(boolean notified) { this.notified = notified; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}