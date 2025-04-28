package test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnexion {
    public static void main(String[] args) {
        // Informations de ta base Railway
        String url = "jdbc:mysql://switchyard.proxy.rlwy.net:31810/railway"; // ← Ton host public Railway + port + database
        String user = "root"; // ← utilisateur
        String password = "eICpmWwoOAPDdTIqkCGclypNTfbCtttY"; // ← ton mot de passe Railway

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion à MySQL réussie !");
            connection.close();
        } catch (SQLException e) {
            System.out.println("Erreur de connexion !");
            e.printStackTrace();
        }
    }
}
