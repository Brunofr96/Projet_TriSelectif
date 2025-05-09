package dao;

import database.DatabaseManager;
import model.BacIntelligent;
import model.TypePoubelle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BacIntelligentDAO {

    // 🔁 Méthode 1 : Enregistrer un bac dans la base
    public void enregistrerBac(BacIntelligent bac) {
        String sql = "INSERT INTO BacIntelligent " +
                     "(Id_BacIntelligent, capaciteMax, emplacement, estPleine, Id_CentreDeTri, Id_TypePoubelle) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bac.getId());
            stmt.setDouble(2, bac.getCapaciteMax());
            stmt.setString(3, bac.getEmplacement());
            stmt.setBoolean(4, bac.isEstPleine());
            stmt.setInt(5, bac.getIdCentreDeTri());
            stmt.setInt(6, bac.getIdTypePoubelle());

            stmt.executeUpdate();
            System.out.println("Bac intelligent enregistré avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'enregistrement du bac : " + e.getMessage());
        }
    }

    public List<BacIntelligent> getAllBacs() {
        List<BacIntelligent> bacs = new ArrayList<>();
        String sql = "SELECT * FROM BacIntelligent";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("Id_BacIntelligent");
                int idTypePoubelle = rs.getInt("Id_TypePoubelle");
                double capacite = rs.getDouble("capaciteMax");
                String emplacement = rs.getString("emplacement");

                TypePoubelle type = TypePoubelle.fromId(idTypePoubelle);

                BacIntelligent bac = new BacIntelligent(id, type, capacite, emplacement);
                bac.setIdCentreDeTri(rs.getInt("Id_CentreDeTri"));
                bac.setPoidsActuel(rs.getDouble("poidsActuel"));
                bac.setEstPleine(rs.getBoolean("estPleine"));

                bacs.add(bac);
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des bacs : " + e.getMessage());
        }

        return bacs;
    }

    // Mettre à jour les propriétés d’un bac (ex : plein, capacité)
    public void mettreAJourEtat(BacIntelligent bac) {
        String sql = "UPDATE BacIntelligent SET estPleine = ?, capaciteMax = ? WHERE Id_BacIntelligent = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, bac.isEstPleine());
            stmt.setDouble(2, bac.getCapaciteMax());
            stmt.setInt(3, bac.getId());

            stmt.executeUpdate();
            System.out.println("État du bac mis à jour.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du bac : " + e.getMessage());
        }
    }

    // Supprimer un bac
    public void supprimerBac(int id) {
        String sql = "DELETE FROM BacIntelligent WHERE Id_BacIntelligent = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Bac supprimé avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du bac : " + e.getMessage());
        }
    }

    public void collecterTousLesBacs() {
        String sql = "UPDATE BacIntelligent SET estPleine = false, poidsActuel = 0";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.executeUpdate();
            System.out.println("Tous les bacs ont été collectés (vidés + statut mis à jour) !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la collecte des bacs : " + e.getMessage());
        }
    }


}
