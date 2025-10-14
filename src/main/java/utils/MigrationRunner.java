package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import factory.ConnectionFactory;

public class MigrationRunner {
    public static void main() {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL migrationsUrl = classLoader.getResource("migrations");
            if (migrationsUrl == null) {
                System.out.println("Pasta de migrations não encontrada em resources.");
                return;
            }
            File folder = new File(migrationsUrl.getPath());
            File[] files = folder.listFiles((dir, name) -> name.endsWith(".sql") && !name.startsWith("."));

            if (files == null || files.length == 0) {
                System.out.println("Nenhum arquivo de migration encontrado em: " + folder.getAbsolutePath());
                return;
            }

            java.util.Arrays.sort(files);

            try (Connection conn = ConnectionFactory.getConnection()) {
                for (File file : files) {
                    System.out.println("Executando migration: " + file.getName());
                    StringBuilder sql = new StringBuilder();
                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            sql.append(line).append("\n");
                        }
                    } catch (java.io.IOException ioe) {
                        System.err.println("Erro ao ler o arquivo: " + file.getName() + ": " + ioe.getMessage());
                        continue;
                    }
                    for (String statement : sql.toString().split(";")) {
                        String trimmed = statement.trim();
                        if (!trimmed.isEmpty()) {
                            try (Statement stmt = conn.createStatement()) {
                                stmt.execute(trimmed);
                            } catch (java.sql.SQLException ex) {
                                String msg = ex.getMessage();
                                if (msg != null && (msg.toLowerCase().contains("already exists") || msg.contains("ORA-00955") || msg.contains("ORA-00942"))) {
                                    //System.out.println("Aviso: objeto já existe, ignorando: " + msg);
                                } else {
                                    System.err.println("Erro ao executar statement em " + file.getName() + ": " + msg);
                                }
                            }
                        }
                    }
                    System.out.println("Migration executada com sucesso: " + file.getName());
                }
            } catch (java.sql.SQLException e) {
                System.err.println("Erro ao executar migrations: " + e.getMessage());
                e.printStackTrace();
            }
        } finally {
            System.out.println("Processo de migrations concluído.");
        }
    }
}