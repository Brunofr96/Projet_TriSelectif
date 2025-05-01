package dao;

import database.DatabaseManager;
import model.BacIntelligent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BacIntelligentDAO {

    public void enregistrerBac(BacIntelligent bac) {
        String sql = "INSERT INTO BacIntelligent " +
                     "(Id_BacIntelligent, capaciteMax, emplacement, estPleine, Id_CentreDeTri, Id_TypePoubelle) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bac.getId()); // Id_BacIntelligent
            stmt.setDouble(2, bac.getCapaciteMax());
            stmt.setString(3, bac.getEmplacement());
            stmt.setBoolean(4, bac.isEstPleine());
            stmt.setInt(5, bac.getIdCentreDeTri()); // FK centre de tri
            stmt.setInt(6, bac.getType().getId());

            stmt.executeUpdate();
            System.out.println("✅ Bac intelligent enregistré avec succès !");
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'enregistrement du bac : " + e.getMessage());
        }
    }
}
