import fintech.User;
import fintech.Card;
import fintech.utils.EnvLoader;

import java.util.List;
import java.util.Map;
import java.sql.Timestamp;

public class Main {
    public static void main(String[] args) {

        EnvLoader.loadEnv(".env");

        User user = new User(
                0,
                "Aline",
                "12345678900",
                "11999999999",
                "aline@email.com",
                "senhaSegura123",
                1,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()));

        Map<String, Object> userResponse = user.create();
        if (Boolean.FALSE.equals(userResponse.get("success")) && "USER_EXISTS".equals(userResponse.get("code"))) {
            System.out.println("Usuário já existie. Buscando pelo e-mail...");
            user = user.getByEmail(user.getEmail());
            System.out.println("Usuário encontrado: " + user.getName() + " | Email: " + user.getEmail() + " | ID: "
                    + user.getId());
        } else {
            user = userResponse.get("user") instanceof User ? (User) userResponse.get("user") : null;
            System.out.println("Novo usuário criado: " + user.getName() + " | Email: " + user.getEmail() + " | ID: "
                    + user.getId());
        }

        Card card = new Card(0, "Visa", "3456", "Cartão Principal", "N", user.getId(),
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()));
        card = card.create();
        System.out.println("\nCartão criado: " + card.getIssuer() + " | Final: " + card.getLastFourDigits() + " | ID: "
                + card.getId());

        List<Card> cards = card.getAll();
        System.out.println("\n=== Cartões cadastrados ===");
        for (Card c : cards) {
            System.out
                    .println("ID: " + c.getId() + " | Número: " + c.getLastFourDigits() + " | Marca: " + c.getIssuer());
        }
    }
}