package fintech.dao;

import fintech.Notification; // Importa a classe de modelo
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    // Carrega as credenciais do banco de dados usando a sua classe EnvLoader
    private static final String URL = fintech.utils.EnvLoader.get("DB_URL");
    private static final String USER = fintech.utils.EnvLoader.get("DB_USER");
    private static final String PASSWORD = fintech.utils.EnvLoader.get("DB_PASSWORD");

    /**
     * Insere uma nova notificação no banco de dados.
     * O ID gerado é recuperado e atribuído ao objeto.
     * @param notification O objeto Notification a ser inserido.
     */
    @SuppressWarnings("CallToPrintStackTrace")
    public void insert(Notification notification) {
        // SQL com os nomes exatos da sua tabela e colunas
        String sql = "INSERT INTO notifications (user_id, type, content, read, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, notification.getUserId());
            stmt.setShort(2, notification.getType());
            stmt.setString(3, notification.getContent());
            // Converte o boolean 'read' para 'S' ou 'N'
            stmt.setString(4, notification.isRead() ? "S" : "N"); 
            stmt.setTimestamp(5, new Timestamp(notification.getCreatedAt().getTime()));
            stmt.setTimestamp(6, new Timestamp(notification.getUpdatedAt().getTime()));
            
            stmt.executeUpdate();

            // Recupera o ID gerado pelo banco (GENERATED ALWAYS AS IDENTITY)
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    notification.setId(generatedKeys.getInt(1)); 
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao inserir notificação: " + e.getMessage());
            // Para depuração, é útil imprimir o stack trace
            e.printStackTrace();
        }
    }

    /**
     * Busca todas as notificações do banco de dados.
     * @return Uma lista de todos os objetos Notification.
     */
    public List<Notification> getAll() {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT id, user_id, type, content, read, created_at, updated_at FROM notifications ORDER BY id";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                notifications.add(mapNotification(rs));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao consultar notificações: " + e.getMessage());
            e.printStackTrace();
        }
        return notifications;
    }

    /**
     * Busca uma notificação específica pelo seu ID.
     * @param id O ID da notificação a ser buscada.
     * @return O objeto Notification encontrado ou null se não existir.
     */
    public Notification getById(int id) {
        String sql = "SELECT id, user_id, type, content, read, created_at, updated_at FROM notifications WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapNotification(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar notificação por id: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
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
        // Converte o CHAR 'S'/'N' para boolean
        boolean read = "S".equalsIgnoreCase(rs.getString("read")); 
        
        Timestamp createdAt = rs.getTimestamp("created_at");
        Timestamp updatedAt = rs.getTimestamp("updated_at");

        return new Notification(id, userId, type, content, read, createdAt, updatedAt);
    }

    private static class reparedStatement {

        public reparedStatement() {
        }
    }
}
