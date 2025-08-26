public class BankAccount {

    private String holder;         // Titular da conta
    private String accountNumber;  // Número da conta
    private double balance;        // Saldo da conta

    // Construtor com parâmetros (conta já existente)
    public BankAccount(String holder, String accountNumber, double balance) {
        this.holder = holder;
        this.accountNumber = accountNumber;
        this.balance = balance;
        System.out.println("Exibindo informações da conta existente de " + holder);
    }

    // Método para exibir informações da conta
    public void displayInfo() {
        System.out.println("Titular: " + holder);
        System.out.println("Número da conta: " + accountNumber);
        System.out.println("Saldo: $" + balance);
    }
}