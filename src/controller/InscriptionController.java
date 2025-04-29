package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Menage;

import java.io.IOException;
import java.sql.*;

public class InscriptionController {

    @FXML
    private TextField nomField;

    @FXML
    private TextField adresseField;

    @FXML
    private TextField mailField;

    @FXML
    private PasswordField motDePasseField;

    @FXML
    private TextField codeAccesField;

    @FXML
    private Button validerButton;

    @FXML
    private Button retourButton;

    @FXML
    private Label messageLabel;
    

    @FXML
    private void handleInscription() {
        String nom = nomField.getText().trim();
        String adresse = adresseField.getText().trim();
        String mail = mailField.getText().trim();
        String motDePasse = motDePasseField.getText().trim();
        String codeAccesTexte = codeAccesField.getText().trim();

        if (nom.isEmpty() || adresse.isEmpty() || mail.isEmpty() || motDePasse.isEmpty() || codeAccesTexte.isEmpty()) {
            messageLabel.setText("Veuillez remplir tous les champs.");
            return;
        }

        try {
            int codeAcces = Integer.parseInt(codeAccesTexte);

            // Création d'un Menage
            Menage menage = new Menage(nom, adresse, mail, motDePasse, codeAcces, null);

            // Connexion à la BDD et insertion
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://switchyard.proxy.rlwy.net:31810/railway",
                "root",
                "eICpmWwoOAPDdTIqkCGclypNTfbCtttY"
            );

            String sql = "INSERT INTO Menage (nom, adresse, adresseMail, motDePasse, CodeAcces, pointsFidelite) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, menage.getNom());
            stmt.setString(2, menage.getAdresse());
            stmt.setString(3, menage.getAdresseMail());
            stmt.setString(4, menage.getMotDePasse());
            stmt.setInt(5, menage.getCodeAcces());
            stmt.setInt(6, menage.getPointsFidelite()); // 0 au départ

            stmt.executeUpdate();
            conn.close();

            messageLabel.setText("Inscription réussie !");

        } catch (NumberFormatException e) {
            messageLabel.setText("Code d'accès doit être un nombre !");
        } catch (SQLException e) {
            e.printStackTrace();
            messageLabel.setText("Erreur lors de l'inscription !");
        }
    }

    @FXML
    private void handleRetour() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ihm/Connexion.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) retourButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Connexion");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
}
