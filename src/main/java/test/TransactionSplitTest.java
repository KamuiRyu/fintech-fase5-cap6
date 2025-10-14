package test;

import fintech.models.User;
import fintech.models.Category;
import fintech.models.Transaction;
import fintech.models.Card;
import fintech.models.TransactionSplit;
import utils.EnvLoader;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class TransactionSplitTest {
    public static void main() {
        EnvLoader.loadEnvFromResources(".env");

        try {
            Timestamp now = new Timestamp(System.currentTimeMillis());

            // 1. Cria ou busca usuário
            User user = new User(
                0,
                "Usuário Split",
                "12345678904",
                "11999999995",
                "split@teste.com",
                "senha123",
                1,
                now,
                now
            );
            Map<String, Object> response = user.create();
            if (Boolean.FALSE.equals(response.get("success")) && "USER_EXISTS".equals(response.get("code"))) {
                System.out.println("Usuário já existe. Buscando pelo e-mail...");
                user = user.getByEmail(user.getEmail());
            } else {
                user = response.get("user") instanceof User ? (User) response.get("user") : null;
            }
            System.out.println("Usuário: " + user.getEmail() + " (ID: " + user.getId() + ")");

            // 2. Cria uma categoria
            Category category = new Category(
                0,
                "Categoria Split",
                "Categoria para Split",
                (short) 1,
                now,
                now
            );
            category.create();
            System.out.println("Categoria criada: " + category.getName() + " (ID: " + category.getId() + ")");

            // 3. Cria um cartão
            Card card = new Card(
                0,
                "Banco Split",
                "8765",
                "Cartão Split",
                "N",
                user.getId(),
                now,
                now
            );
            card.create();
            System.out.println("Cartão criado: " + card.getAlias() + " (ID: " + card.getId() + ")");

            // 4. Cria uma transação
            Transaction transaction = new Transaction(
                0,
                category.getId(),
                card.getId(),
                user.getId(),
                "Transação para Split",
                200.0,
                now,
                now,
                "N",
                "N",
                now,
                now
            );
            transaction.create();
            System.out.println("Transação criada: " + transaction.getDescription() + " (ID: " + transaction.getId() + ")");

            // 5. Cria um TransactionSplit
            TransactionSplit split = new TransactionSplit(
                0,
                transaction.getId(),
                user.getId(),
                100.0,
                now,
                now
            );
            split.create();
            System.out.println("TransactionSplit criado: transaction_id=" + transaction.getId() + ", user_id=" + user.getId() + ", amount=100.0");

            // Lista todos os splits
            TransactionSplit search = new TransactionSplit();
            List<TransactionSplit> splits = search.getByUserId(user.getId());
            System.out.println("TransactionSplits no banco:");
            System.out.printf("%-4s %-14s %-8s %-10s %-20s %-20s%n", "ID", "TransactionID", "UserID", "Amount", "Criado em", "Atualizado em");
            System.out.println("-------------------------------------------------------------------------------");
            for (TransactionSplit ts : splits) {
                System.out.printf("%-4d %-14d %-8d %-10.2f %-20s %-20s%n",
                        ts.getId(),
                        ts.getTransactionId(),
                        ts.getUserId(),
                        ts.getAmount(),
                        ts.getCreatedAt(),
                        ts.getUpdatedAt()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
}