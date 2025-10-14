package fintech.dao;

import fintech.models.Notification;
import factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    /**
     * Insere uma nova notificação no banco de dados.
     * O ID gerado é recuperado e atribuído ao objeto.
     * @param notification O objeto Notification a ser inserido.
     */
    public void insert(Notification notification) throws utils.Exception {
        String sql = "INSERT INTO notifications (user_id, type, content, read, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, new String[] { "ID" })) {

            stmt.setInt(1, notification.getUserId());
            stmt.setShort(2, notification.getType());
            stmt.setString(3, notification.getContent());
            stmt.setString(4, notification.isRead() ? "S" : "N"); 
            stmt.setTimestamp(5, new Timestamp(notification.getCreatedAt().getTime()));
            stmt.setTimestamp(6, new Timestamp(notification.getUpdatedAt().getTime()));
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new utils.Exception("Falha ao inserir a notificação, nenhum registro afetado.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    notification.setId(generatedKeys.getInt(1)); 
                } else {
                    throw new utils.Exception("Falha ao recuperar o ID da notificação inserida.");
                }
            }

        } catch (SQLException e) {
            throw new utils.Exception("Erro ao inserir notificação: " + e.getMessage());
        }
    }

    /**
     * Busca todas as notificações do banco de dados.
     * @return Uma lista de todos os objetos Notification.
     */
    public List<Notification> getAll() throws utils.Exception {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT id, user_id, type, content, read, created_at, updated_at FROM notifications ORDER BY id";

        try (Connection conn = factory.ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                notifications.add(mapNotification(rs));
            }

        } catch (SQLException e) {
            throw new utils.Exception("Erro ao consultar notificações: " + e.getMessage());
        }
        return notifications;
    }

    /**
     * Busca uma notificação específica pelo seu ID.
     * @param id O ID da notificação a ser buscada.
     * @return O objeto Notification encontrado ou lança Exception se não existir.
     */
    public Notification getById(int id) throws utils.Exception {
        String sql = "SELECT id, user_id, type, content, read, created_at, updated_at FROM notifications WHERE id = ?";

        try (Connection conn = factory.ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapNotification(rs);
                } else {
                    throw new utils.Exception("Notificação não encontrada para o id: " + id);
                }
            }

        } catch (SQLException e) {
            throw new utils.Exception("Erro ao buscar notificação por id: " + e.getMessage());
        }
    }

    /**
     * Método auxiliar para mapear uma linha do ResultSet para um objeto Notification.
     * @param rs O ResultSet posicionado na linha a ser mapeada.
     * @return Um objeto Notification preenchido.
     * @throws SQLException
     */
    private Notification mapNotification(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int userId = rs.getInt("user_id");
        short type = rs.getShort("type");
        String content = rs.getString("content");
        boolean read = "S".equalsIgnoreCase(rs.getString("read")); 
        Timestamp createdAt = rs.getTimestamp("created_at");
        Timestamp updatedAt = rs.getTimestamp("updated_at");

        return new Notification(id, userId, type, content, read, createdAt, updatedAt);
    }

    public List<Notification> getByUserId(int userId) throws utils.Exception {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT id, user_id, type, content, read, created_at, updated_at FROM notifications WHERE user_id = ? ORDER BY id";

        try (Connection conn = factory.ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    notifications.add(mapNotification(rs));
                }
            }

        } catch (SQLException e) {
            throw new utils.Exception("Erro ao buscar notificações por user_id: " + e.getMessage());
        }
        return notifications;
    }
}