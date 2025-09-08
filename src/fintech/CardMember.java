package fintech;

public class CardMember {
    private int id;
    private Card card;
    private User user;
    private boolean canManage;
    private java.util.Date createdAt;
    private java.util.Date updatedAt;

    public CardMember(int id, Card card, User user, boolean canManage, java.util.Date createdAt, java.util.Date updatedAt) {
        this.id = id;
        this.card = card;
        this.user = user;
        this.canManage = canManage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isCanManage() {
        return canManage;
    }

    public void setCanManage(boolean canManage) {
        this.canManage = canManage;
    }

    public java.util.Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
    }

    public java.util.Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}