package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://switchyard.proxy.rlwy.net:31810/railway"; // Remplace ici par ton URL exacte
    private static final String USER = "root";  // utilisateur (vérifie si c'est bien root ou autre)
    private static final String PASSWORD = "eICpmWwoOAPDdTIqkCGclypNTfbCtttY"; // Mets ici ton mot de passe Railway

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
