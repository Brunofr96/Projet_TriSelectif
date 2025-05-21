package controller;

import dao.OperationDepotDAO;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import model.TypePoubelle;

import java.util.Map;

public class StatistiquesController {

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    public void initialize() {
        try {
            OperationDepotDAO dao = new OperationDepotDAO();
            Map<TypePoubelle, Double> stats = dao.getStatistiquesParTypePoubelle();

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Déchets collectés");

            for (Map.Entry<TypePoubelle, Double> entry : stats.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    XYChart.Data<String, Number> data = new XYChart.Data<>(entry.getKey().name(), entry.getValue());
                    series.getData().add(data);
                }
            }

            barChart.getData().clear();
            barChart.getData().add(series);

            // ⏳ Délai pour que le graphique soit prêt
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
            System.out.println(" Erreur chargement des stats poubelles : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
