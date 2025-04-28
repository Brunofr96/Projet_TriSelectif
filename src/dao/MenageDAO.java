package dao;

import model.Menage;
import database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MenageDAO {

	public void enregistrerMenage(Menage menage) {
	    String sql = "INSERT INTO Menage (Id_Menage, nom, adresse, adresseMail, motDePasse, codeAcces, pointsFidelite) VALUES (?, ?, ?, ?, ?, ?, ?)";

	    try (Connection conn = DatabaseManager.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, menage.getId());
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

}
