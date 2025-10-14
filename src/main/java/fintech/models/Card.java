package fintech.models;

import java.sql.Timestamp;
import java.util.List;

import fintech.dao.CardDAO;
public class Card {
    private int id;
    private String issuer;
    private String lastFourDigits;
    private String alias;
    private String shared;
    private int ownerId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    CardDAO cardDAO = new CardDAO();

    public Card(int id, String issuer, String lastFourDigits, String alias, String shared, int ownerId, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.issuer = issuer;
        this.lastFourDigits = lastFourDigits;
        this.alias = alias;
        this.shared = shared;
        this.ownerId = ownerId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Card create() {
        try {
            cardDAO.insert(this);
        } catch (Exception e) {
            throw new RuntimeException("Failed to insert card", e);
        }
        return this;
    }

    public List<Card> getAll(){
        try {
            return cardDAO.getAll();
        } catch (utils.Exception e) {
            throw new RuntimeException("Failed to get all cards", e);
        }
    }

    public Card getById(int id){
        try {
            return cardDAO.getById(id);
        } catch (utils.Exception e) {
            throw new RuntimeException("Failed to get card by id", e);
        }
    }

    public int getId() { return id; }
    public String getIssuer() { return issuer; }
    public String getLastFourDigits() { return lastFourDigits; }
    public String getAlias() { return alias; }
    public String isShared() { return shared; }
    public int getOwnerId() {return this.ownerId;}
    public Timestamp getCreatedAt() { return createdAt; }
    public Timestamp getUpdatedAt() { return updatedAt; }
    

    public void setId(int id) { this.id = id;}
    public void setAlias(String alias) { this.alias = alias; }
    public void setShared(String shared) { this.shared = shared; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

}