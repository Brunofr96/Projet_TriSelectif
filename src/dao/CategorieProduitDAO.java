package dao;

import model.CategorieProduit;
import database.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategorieProduitDAO {

    // ➤ Insère une nouvelle catégorie de produit liée à un contrat
    public void enregistrerCategorie(CategorieProduit categorie) {
        String sql = "INSERT INTO CategorieProduit (libelle, id_contrat) VALUES (?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, categorie.getLibelle());
            stmt.setInt(2, categorie.getIdContrat());

            stmt.executeUpdate();
            System.out.println("✅ Catégorie enregistrée avec succès !");
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'enregistrement de la catégorie : " + e.getMessage());
        }
    }

    // ➤ Récupère toutes les catégories associées à un contrat
    public List<CategorieProduit> getCategoriesParContrat(int idContrat) {
        List<CategorieProduit> categories = new ArrayList<>();
        String sql = "SELECT * FROM CategorieProduit WHERE id_contrat = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idContrat);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                categories.add(new CategorieProduit(
                    rs.getInt("id"), 
                    rs.getString("libelle"), 
                    rs.getInt("id_contrat")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }
}
