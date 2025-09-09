import fintech.User;
import fintech.CreditCard;

public class Test {
    public static void main(String[] args) {
        User user = new User("João", "11122233344", "11987654321", "10/10/1980");
        System.out.println("=== Usuário ===");
        System.out.println("Nome: " + user.getName());
        System.out.println("Document: " + user.getDoc());
        user.setPhone("11999998888");
        System.out.println("Telefone atualizado: " + user.getPhone());


        CreditCard creditCard = new CreditCard(
                1, "Visa", "3456", "Cartão Principal", false, user, new java.util.Date(), new java.util.Date(), 5000.0
        );
        creditCard.setCreditLimit(10000.0);

        System.out.println("\n=== Cartão de Crédito ===");
        System.out.println("Últimos dígitos: " + creditCard.getLastFourDigits());
        System.out.println("Limite de Crédito: " + creditCard.getCreditLimit());
        

        creditCard.printInfo();
    }
}