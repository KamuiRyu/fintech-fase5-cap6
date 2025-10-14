package fintech.models;

import java.sql.Timestamp;

import fintech.dao.CardMemberDAO;

public class CardMember {
    private int id;
    private int cardId;
    private int userId;
    private boolean canManage;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    private CardMemberDAO cardMemberDAO = new CardMemberDAO();

    public CardMember() {
    }

    public CardMember(int id, int cardId, int userId, boolean canManage, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.cardId = cardId;
        this.userId = userId;
        this.canManage = canManage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setUser(int userId) {
        this.userId = userId;
    }

    public boolean isCanManage() {
        return canManage;
    }

    public void setCanManage(boolean canManage) {
        this.canManage = canManage;
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

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public CardMember create() {
        try {
            cardMemberDAO.insert(this);
        } catch (utils.Exception e) {
            throw new RuntimeException("Failed to insert card member", e);
        }
        return this;
    }

    public java.util.List<CardMember> getAll() {
        try {
            return cardMemberDAO.getAll();
        } catch (utils.Exception e) {
            throw new RuntimeException("Failed to get all card members", e);
        }
    }

    public java.util.List<CardMember> getByUserId(int userId) {
        try {
            return cardMemberDAO.getByUserId(userId);
        } catch (utils.Exception e) {
            throw new RuntimeException("Failed to get card members by card id", e);
        }
    }

    public CardMember getById(int id) {
        try {
            return cardMemberDAO.getById(id);
        } catch (utils.Exception e) {
            throw new RuntimeException("Failed to get card member by id", e);
        }
    }
    

}