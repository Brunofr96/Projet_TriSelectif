package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import model.Menage;
import model.OffreFidelite;
import model.BonPossede;
import dao.BonPossedeDAO;
import dao.OffreFideliteDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MesBonsController {

    @FXML
    private TableView<BonAffiche> bonsTable;

    @FXML
    private TableColumn<BonAffiche, String> descriptionColumn;

    @FXML
    private TableColumn<BonAffiche, Integer> quantiteColumn;

    @FXML
    private Button retourButton;

    private Menage menage;

    public void setMenage(Menage menage) {
        this.menage = menage;
        chargerBons();
    }

    private void chargerBons() {
        BonPossedeDAO bonDAO = new BonPossedeDAO();
        OffreFideliteDAO offreDAO = new OffreFideliteDAO();

        List<BonPossede> bonsPossedes = bonDAO.getBonsParMenage(menage.getId());
        List<BonAffiche> bonsAffiches = new ArrayList<>();

        for (BonPossede bon : bonsPossedes) {
            OffreFidelite offre = offreDAO.getOffreParId(bon.getIdOffre());
            if (offre != null) {
                bonsAffiches.add(new BonAffiche(offre.getDescription(), bon.getQuantite()));
            }
        }

        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        quantiteColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        bonsTable.getItems().setAll(bonsAffiches);
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

    // Classe interne temporaire pour l'affichage
    public static class BonAffiche {
        private final String description;
        private final int quantite;

        public BonAffiche(String description, int quantite) {
            this.description = description;
            this.quantite = quantite;
        }

        public String getDescription() {
            return description;
        }

        public int getQuantite() {
            return quantite;
        }
    }
}
