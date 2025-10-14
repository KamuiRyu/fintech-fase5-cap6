package test;

import fintech.models.Category;
import utils.EnvLoader;

import java.sql.Timestamp;
import java.util.List;

public class CategoryTest {
    public static void main() {
        EnvLoader.loadEnvFromResources(".env");

        try {
            Timestamp now = new Timestamp(System.currentTimeMillis());

            // Categorias definidas
            String[] nomes = { "Salário", "Alimentação", "Lazer", "Investimento", "Educação" };
            String[] descricoes = {
                    "Recebimento de salário",
                    "Despesas com alimentação",
                    "Gastos com lazer e entretenimento",
                    "Aplicações e investimentos",
                    "Cursos, livros e educação"
            };
            short[] tipos = { 0, 1, 1, 2, 1 }; // 0=renda, 1=gasto, 2=investimento

            for (int i = 0; i < nomes.length; i++) {
                Category category = new Category(
                        0,
                        nomes[i],
                        descricoes[i],
                        tipos[i],
                        now,
                        now);
                category.create();
                System.out
                        .println("Categoria inserida: " + category.getName() + " (ID banco: " + category.getId() + ")");
            }

            Category search = new Category();
            List<Category> categories = search.getAll();
            System.out.println("Categorias no banco:");
            System.out.printf("%-4s %-18s %-12s %-30s%n", "ID", "Nome", "Tipo", "Descrição");
            System.out.println("-------------------------------------------------------------------------------");
            for (Category c : categories) {
                String tipoStr = switch (c.getType()) {
                    case 0 -> "Renda";
                    case 1 -> "Gasto";
                    case 2 -> "Investimento";
                    default -> "Outro";
                };
                System.out.printf("%-4d %-18s %-12s %-30s%n",
                        c.getId(),
                        c.getName(),
                        tipoStr,
                        c.getDescription());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}