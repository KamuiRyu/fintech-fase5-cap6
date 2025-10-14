package test;

import fintech.models.Category;
import fintech.models.User;
import fintech.models.Card;
import fintech.models.Transaction;
import utils.EnvLoader;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class TransactionTest {
    public static void main() {
        EnvLoader.loadEnvFromResources(".env");

        try {
            Timestamp now = new Timestamp(System.currentTimeMillis());

            // 1. Cria 3 categorias necessárias
            String[] nomes = { "Salário", "Alimentação", "Investimento" };
            short[] tipos = { 0, 1, 2 };
            Category[] categorias = new Category[3];
            for (int i = 0; i < 3; i++) {
                categorias[i] = new Category(
                        0,
                        nomes[i],
                        nomes[i] + " padrão",
                        tipos[i],
                        now,
                        now);
                categorias[i].create();
                System.out.println(
                        "Categoria criada: " + categorias[i].getName() + " (ID: " + categorias[i].getId() + ")");
            }

            // 2. Cria um usuário
            User user = new User(
                    0,
                    "Usuário Teste",
                    "12345678900",
                    "11999999999",
                    "usuario@teste.com",
                    "senha123",
                    1,
                    now,
                    now);
            Map<String, Object> response = user.create();
            if (Boolean.FALSE.equals(response.get("success")) && "USER_EXISTS".equals(response.get("code"))) {
                System.out.println("Usuário já existe. Buscando pelo e-mail...");
                user = user.getByEmail(user.getEmail());
            } else {
                user = response.get("user") instanceof User ? (User) response.get("user") : null;
            }
            System.out.println("Usuário criado: " + user.getEmail() + " (ID: " + user.getId() + ")");

            // 3. Cria um cartão
            Card card = new Card(
                    0,
                    "Banco Teste",
                    "1234",
                    "Cartão Teste",
                    "N",
                    user.getId(),
                    now,
                    now);
            card.create();
            System.out.println("Cartão criado: " + card.getAlias() + " (ID: " + card.getId() + ")");

            for (int i = 0; i < 3; i++) {
                Transaction transaction = new Transaction(
                        0,
                        categorias[i].getId(),
                        card.getId(),
                        user.getId(),
                        "Transação " + (i + 1),
                        100.0 * (i + 1),
                        now,
                        now,
                        "N",
                        "N",
                        now,
                        now);
                transaction.create();
                System.out.println("Transação criada: " + transaction.getDescription() +
                        " (user_id: " + user.getId() +
                        ", card_id: " + card.getId() +
                        ", category_id: " + categorias[i].getId() + ")");
            }

            // Consulta todas as transações
            Transaction search = new Transaction();
            List<Transaction> transactions = search.getByUserId(user.getId());
            System.out.println("Transações no banco:");
            System.out.printf("%-4s %-15s %-10s %-8s %-8s %-8s %-20s %-20s%n",
                    "ID", "Descrição", "Valor", "UserID", "CardID", "CatID", "Criada em", "Atualizada em");
            System.out.println("-------------------------------------------------------------------------------");

            for (Transaction t : transactions) {
                System.out.printf("%-4d %-15s %-10.2f %-8d %-8d %-8d %-20s %-20s%n",
                        t.getId(),
                        t.getDescription(),
                        t.getAmount(),
                        t.getUserId(),
                        t.getCardId(),
                        t.getCategoryId(),
                        t.getCreatedAt(),
                        t.getUpdatedAt());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}