package fintech.dao;

import fintech.models.Reminder;
import factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ReminderDAO {

    /**
     * Insere um novo lembrete no banco de dados.
     * O ID gerado é recuperado e atribuído ao objeto.
     * @param reminder O objeto Reminder a ser inserido.
     */
    public void insert(Reminder reminder) throws utils.Exception {
        String sql = "INSERT INTO reminders (transaction_id, user_id, reminded_at, notified, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, new String[] { "ID" })) {

            stmt.setInt(1, reminder.getTransactionId());
            stmt.setInt(2, reminder.getUserId());
            stmt.setTimestamp(3, new Timestamp(reminder.getRemindAt().getTime()));
            stmt.setString(4, reminder.isNotified());
            stmt.setTimestamp(5, new Timestamp(reminder.getCreatedAt().getTime()));
            stmt.setTimestamp(6, new Timestamp(reminder.getUpdatedAt().getTime()));
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new utils.Exception("Falha ao inserir o lembrete, nenhum registro afetado.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    reminder.setId(generatedKeys.getInt(1)); 
                } else {
                    throw new utils.Exception("Falha ao recuperar o ID do lembrete inserido.");
                }
            }

        } catch (SQLException e) {
            throw new utils.Exception("Erro ao inserir lembrete: " + e.getMessage());
        }
    }

    /**
     * Busca todos os lembretes do banco de dados.
     * @return Uma lista de todos os objetos Reminder.
     */
    public List<Reminder> getAll() throws utils.Exception {
        List<Reminder> reminders = new ArrayList<>();
        String sql = "SELECT id, transaction_id, user_id, reminded_at, notified, created_at, updated_at FROM reminders ORDER BY id";

        try (Connection conn = factory.ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                reminders.add(mapReminder(rs));
            }

        } catch (SQLException e) {
            throw new utils.Exception("Erro ao consultar lembretes: " + e.getMessage());
        }
        return reminders;
    }

    /**
     * Busca um lembrete específico pelo seu ID.
     * @param id O ID do lembrete a ser buscado.
     * @return O objeto Reminder encontrado ou lança Exception se não existir.
     */
    public Reminder getById(int id) throws utils.Exception {
        String sql = "SELECT id, transaction_id, user_id, reminded_at, notified, created_at, updated_at FROM reminders WHERE id = ?";

        try (Connection conn = factory.ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapReminder(rs);
                } else {
                    throw new utils.Exception("Lembrete não encontrado para o id: " + id);
                }
            }

        } catch (SQLException e) {
            throw new utils.Exception("Erro ao buscar lembrete por id: " + e.getMessage());
        }
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
        Timestamp remindAt = rs.getTimestamp("reminded_at");
        String notified = rs.getString("notified");   
        Timestamp createdAt = rs.getTimestamp("created_at");
        Timestamp updatedAt = rs.getTimestamp("updated_at");

        return new Reminder(id, transactionId, userId, remindAt, notified, createdAt, updatedAt);
    }

    public List<Reminder> getByUserId(int userId) throws utils.Exception {

        List<Reminder> reminders = new ArrayList<>();
        String sql = "SELECT id, transaction_id, user_id, reminded_at, notified, created_at, updated_at FROM reminders WHERE user_id = ? ORDER BY id";

        try (Connection conn = factory.ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reminders.add(mapReminder(rs));
                }
            }

        } catch (SQLException e) {
            throw new utils.Exception("Erro ao buscar lembretes por user_id: " + e.getMessage());
        }
        return reminders;
    }
}