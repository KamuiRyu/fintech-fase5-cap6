package test;

import fintech.models.User;
import utils.EnvLoader;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class UserTest {
    public static void main() {
        EnvLoader.loadEnvFromResources(".env");
        try {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            Random random = new Random();

            for (int i = 1; i <= 5; i++) {
                int randomId = random.nextInt(10000) + 1;
                User user = new User(
                    0,
                    "User" + i,
                    "1234567890" + i,
                    "1199999999" + i,
                    "user" + randomId + "@email.com",
                    "senha" + i,
                    1,
                    now,
                    now
                );
                Map<String, Object> response = user.create();

                if (Boolean.FALSE.equals(response.get("success")) && "USER_EXISTS".equals(response.get("code"))) {
                    System.out.println("Usuário já existe. Buscando pelo e-mail...");
                    user = user.getByEmail(user.getEmail());
                    if (user != null) {
                        System.out.println("Usuário encontrado: " + user.getName() + " | Email: " + user.getEmail() + " | ID: " + user.getId());
                    } else {
                        System.out.println("Usuário não encontrado pelo e-mail.");
                    }
                    continue;
                } else {
                    user = response.get("user") instanceof User ? (User) response.get("user") : null;
                }


                if (user != null) {
                    System.out.println("Usuário inserido: " + user.getEmail() + " (ID: " + user.getId() + ")");
                } else {
                    System.out.println("Falha ao inserir usuário User" + i + ": " + response.get("message") + ".");
                }
            }

            User search = new User();
            List<User> users = search.getAll();
            System.out.println("Usuários no banco:");
            System.out.printf("%-4s %-18s %-28s %-14s %-20s%n", "ID", "Nome", "Email", "Telefone", "Criado em");
            System.out.println("-------------------------------------------------------------------------------");
            for (User u : users) {
                System.out.printf("%-4d %-18s %-28s %-14s %-20s%n",
                        u.getId(),
                        u.getName(),
                        u.getEmail(),
                        u.getPhone(),
                        u.getCreatedAt()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}