package controller;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Menage;
import javafx.scene.control.PasswordField;


import java.io.IOException;
import java.sql.*;

public class ConnexionController {
	
	@FXML
	private TextField mailAcess;

	@FXML
	private PasswordField mdpAcess;

    @FXML
    private Button connexionButton;

    @FXML
    private Button inscriptionButton;
    
    @FXML
    private Button centreDeTriButton;

    @FXML
    private Label messageLabel;

    @FXML
    private void handleConnexion() {
        String mail = mailAcess.getText().trim();
        String motDePasse = mdpAcess.getText().trim();

        Menage menage = getMenageDepuisMailEtMdp(mail, motDePasse); // 

        if (menage != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ihm/Accueil.fxml"));
                Parent accueilRoot = loader.load();

                AccueilController controller = loader.getController();
                controller.setMenage(menage);

                Stage stage = (Stage) connexionButton.getScene().getWindow();
                stage.setScene(new Scene(accueilRoot));
                stage.setTitle("Accueil");
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
                messageLabel.setText("Erreur lors du chargement de la page.");
            }
        } else {
            messageLabel.setText("Email ou mot de passe incorrect !");
        }
    }

    private Menage getMenageDepuisMailEtMdp(String mail, String motDePasse) {
        String url = "jdbc:mysql://switchyard.proxy.rlwy.net:31810/railway";
        String user = "root";
        String password = "eICpmWwoOAPDdTIqkCGclypNTfbCtttY";

        String query = "SELECT * FROM Menage WHERE adresseMail = ? AND motDePasse = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, mail);
            stmt.setString(2, motDePasse);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Menage(
                	rs.getInt("Id_Menage"),
                    rs.getString("nom"),
                    rs.getString("adresse"),
                    rs.getString("adresseMail"),
                    rs.getString("motDePasse"),
                    rs.getInt("CodeAcces"),
                    rs.getInt("pointsFidelite"),
                    null
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
            messageLabel.setText("Erreur base de données !");
        }

        return null;
    }

    
    @FXML
    private void handleInscription() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ihm/Inscription.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) inscriptionButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Inscription");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Erreur lors du chargement de l'inscription.");
        }
    }
    
    @FXML
    private void ouvrirCentreDeTri() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ihm/CentreDeTri.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Centre de Tri - Gestion des Bacs");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Erreur lors de l'ouverture du Centre de Tri.");
        }
    }


}
