package controller;

import dao.BacIntelligentDAO;
import dao.OperationDepotDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import model.*;

import java.io.IOException;
import java.util.List;

public class DepotController {

    @FXML private ComboBox<BacIntelligent> comboBac;
    @FXML private ComboBox<TypeDechet> comboType;
    @FXML private TextField poidsField;
    @FXML private Label messageLabel;
    @FXML private Button retourButton;

    private Menage utilisateur;

    public void setUtilisateur(Menage menage) {
        this.utilisateur = menage;
    }

    public void initialize() {
        BacIntelligentDAO dao = new BacIntelligentDAO();
        List<BacIntelligent> bacs = dao.getAllBacs();
        comboBac.getItems().addAll(bacs);

        comboType.getItems().addAll(TypeDechet.values());

        comboBac.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(BacIntelligent item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getTypePoubelleLabel() + " - " + item.getEmplacement());
                }
            }
        });
        comboBac.setButtonCell(comboBac.getCellFactory().call(null));
    }

    @FXML
    private void validerDepot() {
        try {
            BacIntelligent bacChoisi = comboBac.getValue();
            TypeDechet type = comboType.getValue();
            double poids = Double.parseDouble(poidsField.getText());

            if (bacChoisi == null || type == null || poids <= 0) {
                throw new IllegalArgumentException("Champs invalides");
            }

            Dechet d = new Dechet(type, poids);

            boolean typeConforme = bacChoisi.getType().accepte(d.getType());
            int points = typeConforme ? bacChoisi.calculerPoints(d) : -5;
            OperationDepot depot = new OperationDepot(0, utilisateur, bacChoisi, List.of(d), points);
            depot.setQuantite(poids);

            OperationDepotDAO depotDAO = new OperationDepotDAO();
            boolean success = depotDAO.enregistrerDepot(depot);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            if (success) {
                alert.setTitle("Succès");
                alert.setContentText("Dépôt effectué ! " + (points < 0 ? "(Type incorrect - points retirés)" : ""));
            } else {
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setTitle("Échec");
                alert.setContentText("Dépôt refusé (bac plein ou erreur interne).");
            }
            alert.showAndWait();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Une erreur s'est produite");
            alert.setContentText("Assure-toi d'avoir rempli tous les champs correctement.");
            alert.showAndWait();
        }
    }

    @FXML
    private void retourAccueil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ihm/Accueil.fxml"));
            Parent root = loader.load();

            AccueilController controller = loader.getController();
            controller.setMenage(utilisateur);

            Stage stage = (Stage) retourButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Accueil");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
