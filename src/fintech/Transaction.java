import java.util.Date;

public abstract class Transaction {
    private int id;
    private String description;
    private double amount;
    private Date ocurredAt;
    private Date dueDate;
    private boolean isRecurring;
    private boolean isPaid;
    private Date createdAt;
    private Date updatedAt;


    public Transaction(){}

    public Transaction(int id, String description, double amount, Date ocurredAt){
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.ocurredAt = ocurredAt;
    }

    public Transaction(int id, String description, double amount, Date ocurredAt, Date dueDate, boolean isRecurring, boolean isPaid, Date createdAt, Date updatedAt){
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

    public Date getOcurredAt() {
        return ocurredAt;
    }

    public void setOcurredAt(Date ocurredAt) {
        this.ocurredAt = ocurredAt;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isRecurring() {
        return isRecurring;
    }

    public void setRecurring(boolean recurring) {
        isRecurring = recurring;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public abstract void processTransaction();
}
