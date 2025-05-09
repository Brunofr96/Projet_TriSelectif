package controller;

import dao.BacIntelligentDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.BacIntelligent;
import model.TypePoubelle;

public class AjoutBacController {

    @FXML
    private ComboBox<TypePoubelle> typeCombo;

    @FXML
    private Spinner<Double> capaciteSpinner;

    @FXML
    private TextField emplacementField;

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnAnnuler;

    @FXML
    public void initialize() {
        typeCombo.getItems().addAll(TypePoubelle.values());

        // Valeurs par défaut pour la capacité (de 10 à 500 kg, par pas de 10)
        capaciteSpinner.setValueFactory(new javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory(10, 500, 50, 10));

        btnAjouter.setOnAction(e -> ajouterBac());
        btnAnnuler.setOnAction(e -> fermerFenetre());
    }

    private void ajouterBac() {
        TypePoubelle type = typeCombo.getValue();
        double capacite = capaciteSpinner.getValue();
        String emplacement = emplacementField.getText().trim();

        if (type == null || emplacement.isEmpty()) {
            System.out.println("Veuillez remplir tous les champs !");
            return;
        }

        BacIntelligent bac = new BacIntelligent(0, type, capacite, emplacement);
        bac.setIdCentreDeTri(1); // On fixe à 1 car un seul centre

        BacIntelligentDAO dao = new BacIntelligentDAO();
        dao.enregistrerBac(bac);

        System.out.println("Bac ajouté avec succès !");
        fermerFenetre();
    }

    private void fermerFenetre() {
        Stage stage = (Stage) btnAnnuler.getScene().getWindow();
        stage.close();
    }
}
