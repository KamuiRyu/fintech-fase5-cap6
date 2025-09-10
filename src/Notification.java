import java.util.Date;

public class Notification {
    private int id;
    private int userId;
    private short type;
    private String content;
    private boolean read;
    private Date createdAt;
    private Date updatedAt;

    public Notification(int id, int userId, short type, String content, boolean read, Date createdAt, Date updatedAt) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.content = content;
        this.read = read;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void markAsRead() {
        this.read = true;
        setUpdatedAt(new Date());
        System.out.println("Notificação (ID: " + getId() + ") marcada como lida.");
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public short getType() { return type; }
    public void setType(short type) { this.type = type; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public boolean isRead() { return read; }
    public void setRead(boolean read) { this.read = read; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}