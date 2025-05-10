package controller;

import dao.OperationDepotDAO;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javafx.animation.PauseTransition;
import model.TypeDechet;

import java.util.Map;

public class StatistiquesDechetController {

    @FXML
    private BarChart<String, Number> barChartDechets;

    @FXML
    public void initialize() {
        try {
            OperationDepotDAO dao = new OperationDepotDAO();
            Map<TypeDechet, Double> stats = dao.getStatistiquesParTypeDechet();

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Déchets collectés");

            for (Map.Entry<TypeDechet, Double> entry : stats.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    XYChart.Data<String, Number> data = new XYChart.Data<>(entry.getKey().name(), entry.getValue());
                    series.getData().add(data);
                }
            }

            barChartDechets.getData().clear();
            barChartDechets.getData().add(series);

            // Ajouter un petit délai pour s'assurer que les barres sont dessinées
            PauseTransition delay = new PauseTransition(Duration.millis(500));
            delay.setOnFinished(event -> {
                for (XYChart.Data<String, Number> data : series.getData()) {
                    Label label = new Label(String.format("%.1f", data.getYValue().doubleValue()));
                    StackPane bar = (StackPane) data.getNode();
                    bar.getChildren().add(label);
                }
            });
            delay.play();

        } catch (Exception e) {
            System.out.println("❌ Erreur lors du chargement des statistiques de déchets : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
