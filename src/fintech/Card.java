package fintech;

public abstract class Card {
    private int id;
    private String issuer;
    private String lastFourDigits;
    private String alias;
    private boolean shared;
    private User owner;
    private java.util.Date createdAt;
    private java.util.Date updatedAt;

    public Card(int id, String issuer, String lastFourDigits, String alias, boolean shared, User owner, java.util.Date createdAt, java.util.Date updatedAt) {
        this.id = id;
        this.issuer = issuer;
        this.lastFourDigits = lastFourDigits;
        this.alias = alias;
        this.shared = shared;
        this.owner = owner;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() { return id; }
    public String getIssuer() { return issuer; }
    public String getLastFourDigits() { return lastFourDigits; }
    public String getAlias() { return alias; }
    public boolean isShared() { return shared; }
    public User getOwner() { return owner; }
    public java.util.Date getCreatedAt() { return createdAt; }
    public java.util.Date getUpdatedAt() { return updatedAt; }

    public void setAlias(String alias) { this.alias = alias; }
    public void setShared(boolean shared) { this.shared = shared; }
    public void setUpdatedAt(java.util.Date updatedAt) { this.updatedAt = updatedAt; }

    public abstract void printInfo();
}