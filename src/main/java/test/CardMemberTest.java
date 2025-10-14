package test;

import fintech.models.User;
import fintech.models.Card;
import fintech.models.CardMember;
import utils.EnvLoader;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class CardMemberTest {
    public static void main() {
        EnvLoader.loadEnvFromResources(".env");

        try {
            Timestamp now = new Timestamp(System.currentTimeMillis());

            // Cria ou busca usuário
            User user = new User(
                0,
                "Usuário CardMember",
                "12345678901",
                "11999999998",
                "cardmember@teste.com",
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

            // Cria um cartão para o usuário
            Card card = new Card(
                0,
                "Banco CardMember",
                "5678",
                "Cartão CardMember",
                "N",
                user.getId(),
                now,
                now
            );
            card.create();
            System.out.println("Cartão criado: " + card.getAlias() + " (ID: " + card.getId() + ")");

            // Cria um CardMember
            CardMember cardMember = new CardMember(
                0,
                card.getId(),
                user.getId(),
                true,
                now,
                now
            );
            cardMember.create();
            System.out.println("CardMember criado: user_id=" + user.getId() + ", card_id=" + card.getId());

            CardMember search = new CardMember();
            List<CardMember> members = search.getAll();
            System.out.println("CardMembers no banco:");
            System.out.printf("%-4s %-8s %-8s %-10s %-20s %-20s%n", "ID", "CardID", "UserID", "CanManage", "Criado em", "Atualizado em");
            System.out.println("-------------------------------------------------------------------------------");
            for (CardMember cm : members) {
                System.out.printf("%-4d %-8d %-8d %-10s %-20s %-20s%n",
                        cm.getId(),
                        cm.getCardId(),
                        cm.getUserId(),
                        cm.isCanManage() ? "Sim" : "Não",
                        cm.getCreatedAt(),
                        cm.getUpdatedAt()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}