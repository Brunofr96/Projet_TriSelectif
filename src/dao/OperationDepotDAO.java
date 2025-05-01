package dao;

import model.OperationDepot;
import database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class OperationDepotDAO {

    public void enregistrerDepot(OperationDepot depot) {
        String sql = "INSERT INTO OperationDepot (dateDepot, quantite, pointsGagnes, Id_Menage, Id_BacIntelligent) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // 🟢 Respect de l’ordre dans le INSERT
            stmt.setString(1, depot.getDateDepot().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            stmt.setDouble(2, depot.getQuantite());
            stmt.setInt(3, depot.getPointsGagnes());
            stmt.setInt(4, depot.getMenage().getId());
            stmt.setInt(5, depot.getBac().getId());

            stmt.executeUpdate();
            System.out.println("✅ Dépôt enregistré avec succès !");
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'enregistrement du dépôt : " + e.getMessage());
        }
    }
}
