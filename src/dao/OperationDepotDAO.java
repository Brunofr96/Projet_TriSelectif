package dao;

import model.OperationDepot;
import model.Dechet;
import database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class OperationDepotDAO {

    public void enregistrerDepot(OperationDepot depot) {
        String sql = "INSERT INTO OperationDepot (idDepot, dateDepot, quantite, pointsGagnes, idMenage, idBac) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, depot.getId());
            stmt.setString(2, depot.getDateDepot().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            stmt.setDouble(3, depot.getQuantite());
            stmt.setInt(4, depot.getPointsGagnes());
            stmt.setInt(5, depot.getMenage().getId());
            stmt.setInt(6, depot.getBac().getId());

            stmt.executeUpdate();
            System.out.println(" Dépôt enregistré avec succès !");
        } catch (SQLException e) {
            System.out.println(" Erreur lors de l'enregistrement du dépôt : " + e.getMessage());
        }
    }
}
