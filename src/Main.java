import fintech.User;

public class Main {
    public static void main(String[] args) {
        // Criando um objeto User
        User user1 = new User("Aline", "12345678900", "11999999999", "01/01/1990");

        // Exibindo dados usando getters
        System.out.println("Nome: " + user1.getName());
        System.out.println("Documento: " + user1.getDoc());
        System.out.println("Telefone: " + user1.getPhone());
        System.out.println("Data de Nascimento: " + user1.getDateOfBirth());

        // Atualizando dados com setters
        user1.setName("Aline Silva");
        user1.setPhone("11988888888");

        System.out.println("\n--- Após atualização ---");
        System.out.println("Nome: " + user1.getName());
        System.out.println("Telefone: " + user1.getPhone());

        // Chamando os métodos de exemplo
        user1.update();
        user1.remove();
    }
}
