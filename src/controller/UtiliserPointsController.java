package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import model.Menage;
import model.OffreFidelite;

import java.io.IOException;
import java.util.List;

public class UtiliserPointsController {

    @FXML
    private Label pointsLabel;

    @FXML
    private TableView<OffreFidelite> bonsTable;

    @FXML
    private TableColumn<OffreFidelite, String> descriptionColumn;

    @FXML
    private TableColumn<OffreFidelite, Integer> coutColumn;

    @FXML
    private TableColumn<OffreFidelite, Void> actionColumn;

    @FXML
    private Label confirmationLabel;

    @FXML
    private Button retourButton;

    private Menage menage;

    public void setMenage(Menage menage) {
        this.menage = menage;
        pointsLabel.setText("Mes points : " + menage.getPointsFidelite());
        initialiserTable();
    }

    private void initialiserTable() {
        // Liens entre colonnes et attributs
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        coutColumn.setCellValueFactory(new PropertyValueFactory<>("cout"));

        // Bouton d'action par ligne
        actionColumn.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Utiliser");

            {
                btn.setOnAction(event -> {
                    OffreFidelite offre = getTableView().getItems().get(getIndex());

                    if (offre.appliquerOperation(menage)) {
                        confirmationLabel.setText("Offre utilisée : " + offre.getDescription());
                        pointsLabel.setText("Mes points : " + menage.getPointsFidelite());
                    } else {
                        confirmationLabel.setText("Points insuffisants pour : " + offre.getDescription());
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

        // Exemples d’offres (à remplacer par BDD plus tard)
        bonsTable.getItems().setAll(
            new OffreFidelite(1, "Réduction 5€ chez Partenaire A", 20, "Réduction"),
            new OffreFidelite(2, "Bon d’achat 10€", 50, "Bon"),
            new OffreFidelite(3, "Livraison gratuite", 30, "Service")
        );
    }

    @FXML
    private void handleRetour() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ihm/Accueil.fxml"));
            Parent root = loader.load();

            // Repassage de l’objet Menage
            AccueilController controller = loader.getController();
            controller.setMenage(menage);

            Stage stage = (Stage) retourButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Accueil");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
