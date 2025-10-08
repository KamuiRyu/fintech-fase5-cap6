package fintech;

import java.sql.Timestamp;
import java.util.Date;
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

    public Card create(){
        cardDAO.insert(this);
        return this;
    }

    public List<Card> getAll(){
        return cardDAO.getAll();
    }

    public Card getById(int id){
        return cardDAO.getById(id);
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