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


public class OperationDepotDAO {
	
	private int insererDechet(Connection conn, Dechet dechet) throws SQLException {
	    String sql = "INSERT INTO Dechet (poids, Id_TypeDechet) VALUES (?, ?)";

	    try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	        stmt.setDouble(1, dechet.getPoids());
	        stmt.setInt(2, dechet.getType().ordinal() + 1); // Attention : TypeDechet (Enum) commence à 0

	        stmt.executeUpdate();

	        ResultSet rs = stmt.getGeneratedKeys();
	        if (rs.next()) {
	            return rs.getInt(1); // retourne l'ID auto-incrémenté du déchet
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



	public void enregistrerDepot(OperationDepot depot) {
	    String sql = "INSERT INTO OperationDepot (Id_Dechet, Id_Menage, Id_BacIntelligent, dateDepot, quantite, pointsGagnes) VALUES (?, ?, ?, ?, ?, ?)";

	    try (Connection conn = DatabaseManager.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        // 🟢 1. Insérer le déchet d'abord
	        int idDechet = insererDechet(conn, depot.getDechets().get(0));

	        // 🟢 2. Ensuite insérer le dépôt avec l'ID du déchet
	        stmt.setInt(1, idDechet);
	        stmt.setInt(2, depot.getMenage().getId());
	        stmt.setInt(3, depot.getBac().getId());
	        stmt.setString(4, depot.getDateDepot().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
	        stmt.setDouble(5, depot.getQuantite());
	        stmt.setInt(6, depot.getPointsGagnes());

	        stmt.executeUpdate();
	        ajouterPointsFidelite(conn, depot.getMenage().getId(), depot.getPointsGagnes());

	        System.out.println("Dépôt enregistré avec succès !");
	        
	    } catch (SQLException e) {
	        System.out.println("Erreur lors de l'enregistrement du dépôt : " + e.getMessage());
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
            System.out.println("🔎 ID recherché : " + menage.getId());
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
                    0, // Pas d'ID unique utile ici
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



}
