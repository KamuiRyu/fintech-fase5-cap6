package test;

import fintech.models.User;
import fintech.models.Notification;
import utils.EnvLoader;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class NotificationTest {
    public static void main() {
        EnvLoader.loadEnvFromResources(".env");

        try {
            Timestamp now = new Timestamp(System.currentTimeMillis());

            // Cria ou busca usuário
            User user = new User(
                0,
                "Usuário Notificação",
                "12345678902",
                "11999999997",
                "notification@teste.com",
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

            // Cria algumas notificações para o usuário
            for (int i = 1; i <= 3; i++) {
                Notification notification = new Notification(
                    0,
                    user.getId(),
                    (short) (i % 2), // tipo alternando
                    "Conteúdo da notificação " + i,
                    false,
                    now,
                    now
                );
                notification.create();
                System.out.println("Notificação criada: " + notification.getContent() + " (ID: " + notification.getId() + ")");
            }

            // Lista todas as notificações
            Notification search = new Notification();
            List<Notification> notifications = search.getByUserId(user.getId());
            System.out.println("Notificações no banco:");
            System.out.printf("%-4s %-8s %-6s %-30s %-8s %-20s %-20s%n", "ID", "UserID", "Tipo", "Conteúdo", "Lida", "Criada em", "Atualizada em");
            System.out.println("---------------------------------------------------------------------------------------------");
            for (Notification n : notifications) {
                System.out.printf("%-4d %-8d %-6d %-30s %-8s %-20s %-20s%n",
                        n.getId(),
                        n.getUserId(),
                        n.getType(),
                        n.getContent(),
                        n.isRead() ? "Sim" : "Não",
                        n.getCreatedAt(),
                        n.getUpdatedAt()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}