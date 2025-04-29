package dao;

import model.BacIntelligent;
import database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BacIntelligentDAO {

    public void enregistrerBac(BacIntelligent bac) {
        String sql = "INSERT INTO BacIntelligent (id_BacIntelligent, type, capaciteMax, emplacement) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bac.getId());
            stmt.setString(2, bac.getType().toString()); // On enregistre le type comme String
            stmt.setDouble(3, bac.getCapaciteMax());
            stmt.setString(4, bac.getEmplacement());

            stmt.executeUpdate();
            System.out.println("Bac intelligent enregistré avec succès !");
        } catch (SQLException e) {
            System.out.println(" Erreur lors de l'enregistrement du bac : " + e.getMessage());
        }
    }
}
