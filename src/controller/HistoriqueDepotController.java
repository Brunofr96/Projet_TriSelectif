package controller;

import dao.OperationDepotDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Menage;
import model.OperationDepot;

import java.util.List;

public class HistoriqueDepotController {

    @FXML
    private TableView<OperationDepot> historiqueTable;

    @FXML
    private TableColumn<OperationDepot, String> colBac;

    @FXML
    private TableColumn<OperationDepot, String> colType;

    @FXML
    private TableColumn<OperationDepot, Double> colQuantite;
    
    @FXML
    private TableColumn<OperationDepot, String> colDateDepot;

    @FXML
    private TableColumn<OperationDepot, Integer> colPoints;


    @FXML
    private Button btnRetour;

    // Ménage connecté, à passer depuis la page de connexion
    private Menage menageConnecte;

    @FXML
    public void initialize() {
        colBac.setCellValueFactory(new PropertyValueFactory<>("nomBac"));
        colType.setCellValueFactory(new PropertyValueFactory<>("typeDechet"));
        colQuantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        colDateDepot.setCellValueFactory(new PropertyValueFactory<>("dateDepotFormatee"));
        colPoints.setCellValueFactory(new PropertyValueFactory<>("pointsGagnes"));

        btnRetour.setOnAction(e -> btnRetour.getScene().getWindow().hide());
    }

    private void chargerHistorique() {
        OperationDepotDAO depotDAO = new OperationDepotDAO();
        List<OperationDepot> liste = depotDAO.getDepotsParMenage(menageConnecte);
        ObservableList<OperationDepot> observableList = FXCollections.observableArrayList(liste);
        historiqueTable.setItems(observableList);
    }

    // Méthode pour transmettre le ménage connecté
    public void setMenageConnecte(Menage menage) {
        this.menageConnecte = menage;
        chargerHistorique(); // Recharge quand on reçoit le ménage
    }
}
