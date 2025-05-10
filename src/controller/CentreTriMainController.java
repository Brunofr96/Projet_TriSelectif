package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CentreTriMainController {

    @FXML
    private AnchorPane mainContainer;

    @FXML
    private void afficherGestionBacs() {
        chargerVue("/ihm/CentreDeTri.fxml");
    }

    @FXML
    private void afficherStatistiquesPoubelles() {
        chargerVue("/ihm/Statistiques.fxml");
    }

    @FXML
    private void afficherStatistiquesDechets() {
        chargerVue("/ihm/StatistiqueDechets.fxml"); // ✅ corrigé ici
    }

    private void chargerVue(String chemin) {
        try {
            Parent contenu = FXMLLoader.load(getClass().getResource(chemin));
            mainContainer.getChildren().setAll(contenu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
