package controller;

import dao.BacIntelligentDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.BacIntelligent;
import model.TypePoubelle;

import java.io.IOException;
import java.util.List;

public class CentreDeTriController {

    @FXML
    private TableView<BacIntelligent> bacsTable;
    @FXML
    private TableColumn<BacIntelligent, Integer> colId;
    @FXML
    private TableColumn<BacIntelligent, String> colType;
    @FXML
    private TableColumn<BacIntelligent, Double> colCapacite;
    @FXML
    private TableColumn<BacIntelligent, Double> colPoids;
    @FXML
    private TableColumn<BacIntelligent, String> colEmplacement;
    @FXML
    private TableColumn<BacIntelligent, String> colPleine;
    @FXML
    private Button btnAjouter;
    @FXML
    private Button btnSupprimer;
    @FXML
    private Button btnCollecter;

    private BacIntelligentDAO bacDAO = new BacIntelligentDAO();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colType.setCellValueFactory(new PropertyValueFactory<>("typePoubelleLabel"));
        colCapacite.setCellValueFactory(new PropertyValueFactory<>("capaciteMax"));
        colPoids.setCellValueFactory(new PropertyValueFactory<>("poidsActuel"));
        colEmplacement.setCellValueFactory(new PropertyValueFactory<>("emplacement"));
        colPleine.setCellValueFactory(cellData -> {
            boolean estPleine = cellData.getValue().isEstPleine();
            String affichage = estPleine ? "Oui" : "Non";
            return new javafx.beans.property.SimpleStringProperty(affichage);
        });


        rafraichirTable();

        btnAjouter.setOnAction(e -> ouvrirFormulaireAjoutBac());
        btnSupprimer.setOnAction(e -> supprimerBac());
        btnCollecter.setOnAction(e -> collecterDechets());
    }

    private void rafraichirTable() {
        List<BacIntelligent> bacs = bacDAO.getAllBacs();
        ObservableList<BacIntelligent> observableBacs = FXCollections.observableArrayList(bacs);
        bacsTable.setItems(observableBacs);
    }

    private void ouvrirFormulaireAjoutBac() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ihm/AjoutBac.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Ajouter un bac");
            stage.setScene(new Scene(root));
            stage.showAndWait(); // attend la fermeture avant de rafraîchir
            rafraichirTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void supprimerBac() {
        BacIntelligent bac = bacsTable.getSelectionModel().getSelectedItem();
        if (bac != null) {
            bacDAO.supprimerBac(bac.getId());
            rafraichirTable();
        }
    }

    private void collecterDechets() {
        bacDAO.collecterTousLesBacs();
        rafraichirTable();
    }
    
    @FXML
    private void handleFermer() {
        Stage stage = (Stage) bacsTable.getScene().getWindow();
        stage.close();
    }


}
