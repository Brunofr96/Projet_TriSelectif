package dao;

import model.OperationDepot;
import database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import model.BacIntelligent;
import model.Dechet;
import model.Menage;
import model.TypePoubelle;
import model.TypeDechet;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;
import java.util.Map;
import java.util.HashMap;


public class OperationDepotDAO {

    private int insererDechet(Connection conn, Dechet dechet) throws SQLException {
        String sql = "INSERT INTO Dechet (poids, Id_TypeDechet) VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDouble(1, dechet.getPoids());
            stmt.setInt(2, dechet.getType().ordinal() + 1);

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("Erreur lors de la récupération de l'ID du déchet inséré.");
            }
        }
    }

    private void ajouterPointsFidelite(Connection conn, int idMenage, int pointsGagnes) throws SQLException {
        String sql = "UPDATE Menage SET pointsFidelite = pointsFidelite + ? WHERE Id_Menage = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pointsGagnes);
            stmt.setInt(2, idMenage);
            stmt.executeUpdate();
        }
    }

    private double getPoidsTotal(Connection conn, int idBac) throws SQLException {
        String sql = "SELECT poidsActuel FROM BacIntelligent WHERE Id_BacIntelligent = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idBac);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getDouble("poidsActuel") : 0;
        }
    }

    private boolean peutAccepterDepot(Connection conn, int idBac, double quantiteDepot) throws SQLException {
        String sql = "SELECT capaciteMax, poidsActuel FROM BacIntelligent WHERE Id_BacIntelligent = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idBac);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                double capaciteMax = rs.getDouble("capaciteMax");
                double poidsActuel = rs.getDouble("poidsActuel");
                return (poidsActuel + quantiteDepot) <= capaciteMax;
            } else {
                throw new SQLException("Bac non trouvé.");
            }
        }
    }

    public boolean enregistrerDepot(OperationDepot depot) {
        String sqlDepot = "INSERT INTO OperationDepot (Id_Dechet, Id_Menage, Id_BacIntelligent, dateDepot, quantite, pointsGagnes) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlMajBac = "UPDATE BacIntelligent SET poidsActuel = poidsActuel + ?, estPleine = ? WHERE Id_BacIntelligent = ?";

        try (Connection conn = DatabaseManager.getConnection()) {
            if (!peutAccepterDepot(conn, depot.getBac().getId(), depot.getQuantite())) {
                System.out.println("❌ Dépôt refusé : capacité maximale dépassée.");
                return false;
            }

            int idDechet = insererDechet(conn, depot.getDechets().get(0));

            try (PreparedStatement stmtDepot = conn.prepareStatement(sqlDepot)) {
                stmtDepot.setInt(1, idDechet);
                stmtDepot.setInt(2, depot.getMenage().getId());
                stmtDepot.setInt(3, depot.getBac().getId());
                stmtDepot.setString(4, depot.getDateDepot().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                stmtDepot.setDouble(5, depot.getQuantite());
                stmtDepot.setInt(6, depot.getPointsGagnes());
                stmtDepot.executeUpdate();
            }

            double poidsTotal = getPoidsTotal(conn, depot.getBac().getId()) + depot.getQuantite();
            boolean estPleine = poidsTotal >= depot.getBac().getCapaciteMax();

            try (PreparedStatement stmtMaj = conn.prepareStatement(sqlMajBac)) {
                stmtMaj.setDouble(1, depot.getQuantite());
                stmtMaj.setBoolean(2, estPleine);
                stmtMaj.setInt(3, depot.getBac().getId());
                stmtMaj.executeUpdate();
            }

            ajouterPointsFidelite(conn, depot.getMenage().getId(), depot.getPointsGagnes());

            System.out.println("Dépôt enregistré avec succès !");
            return true;

        } catch (SQLException e) {
            System.out.println("Erreur lors du dépôt : " + e.getMessage());
            return false;
        }
    }

    public List<OperationDepot> getDepotsParMenage(Menage menage) {
        List<OperationDepot> depots = new ArrayList<>();

        String sql = "SELECT od.dateDepot, od.quantite, od.pointsGagnes, " +
                     "b.Id_BacIntelligent, b.capaciteMax, b.emplacement, tp.libelle AS typePoubelle, " +
                     "d.poids, td.libelle AS typeDechet " +
                     "FROM OperationDepot od " +
                     "JOIN BacIntelligent b ON od.Id_BacIntelligent = b.Id_BacIntelligent " +
                     "JOIN TypePoubelle tp ON b.Id_TypePoubelle = tp.Id_TypePoubelle " +
                     "JOIN Dechet d ON od.Id_Dechet = d.Id_Dechet " +
                     "JOIN TypeDechet td ON d.Id_TypeDechet = td.Id_TypeDechet " +
                     "WHERE od.Id_Menage = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, menage.getId());
            System.out.println("ID recherché : " + menage.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                BacIntelligent bac = new BacIntelligent(
                    rs.getInt("Id_BacIntelligent"),
                    TypePoubelle.valueOf(rs.getString("typePoubelle").toUpperCase()),
                    rs.getDouble("capaciteMax"),
                    rs.getString("emplacement")
                );

                Dechet dechet = new Dechet(
                    TypeDechet.valueOf(rs.getString("typeDechet").toUpperCase()),
                    rs.getDouble("poids")
                );

                List<Dechet> listeDechets = new ArrayList<>();
                listeDechets.add(dechet);

                OperationDepot depot = new OperationDepot(
                    0,
                    menage,
                    bac,
                    listeDechets,
                    rs.getInt("pointsGagnes")
                );

                depot.setDateDepot(rs.getTimestamp("dateDepot").toLocalDateTime());
                depots.add(depot);
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des dépôts : " + e.getMessage());
        }

        return depots;
    }
    


 // 📊 Statistiques : total des déchets groupés par type de poubelle
    public Map<TypePoubelle, Double> getStatistiquesParTypePoubelle() {
        Map<TypePoubelle, Double> stats = new HashMap<>();

        String sql = "SELECT tp.libelle AS typePoubelle, SUM(od.quantite) AS total " +
                     "FROM OperationDepot od " +
                     "JOIN BacIntelligent b ON od.Id_BacIntelligent = b.Id_BacIntelligent " +
                     "JOIN TypePoubelle tp ON b.Id_TypePoubelle = tp.Id_TypePoubelle " +
                     "GROUP BY tp.libelle";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String typeLibelle = rs.getString("typePoubelle").toUpperCase();
                double total = rs.getDouble("total");

                try {
                    TypePoubelle type = TypePoubelle.valueOf(typeLibelle);
                    stats.put(type, total);
                } catch (IllegalArgumentException e) {
                    System.out.println("⚠️ Type de poubelle inconnu ignoré : " + typeLibelle);
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération des stats : " + e.getMessage());
        }

        return stats;
    }
    
    
    public Map<TypeDechet, Double> getStatistiquesParTypeDechet() {
        Map<TypeDechet, Double> stats = new HashMap<>();

        String sql = "SELECT td.libelle AS typeDechet, SUM(od.quantite) AS total " +
                     "FROM OperationDepot od " +
                     "JOIN Dechet d ON od.Id_Dechet = d.Id_Dechet " +
                     "JOIN TypeDechet td ON d.Id_TypeDechet = td.Id_TypeDechet " +
                     "GROUP BY td.libelle";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String typeLibelle = rs.getString("typeDechet").toUpperCase();
                double total = rs.getDouble("total");

                stats.put(TypeDechet.valueOf(typeLibelle), total);
            }

        } catch (SQLException e) {
            System.out.println("❌ Erreur récupération stats par type de déchet : " + e.getMessage());
        }

        return stats;
    }



}
