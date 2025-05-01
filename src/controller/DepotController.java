package controller;

import dao.OperationDepotDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;



public class DepotController {

    @FXML private ComboBox<BacIntelligent> comboBac;
    @FXML private ComboBox<TypeDechet> comboType;
    @FXML private TextField poidsField;
    @FXML private Label messageLabel;

    private Menage utilisateur;
    @FXML
    private Button retourButton;


    public void initialize() {
        // ⚠️ Cette liste est à adapter si tu charges les bacs depuis la BDD
        comboBac.getItems().addAll(
            new BacIntelligent(1, TypePoubelle.JAUNE, 50.0, "Entrée"),
            new BacIntelligent(2, TypePoubelle.VERTE, 50.0, "Sortie")
        );

        comboType.getItems().addAll(TypeDechet.values());
    }

    // À appeler depuis le contrôleur précédent après connexion
    public void setUtilisateur(Menage menage) {
        this.utilisateur = menage;
    }

    @FXML
    public void validerDepot() {
        BacIntelligent bac = comboBac.getValue();
        TypeDechet type = comboType.getValue();
        String poidsText = poidsField.getText();

        if (bac == null || type == null || poidsText.isEmpty()) {
            messageLabel.setText(" Veuillez remplir tous les champs.");
            return;
        }

        try {
            double poids = Double.parseDouble(poidsText);
            Dechet dechet = new Dechet(type, poids);
            List<Dechet> liste = List.of(dechet);

            int points = bac.ajouterDechet(liste, utilisateur);
            OperationDepot depot = new OperationDepot(
                (int)(Math.random() * 10000), // ID temporaire
                utilisateur, bac, liste, points
            );

            new OperationDepotDAO().enregistrerDepot(depot);
            utilisateur.ajouterPoints(points);

            messageLabel.setText(" Dépôt enregistré (" + points + " pts gagnés)");

        } catch (NumberFormatException e) {
            messageLabel.setText(" Le poids doit être un nombre.");
        }
    }
    
    @FXML
    private void retourAccueil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ihm/Accueil.fxml"));
            Parent root = loader.load();

            controller.AccueilController controller = loader.getController();
            controller.setMenage(utilisateur); // Réinjecter l'utilisateur

            Stage stage = (Stage) retourButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Accueil");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    
}
