package fintech;

import java.util.Date;

public class DebitCard extends Card {
    private double balance;

    public DebitCard(int id, String issuer, String lastFourDigits, String alias, boolean shared, User owner, Date createdAt, Date updatedAt, double balance) {
        super(id, issuer, lastFourDigits, alias, shared, owner, createdAt, updatedAt);
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public void printInfo() {
        System.out.println("Cartão de Débito: " + getAlias() + " | Emissor: " + getIssuer() + " | Saldo: " + balance);
    }
}