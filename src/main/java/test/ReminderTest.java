package test;

import fintech.models.User;
import fintech.models.Category;
import fintech.models.Transaction;
import fintech.models.Reminder;
import fintech.models.Card;
import utils.EnvLoader;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class ReminderTest {
    public static void main() {
        EnvLoader.loadEnvFromResources(".env");

        try {
            Timestamp now = new Timestamp(System.currentTimeMillis());

            // 1. Cria ou busca usuário
            User user = new User(
                    0,
                    "Usuário Reminder",
                    "12345678903",
                    "11999999996",
                    "reminder@teste.com",
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
            System.out.println("Usuário: " + user.getEmail() + " (ID: " + user.getId() + ")");

            // 2. Cria uma categoria
            Category category = new Category(
                    0,
                    "Categoria Reminder",
                    "Categoria para Reminder",
                    (short) 1,
                    now,
                    now);
            category.create();
            System.out.println("Categoria criada: " + category.getName() + " (ID: " + category.getId() + ")");

            // 3. Cria um cartão
            Card card = new Card(
                    0,
                    "Banco Reminder",
                    "4321",
                    "Cartão Reminder",
                    "N",
                    user.getId(),
                    now,
                    now);
            card.create();
            System.out.println("Cartão criado: " + card.getAlias() + " (ID: " + card.getId() + ")");

            // 4. Cria uma transação
            
             Transaction transaction = new Transaction(
                        0,
                        category.getId(),
                        card.getId(),
                        user.getId(),
                        "Transação Reminder",
                        150.75,
                        now,
                        now,
                        "N",
                        "N",
                        now,
                        now);
            transaction.create();
            System.out.println(
                    "Transação criada: " + transaction.getDescription() + " (ID: " + transaction.getId() + ")");

            // 5. Cria um lembrete para a transação
            Timestamp remindAt = new Timestamp(System.currentTimeMillis() + 10000); // 10 segundos no futuro
            Reminder reminder = new Reminder(
                    0,
                    transaction.getId(),
                    user.getId(),
                    remindAt,
                    "N",
                    now,
                    now);
            reminder.create();
            System.out
                    .println("Lembrete criado para a transação ID " + transaction.getId() + " no horário " + remindAt);

            Reminder search = new Reminder();
            List<Reminder> reminders = search.getByUserId(user.getId());
            System.out.println("Lembretes no banco:");
            System.out.printf("%-4s %-12s %-8s %-20s %-6s %-20s %-20s%n", "ID", "TransactionID", "UserID", "RemindAt",
                    "Sent", "Criado em", "Atualizado em");
            System.out.println(
                    "---------------------------------------------------------------------------------------------");
            for (Reminder r : reminders) {
                System.out.printf("%-4d %-12d %-8d %-20s %-6s %-20s %-20s%n",
                        r.getId(),
                        r.getTransactionId(),
                        r.getUserId(),
                        r.getRemindAt(),
                        "Y".equals(r.isNotified()) ? "Sim" : "Não",
                        r.getCreatedAt(),
                        r.getUpdatedAt());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}