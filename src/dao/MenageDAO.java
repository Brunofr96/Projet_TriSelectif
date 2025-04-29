package dao;

import model.Menage;
import database.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenageDAO {

    // ✅ Enregistrement de base (déjà fait)
    public void enregistrerMenage(Menage menage) {
        String sql = "INSERT INTO Menage (nom, adresse, adresseMail, motDePasse, codeAcces, pointsFidelite) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(2, menage.getNom());
            stmt.setString(3, menage.getAdresse());
            stmt.setString(4, menage.getAdresseMail());
            stmt.setString(5, menage.getMotDePasse());
            stmt.setInt(6, menage.getCodeAcces());
            stmt.setInt(7, menage.getPointsFidelite());

            stmt.executeUpdate();
            System.out.println("✅ Ménage enregistré avec succès !");
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'enregistrement : " + e.getMessage());
        }
    }

    // 🔍 Récupérer un ménage par son ID
    public Menage getMenageById(int id) {
        String sql = "SELECT * FROM Menage WHERE Id_Menage = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Menage(
                    rs.getInt("Id_Menage"),
                    rs.getString("nom"),
                    rs.getString("adresse"),
                    rs.getString("adresseMail"),
                    rs.getString("motDePasse"),
                    rs.getInt("codeAcces"),
                    rs.getInt("pointsFidelite"),
                    null // historique à charger à part si besoin
                );
            }

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération : " + e.getMessage());
        }
        return null;
    }


    // 📋 Récupérer tous les ménages
    public List<Menage> getAllMenages() {
        List<Menage> menages = new ArrayList<>();
        String sql = "SELECT * FROM Menage";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                menages.add(new Menage(
                    rs.getInt("Id_Menage"),
                    rs.getString("nom"),
                    rs.getString("adresse"),
                    rs.getString("adresseMail"),
                    rs.getString("motDePasse"),
                    rs.getInt("codeAcces"),
                    rs.getInt("pointsFidelite"),
                    null
                ));
            }

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération des ménages : " + e.getMessage());
        }

        return menages;
    }

    // ❌ Supprimer un ménage
    public void deleteMenage(int id) {
        String sql = "DELETE FROM Menage WHERE Id_Menage = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int result = stmt.executeUpdate();
            if (result > 0) {
                System.out.println("✅ Ménage supprimé avec succès !");
            } else {
                System.out.println("ℹ️ Aucun ménage trouvé avec l’ID " + id);
            }

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la suppression : " + e.getMessage());
        }
    }
}
