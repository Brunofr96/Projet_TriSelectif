package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
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

    // ➤ Ménage connecté
    private Menage utilisateurActuel;

    // ➤ Appelée depuis ConnexionController
    public void setMenage(Menage menage) {
        this.utilisateurActuel = menage;
        nomLabel.setText("Bonjour, " + menage.getNom());
        pointsLabel.setText("Points fidélité : " + menage.getPointsFidelite());
    }

    // ➤ Bouton Déconnexion
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

    // ➤ Bouton "Déposer des déchets"
    @FXML
    private void ouvrirDepot() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ihm/Depot.fxml"));
            Parent root = loader.load();

            controller.DepotController controller = loader.getController();
            controller.setUtilisateur(utilisateurActuel);

            Stage stage = (Stage) deposerButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Dépôt de déchets");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void retourAccueil() {
        System.out.println("🔁 Retour accueil déclenché.");
        // Tu peux y mettre un vrai retour ou navigation plus tard
    }

    // ➤ Les autres boutons peuvent être ajoutés de la même manière (historique, offres, etc.)
}
