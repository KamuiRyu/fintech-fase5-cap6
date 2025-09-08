package fintech;

public class CreditCard extends Card {
    private double creditLimit;

    public CreditCard(int id, String issuer, String lastFourDigits, String alias, boolean shared, User owner, java.util.Date createdAt, java.util.Date updatedAt, double creditLimit) {
        super(id, issuer, lastFourDigits, alias, shared, owner, createdAt, updatedAt);
        this.creditLimit = creditLimit;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    @Override
    public void printInfo() {
        System.out.println("Cartão de Crédito: " + getAlias() + " | Emissor: " + getIssuer() + " | Limite: " + creditLimit);
    }
}