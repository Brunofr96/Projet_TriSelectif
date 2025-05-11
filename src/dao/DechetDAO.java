package dao;

import database.DatabaseManager;
import model.Dechet;
import model.TypeDechet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DechetDAO {

    // ✅ Insérer un déchet dans la base et récupérer l'ID généré
    public void enregistrerDechet(Dechet dechet) {
        String sql = "INSERT INTO Dechet (Id_TypeDechet, poids) VALUES (?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, dechet.getType().getId());
            stmt.setDouble(2, dechet.getPoids());

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    dechet.setId(rs.getInt(1));
                    System.out.println(" Déchet enregistré avec ID : " + dechet.getId());
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'enregistrement du déchet : " + e.getMessage());
        }
    }

    // ✅ Récupérer tous les déchets
    public List<Dechet> getAllDechets() {
        List<Dechet> dechets = new ArrayList<>();
        String sql = "SELECT * FROM Dechet";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("Id_Dechet");
                double poids = rs.getDouble("poids");
                int typeId = rs.getInt("Id_TypeDechet");

                TypeDechet type = TypeDechet.fromId(typeId);
                dechets.add(new Dechet(id, type, poids));
            }

        } catch (SQLException e) {
            System.out.println(" Erreur lors de la récupération des déchets : " + e.getMessage());
        }

        return dechets;
    }

    // ✅ Récupérer les déchets filtrés par type
    public List<Dechet> getDechetsParType(TypeDechet typeDechet) {
        List<Dechet> dechets = new ArrayList<>();
        String sql = "SELECT * FROM Dechet WHERE Id_TypeDechet = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, typeDechet.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Id_Dechet");
                double poids = rs.getDouble("poids");
                dechets.add(new Dechet(id, typeDechet, poids));
            }

        } catch (SQLException e) {
            System.out.println(" Erreur lors de la récupération des déchets par type : " + e.getMessage());
        }

        return dechets;
    }

    // ✅ Supprimer un déchet
    public void supprimerDechet(int id) {
        String sql = "DELETE FROM Dechet WHERE Id_Dechet = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            System.out.println("✅ Déchet supprimé : " + rows + " ligne(s)");

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la suppression du déchet : " + e.getMessage());
        }
    }
}
