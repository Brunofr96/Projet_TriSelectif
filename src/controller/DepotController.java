package controller;

import dao.BacIntelligentDAO;
import dao.DechetDAO;
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
    public void validerDepot() {
        BacIntelligent bac = comboBac.getValue();
        TypeDechet type = comboType.getValue();
        String poidsText = poidsField.getText();

        if (bac == null || type == null || poidsText.isEmpty()) {
            messageLabel.setText("Veuillez remplir tous les champs.");
            return;
        }

        try {
            double poids = Double.parseDouble(poidsText);
            Dechet dechet = new Dechet(type, poids);

            DechetDAO dechetDAO = new DechetDAO();
            dechetDAO.enregistrerDechet(dechet);

            List<Dechet> liste = List.of(dechet); // ✅ Corrigé ici

            OperationDepot depot = new OperationDepot(
                (int)(Math.random() * 10000),
                utilisateur,
                bac,
                liste,
                (int)(poids * 10)
            );

            OperationDepotDAO depotDAO = new OperationDepotDAO();
            depotDAO.enregistrerDepot(depot);

            utilisateur.ajouterPoints((int)(poids * 10));
            messageLabel.setText("✅ Dépôt enregistré (" + (int)(poids * 10) + " pts gagnés)");

        } catch (NumberFormatException e) {
            messageLabel.setText("❌ Le poids doit être un nombre.");
        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("❌ Erreur lors de l'enregistrement.");
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
