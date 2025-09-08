package fintech;

import java.util.Date;

public class CardMember {
    private int id;
    private Card card;
    private User user;
    private boolean canManage;
    private Date createdAt;
    private Date updatedAt;

    public CardMember(int id, Card card, User user, boolean canManage, Date createdAt, Date updatedAt) {
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}