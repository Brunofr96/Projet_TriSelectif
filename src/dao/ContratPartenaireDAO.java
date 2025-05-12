package dao;

import model.ContratPartenaire;
import model.CategorieProduit;
import dao.CategorieProduitDAO;
import database.DatabaseManager;

import java.sql.ResultSet;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContratPartenaireDAO {

    public void enregistrerContrat(ContratPartenaire contrat) {
        String sql = "INSERT INTO ContratPartenaire (id_ContratPartenaire, dateDebut, dateFin, pointsNecessaires, renouvelable, Id_Commerce, Id_CentreDeTri) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, contrat.getId());
            stmt.setString(2, new SimpleDateFormat("yyyy-MM-dd").format(contrat.getDateDebut()));
            stmt.setString(3, new SimpleDateFormat("yyyy-MM-dd").format(contrat.getDateFin()));
            stmt.setInt(4, contrat.getPointsNecessaires());
            stmt.setBoolean(5, contrat.isRenouvelable());
            stmt.setInt(6, contrat.getIdCommerce());
            stmt.setInt(7, contrat.getIdCentreDeTri());

            stmt.executeUpdate();
            System.out.println("Contrat de partenariat enregistré avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'enregistrement du contrat : " + e.getMessage());
        }
    }

    public ContratPartenaire getContratParCommerce(int idCommerce) {
        String sql = "SELECT * FROM ContratPartenaire WHERE Id_Commerce = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCommerce);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Date dateDebut = rs.getDate("dateDebut");
                Date dateFin = rs.getDate("dateFin");
                int pointsNecessaires = rs.getInt("pointsNecessaires");
                boolean renouvelable = rs.getBoolean("renouvelable");
                int idContrat = rs.getInt("id_ContratPartenaire");
                int idCentre = rs.getInt("Id_CentreDeTri");

                // 🔄 Charger les catégories
                CategorieProduitDAO catDAO = new CategorieProduitDAO();
                List<CategorieProduit> categories = catDAO.getCategoriesParContrat(idContrat);
                List<String> nomsCategories = new ArrayList<>();
                for (CategorieProduit c : categories) {
                    nomsCategories.add(c.getLibelle());
                }

                return new ContratPartenaire(
                    idContrat,
                    dateDebut,
                    dateFin,
                    nomsCategories,
                    pointsNecessaires,
                    renouvelable,
                    idCommerce,
                    idCentre
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<ContratPartenaire> getAllContrats() {
        List<ContratPartenaire> contrats = new ArrayList<>();
        String sql = "SELECT * FROM ContratPartenaire";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idContrat = rs.getInt("id_ContratPartenaire");
                Date dateDebut = rs.getDate("dateDebut");
                Date dateFin = rs.getDate("dateFin");
                int pointsNecessaires = rs.getInt("pointsNecessaires");
                boolean renouvelable = rs.getBoolean("renouvelable");
                int idCommerce = rs.getInt("Id_Commerce");
                int idCentre = rs.getInt("Id_CentreDeTri");

                // 🔄 Charger les catégories
                CategorieProduitDAO catDAO = new CategorieProduitDAO();
                List<CategorieProduit> categories = catDAO.getCategoriesParContrat(idContrat);
                List<String> nomsCategories = new ArrayList<>();
                for (CategorieProduit c : categories) {
                    nomsCategories.add(c.getLibelle());
                }

                ContratPartenaire contrat = new ContratPartenaire(
                    idContrat, dateDebut, dateFin,
                    nomsCategories,
                    pointsNecessaires, renouvelable,
                    idCommerce, idCentre
                );

                contrats.add(contrat);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contrats;
    }
}
