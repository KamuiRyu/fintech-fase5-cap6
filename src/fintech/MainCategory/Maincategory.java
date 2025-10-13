package fintech;

import fintech.dao.CategoryDAO;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CategoryDAO dao = new CategoryDAO();
        Date now = new Date();

        System.out.println("=== Inserindo categorias no banco ===");

        // Inserir 5 categorias
        dao.insert(new Category(0, "Investimentos", "Investimentos financeiros", (short)2, now, now));
        dao.insert(new Category(0, "Despesas", "Despesas mensais", (short)1, now, now));
        dao.insert(new Category(0, "Receitas", "Receitas mensais", (short)0, now, now));
        dao.insert(new Category(0, "Empréstimos", "Empréstimos bancários", (short)2, now, now));
        dao.insert(new Category(0, "Outros", "Outros tipos de categoria", (short)1, now, now));

        System.out.println("\n=== Consultando todas as categorias no banco ===");

        // Consultar todas as categorias
        List<Category> categorias = dao.getAll();
        if (categorias.isEmpty()) {
            System.out.println("Nenhuma categoria encontrada.");
        } else {
            categorias.forEach(Category::showInfo);
        }

        System.out.println("\n=== Fim do teste ===");
    }
}

