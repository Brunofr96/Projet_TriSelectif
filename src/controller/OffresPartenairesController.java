package controller;

import dao.BonPossedeDAO;
import dao.ContratPartenaireDAO;
import dao.OffreFideliteDAO;
import dao.MenageDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.fxml.FXMLLoader;
import model.ContratPartenaire;
import model.Menage;
import model.OffreFidelite;

import java.io.IOException;
import java.util.*;

public class OffresPartenairesController {

    @FXML private TableView<OffreFidelite> tableContrats;
    @FXML private TableColumn<OffreFidelite, String> colDescription;
    @FXML private TableColumn<OffreFidelite, Integer> colPoints;
    @FXML private TableColumn<OffreFidelite, String> colCategories;
    @FXML private TableColumn<OffreFidelite, Void> colAction;
    @FXML private Label pointsLabel;
    @FXML private ComboBox<String> categorieFilter;
    @FXML private Button retourButton;

    private Menage menage;
    private List<OffreFidelite> allOffres;

    public void setMenage(Menage menage) {
        this.menage = menage;
        pointsLabel.setText("Mes points : " + menage.getPointsFidelite());
        initialiserTable();
    }

    private void initialiserTable() {
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPoints.setCellValueFactory(new PropertyValueFactory<>("cout"));

        colCategories.setCellValueFactory(data -> {
            ContratPartenaire contrat = new ContratPartenaireDAO().getContratParCommerce(data.getValue().getIdCommerce());
            String categories = String.join(", ", contrat.getCategoriesProduits());
            return new SimpleStringProperty(categories);
        });

        colAction.setCellFactory(col -> new TableCell<>() {
            private final Button btnUtiliser = new Button("Utiliser");
            private final Button btnDetails = new Button("Détails");

            {
                // Largeur fixe pour éviter les boutons tronqués
                btnUtiliser.setPrefWidth(70);
                btnDetails.setPrefWidth(70);

                // Action du bouton "Utiliser"
                btnUtiliser.setOnAction(event -> {
                    OffreFidelite offre = getTableView().getItems().get(getIndex());

                    if (offre.appliquerOperation(menage)) {
                        new MenageDAO().mettreAJourPoints(menage);
                        new BonPossedeDAO().ajouterOuIncrementerBon(menage, offre);

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Bon utilisé !");
                        alert.setContentText("Votre bon a été ajouté dans Mes Bons !");
                        alert.showAndWait();

                        // Mettre à jour l'affichage des points
                        pointsLabel.setText("Mes points : " + menage.getPointsFidelite());
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Points insuffisants");
                        alert.setContentText("Vous n'avez pas assez de points pour cette offre.");
                        alert.showAndWait();
                    }
                });

                // Action du bouton "Détails"
                btnDetails.setOnAction(event -> {
                    OffreFidelite offre = getTableView().getItems().get(getIndex());
                    ContratPartenaire contrat = new ContratPartenaireDAO().getContratParCommerce(offre.getIdCommerce());

                    if (contrat != null) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Détails du contrat partenaire");
                        alert.setContentText(
                            "Du " + contrat.getDateDebut() + " au " + contrat.getDateFin()
                            + "\nPoints requis : " + offre.getCout()
                            + "\nRenouvelable : " + (contrat.isRenouvelable() ? "Oui" : "Non")
                            + "\nCatégories : " + String.join(", ", contrat.getCategoriesProduits())
                        );
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Erreur");
                        alert.setContentText("Aucun contrat partenaire trouvé pour cette offre.");
                        alert.showAndWait();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(10, btnDetails, btnUtiliser);
                    hbox.setAlignment(Pos.CENTER);
                    setGraphic(hbox);
                }
            }
        });


        // Charger toutes les offres
        OffreFideliteDAO dao = new OffreFideliteDAO();
        allOffres = dao.getAllOffres();

        // Afficher uniquement les offres dont le contrat est encore valide
        tableContrats.getItems().setAll(
                allOffres.stream()
                        .filter(o -> {
                            ContratPartenaire c = new ContratPartenaireDAO().getContratParCommerce(o.getIdCommerce());
                            return c != null && c.estValide(new Date());
                        }).toList()
        );

        // Remplir la ComboBox des catégories disponibles
        Set<String> categoriesUniques = new HashSet<>();
        for (OffreFidelite offre : allOffres) {
            ContratPartenaire c = new ContratPartenaireDAO().getContratParCommerce(offre.getIdCommerce());
            if (c != null) categoriesUniques.addAll(c.getCategoriesProduits());
        }

        categorieFilter.getItems().add("Toutes les catégories");
        categorieFilter.getItems().addAll(categoriesUniques);
        categorieFilter.setValue("Toutes les catégories");
    }

    @FXML
    private void filtrerParCategorie() {
        String selected = categorieFilter.getValue();
        List<OffreFidelite> filtres = new ArrayList<>();

        for (OffreFidelite offre : allOffres) {
            ContratPartenaire c = new ContratPartenaireDAO().getContratParCommerce(offre.getIdCommerce());
            if (c != null && c.estValide(new Date())) {
                if (selected.equals("Toutes les catégories") || c.getCategoriesProduits().contains(selected)) {
                    filtres.add(offre);
                }
            }
        }

        tableContrats.getItems().setAll(filtres);
    }

    @FXML
    private void handleRetour() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ihm/Accueil.fxml"));
            Parent root = loader.load();

            controller.AccueilController controller = loader.getController();
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
