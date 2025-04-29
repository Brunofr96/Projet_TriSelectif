package dao;

import model.OffreFidelite;
import database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OffreFideliteDAO {

    public void enregistrerOffre(OffreFidelite offre) {
        String sql = "INSERT INTO OffreFidelite (id_OffreFidelite, description, cout, type) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, offre.getId());
            stmt.setString(2, offre.getDescription());
            stmt.setInt(3, offre.getCout());
            stmt.setString(4, offre.getType());

            stmt.executeUpdate();
            System.out.println("Offre de fidélité enregistrée avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'enregistrement de l'offre : " + e.getMessage());
        }
    }
}
