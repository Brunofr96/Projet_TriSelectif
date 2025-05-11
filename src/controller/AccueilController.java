package controller;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Menage;

import java.io.IOException;

public class AccueilController {

    @FXML private Label nomLabel;
    @FXML private Label pointsLabel;

    @FXML private Button deposerButton;
    @FXML private Button historiqueButton;
    @FXML private Button offresButton;
    @FXML private Button utiliserPointsButton;
    @FXML private Button ecoloButton;
    @FXML private Button deconnexionButton;

    private Menage utilisateurActuel;

    public void setMenage(Menage menage) {
        this.utilisateurActuel = menage;
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

    @FXML
    private void ouvrirDepot() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Vérification de sécurité");
        dialog.setHeaderText("Authentification requise");
        dialog.setContentText("Veuillez entrer votre code d'accès :");

        dialog.showAndWait().ifPresent(code -> {
            try {
                int codeEntre = Integer.parseInt(code.trim());

                if (codeEntre == utilisateurActuel.getCodeAcces()) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ihm/Depot.fxml"));
                    Parent root = loader.load();

                    controller.DepotController controller = loader.getController();
                    controller.setUtilisateur(utilisateurActuel);

                    Stage stage = (Stage) deposerButton.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Dépôt de déchets");
                    stage.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Accès refusé");
                    alert.setHeaderText(null);
                    alert.setContentText("Code incorrect.");
                    alert.showAndWait();
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText(" Veuillez entrer un code valide.");
                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void mesBons() {
        System.out.println("Retour accueil déclenché.");
    }

    @FXML
    private void ouvrirHistorique() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ihm/HistoriqueDepot.fxml"));
            Parent root = loader.load();

            controller.HistoriqueDepotController controller = loader.getController();
            controller.setMenageConnecte(utilisateurActuel);

            Stage stage = new Stage();
            stage.setTitle("Historique de mes dépôts");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ouvrirUtiliserPoints() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ihm/UtiliserPoints.fxml"));
            Parent root = loader.load();

            controller.UtiliserPointsController controller = loader.getController();
            controller.setMenage(utilisateurActuel); // si tu as besoin de transmettre le ménage

            Stage stage = new Stage();
            stage.setTitle("Utiliser mes points");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleMesBons() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ihm/MesBons.fxml"));
            Parent root = loader.load();

            MesBonsController controller = loader.getController();
            controller.setMenage(utilisateurActuel); // on passe l'objet connecté

            Stage stage = (Stage) utiliserPointsButton.getScene().getWindow(); // ou un autre bouton dédié
            stage.setScene(new Scene(root));
            stage.setTitle("Mes bons");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
