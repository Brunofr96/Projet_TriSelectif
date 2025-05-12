package dao;

import model.OffreFidelite;
import database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
	

public class OffreFideliteDAO {

    public void enregistrerOffre(OffreFidelite offre) {
        String sql = "INSERT INTO OffreFidelite (id_OffreFidelite, description, cout, type, Id_Commerce) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, offre.getId());
            stmt.setString(2, offre.getDescription());
            stmt.setInt(3, offre.getCout());
            stmt.setString(4, offre.getType());
            stmt.setInt(5, offre.getIdCommerce());

            stmt.executeUpdate();
            System.out.println("Offre de fidélité enregistrée avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'enregistrement de l'offre : " + e.getMessage());
        }
    }
    
    public OffreFidelite getOffreParId(int id) {
        String sql = "SELECT * FROM OffreFidelite WHERE Id_OffreFidelite = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new OffreFidelite(
                    rs.getInt("Id_OffreFidelite"),
                    rs.getString("description"),
                    rs.getInt("cout"),
                    rs.getString("type"),
                    rs.getInt("Id_Commerce")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<OffreFidelite> getAllOffres() {
        List<OffreFidelite> offres = new ArrayList<>();
        String sql = "SELECT * FROM OffreFidelite";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                OffreFidelite offre = new OffreFidelite(
                    rs.getInt("Id_OffreFidelite"),
                    rs.getString("description"),
                    rs.getInt("cout"),
                    rs.getString("type"),
                    rs.getInt("Id_Commerce")
                );
                offres.add(offre);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return offres;
    }
}
