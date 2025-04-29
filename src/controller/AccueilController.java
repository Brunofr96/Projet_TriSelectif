package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Menage;

import java.io.IOException;

public class AccueilController {

    @FXML
    private Label nomLabel;

    @FXML
    private Label pointsLabel;

    @FXML
    private Button deposerButton;
    @FXML
    private Button historiqueButton;
    @FXML
    private Button offresButton;
    @FXML
    private Button utiliserPointsButton;
    @FXML
    private Button ecoloButton;
    @FXML
    private Button deconnexionButton;

    public void setMenage(Menage menage) {
        nomLabel.setText("Bonjour, " + menage.getNom());
        pointsLabel.setText("Points fidélité : " + menage.getPointsFidelite());
    }
    @FXML
    private void handleDeconnexion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ihm/Connexion.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) deconnexionButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Connexion");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
	