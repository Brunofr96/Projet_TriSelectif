package dao;

import model.Commerce;
import database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CommerceDAO {

    public void enregistrerCommerce(Commerce commerce) {
        String sql = "INSERT INTO Commerce (id_Commerce, nom) VALUES (?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, commerce.getId());
            stmt.setString(2, commerce.getNom());

            stmt.executeUpdate();
            System.out.println("Commerce enregistré avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'enregistrement du commerce : " + e.getMessage());
        }
    }
}
