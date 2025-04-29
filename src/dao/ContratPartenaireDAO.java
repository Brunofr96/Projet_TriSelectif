package dao;

import model.ContratPartenaire;
import database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class ContratPartenaireDAO {

    public void enregistrerContrat(ContratPartenaire contrat) {
        String sql = "INSERT INTO ContratPartenaire (id_ContratPartenaire, dateDebut, dateFin, pointsNecessaires, renouvelable) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, contrat.getId());
            stmt.setString(2, new SimpleDateFormat("yyyy-MM-dd").format(contrat.getDateDebut()));
            stmt.setString(3, new SimpleDateFormat("yyyy-MM-dd").format(contrat.getDateFin()));
            stmt.setInt(4, contrat.getPointsNecessaires());
            stmt.setBoolean(5, contrat.isRenouvelable());

            stmt.executeUpdate();
            System.out.println("Contrat de partenariat enregistré avec succès !");
        } catch (SQLException e) {
            System.out.println(" Erreur lors de l'enregistrement du contrat : " + e.getMessage());
        }
    }
}
