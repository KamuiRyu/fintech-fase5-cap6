import utils.EnvLoader;
import utils.MigrationRunner;
// Importe as classes de teste
import test.UserTest;
import test.CategoryTest;
import test.CardMemberTest;
import test.TransactionTest;
import test.TransactionSplitTest;
import test.ReminderTest;
import test.NotificationTest;

public class Main {
    public static void main(String[] args) {
        EnvLoader.loadEnvFromResources(".env");

        MigrationRunner.main();

        System.out.println("\n=== Teste de Usuário ===");
        UserTest.main();

        System.out.println("\n=== Teste de Categoria ===");
        CategoryTest.main();

        System.out.println("\n=== Teste de CardMember ===");
        CardMemberTest.main();

        System.out.println("\n=== Teste de Transaction ===");
        TransactionTest.main();

        System.out.println("\n=== Teste de TransactionSplit ===");
        TransactionSplitTest.main();

        System.out.println("\n=== Teste de Reminder ===");
        ReminderTest.main();

        System.out.println("\n=== Teste de Notification ===");
        NotificationTest.main();
    }
}