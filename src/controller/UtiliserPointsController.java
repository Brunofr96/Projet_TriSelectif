package controller;

import dao.BonPossedeDAO;
import dao.OffreFideliteDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Menage;
import model.OffreFidelite;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UtiliserPointsController {

    @FXML private Label pointsLabel;
    @FXML private TableView<OffreFidelite> bonsTable;
    @FXML private TableColumn<OffreFidelite, String> descriptionColumn;
    @FXML private TableColumn<OffreFidelite, Integer> coutColumn;
    @FXML private TableColumn<OffreFidelite, Void> actionColumn;
    @FXML private Label confirmationLabel;
    @FXML private Button retourButton;

    private Menage menage;

    public void setMenage(Menage menage) {
        this.menage = menage;
        pointsLabel.setText("Mes points : " + menage.getPointsFidelite());
        initialiserTable();
    }

    private void initialiserTable() {
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        coutColumn.setCellValueFactory(new PropertyValueFactory<>("cout"));

        actionColumn.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Utiliser");

            {
                btn.setOnAction(event -> {
                    OffreFidelite offre = getTableView().getItems().get(getIndex());

                    if (offre.appliquerOperation(menage)) {
                        utiliserBon(offre);
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

        // Remplace le contenu manuel par celui venant de la base
        OffreFideliteDAO dao = new OffreFideliteDAO();
        List<OffreFidelite> offres = dao.getAllOffres();
        bonsTable.getItems().setAll(offres);
    }


    private void utiliserBon(OffreFidelite offre) {
        try {
            // 1. Déduire les points (déjà fait dans appliquerOperation)
            // 2. Mettre à jour les points dans la BDD
            String sql = "UPDATE Menage SET pointsFidelite = ? WHERE Id_Menage = ?";
            try (Connection conn = database.DatabaseManager.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, menage.getPointsFidelite());
                stmt.setInt(2, menage.getId());
                stmt.executeUpdate();
            }

            // 3. Enregistrer le bon acheté dans BonsPossedes
            BonPossedeDAO dao = new BonPossedeDAO();
            dao.ajouterOuIncrementerBon(menage, offre);

            confirmationLabel.setText("Offre utilisée : " + offre.getDescription());
            pointsLabel.setText("Mes points : " + menage.getPointsFidelite());

        } catch (SQLException e) {
            e.printStackTrace();
            confirmationLabel.setText("Erreur lors de l'enregistrement.");
        }
    }

    @FXML
    private void handleRetour() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ihm/Accueil.fxml"));
            Parent root = loader.load();

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
