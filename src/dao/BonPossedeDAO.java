package dao;

import model.BonPossede;
import model.Menage;
import model.OffreFidelite;
import database.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BonPossedeDAO {

    // ➤ Ajoute un bon ou incrémente la quantité si déjà existant
    public void ajouterOuIncrementerBon(Menage menage, OffreFidelite offre) {
        String select = "SELECT * FROM BonsPossedes WHERE id_menage = ? AND id_offre = ?";
        String insert = "INSERT INTO BonsPossedes (id_menage, id_offre, quantite) VALUES (?, ?, 1)";
        String update = "UPDATE BonsPossedes SET quantite = quantite + 1 WHERE id_menage = ? AND id_offre = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmtSelect = conn.prepareStatement(select)) {

            stmtSelect.setInt(1, menage.getId());
            stmtSelect.setInt(2, offre.getId());

            ResultSet rs = stmtSelect.executeQuery();

            if (rs.next()) {
                try (PreparedStatement stmtUpdate = conn.prepareStatement(update)) {
                    stmtUpdate.setInt(1, menage.getId());
                    stmtUpdate.setInt(2, offre.getId());
                    stmtUpdate.executeUpdate();
                }
            } else {
                try (PreparedStatement stmtInsert = conn.prepareStatement(insert)) {
                    stmtInsert.setInt(1, menage.getId());
                    stmtInsert.setInt(2, offre.getId());
                    stmtInsert.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ➤ Récupère les bons possédés par un ménage
    public List<BonPossede> getBonsParMenage(int idMenage) {
        List<BonPossede> bons = new ArrayList<>();
        String query = "SELECT * FROM BonsPossedes WHERE id_menage = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idMenage);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                BonPossede bon = new BonPossede(
                    rs.getInt("id_menage"),
                    rs.getInt("id_offre"),
                    rs.getInt("quantite")
                );
                bons.add(bon);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bons;
    }
}
