package fintech.dao;

import fintech.Reminder; // Importa a classe de modelo
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ReminderDAO {

    // Carrega as credenciais do banco de dados usando a sua classe EnvLoader
    private static final String URL = fintech.utils.EnvLoader.get("DB_URL");
    private static final String USER = fintech.utils.EnvLoader.get("DB_USER");
    private static final String PASSWORD = fintech.utils.EnvLoader.get("DB_PASSWORD");

    /**
     * Insere um novo lembrete no banco de dados.
     * O ID gerado é recuperado e atribuído ao objeto.
     * @param reminder O objeto Reminder a ser inserido.
     */
    public void insert(Reminder reminder) {
        // SQL com os nomes exatos da sua tabela e colunas
        String sql = "INSERT INTO reminders (transaction_id, user_id, reminded_at, notified, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, reminder.getTransactionId());
            stmt.setInt(2, reminder.getUserId());
            // Converte java.util.Date para java.sql.Date
            stmt.setDate(3, new java.sql.Date(reminder.getRemindAt().getTime()));
            // Converte o boolean 'notified' para 'S' ou 'N'
            stmt.setString(4, reminder.isNotified() ? "S" : "N"); 
            stmt.setTimestamp(5, new Timestamp(reminder.getCreatedAt().getTime()));
            stmt.setTimestamp(6, new Timestamp(reminder.getUpdatedAt().getTime()));
            
            stmt.executeUpdate();

            // Recupera o ID gerado pelo banco
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    reminder.setId(generatedKeys.getInt(1)); 
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao inserir lembrete: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Busca todos os lembretes do banco de dados.
     * @return Uma lista de todos os objetos Reminder.
     */
    public List<Reminder> getAll() {
        List<Reminder> reminders = new ArrayList<>();
        String sql = "SELECT id, transaction_id, user_id, reminded_at, notified, created_at, updated_at FROM reminders ORDER BY id";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                reminders.add(mapReminder(rs));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao consultar lembretes: " + e.getMessage());
            e.printStackTrace();
        }
        return reminders;
    }

    /**
     * Busca um lembrete específico pelo seu ID.
     * @param id O ID do lembrete a ser buscado.
     * @return O objeto Reminder encontrado ou null se não existir.
     */
    public Reminder getById(int id) {
        String sql = "SELECT id, transaction_id, user_id, reminded_at, notified, created_at, updated_at FROM reminders WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapReminder(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar lembrete por id: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Método auxiliar para mapear uma linha do ResultSet para um objeto Reminder.
     * @param rs O ResultSet posicionado na linha a ser mapeada.
     * @return Um objeto Reminder preenchido.
     * @throws SQLException
     */
    private Reminder mapReminder(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int transactionId = rs.getInt("transaction_id");
        int userId = rs.getInt("user_id");
        // A coluna é DATE, então usamos getDate. O construtor de Reminder aceita Date.
        java.util.Date remindAt = rs.getDate("reminded_at");
        // Converte o CHAR 'S'/'N' para boolean
        boolean notified = "S".equalsIgnoreCase(rs.getString("notified")); 
        
        Timestamp createdAt = rs.getTimestamp("created_at");
        Timestamp updatedAt = rs.getTimestamp("updated_at");

        return new Reminder(id, transactionId, userId, remindAt, notified, createdAt, updatedAt);
    }
}
