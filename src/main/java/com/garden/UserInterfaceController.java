package com.garden;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextFlow;

import java.util.List;
import java.util.Map;

public class UserInterfaceController {
    @FXML private GridPane gardenGrid;
    @FXML private ListView<TextFlow> dayLogList;
    @FXML private ListView<TextFlow> wateringLogList;
    @FXML private ListView<TextFlow> heatingLogList;
    @FXML private ListView<TextFlow> insectLogList;
    @FXML private ListView<TextFlow> cleanerLogList;
    @FXML private ImageView weatherImageView;
    @FXML private Label weatherLabel;
    @FXML private ListView<String> directoryListView;
    @FXML private TableView<PlantDetails> plantTable;
    @FXML private Label gameOverLabel;
    @FXML private ImageView boilerImageView;
    @FXML private Label temperatureLabel;
    @FXML private ProgressIndicator progressIndicator;
    private Label progressLabel;


    private GardenApi gardenApi;
    @FXML ProgressIndicator progressBar;
    double progress;

    @FXML
    public void initialize() {
        gardenApi = new GardenApi();
        gardenApi.initializeGarden();
        updateGardenView();
        progressBar.setStyle("-fx-accent : #00FF00;");
    }

    public void increaseProgress() {
        int temperature = gardenApi.getCurrentTemperature();

        if (temperature < 10) {
            progressBar.setProgress(0.25);
            progressBar.setStyle("-fx-accent: blue;");
            progressLabel.setText("Progress: 25%");
        } else if (temperature < 20) {
            progressBar.setProgress(0.5);
            progressBar.setStyle("-fx-accent: lightblue;");
            progressLabel.setText("Progress: 50%");
        } else if (temperature > 30) {
            progressBar.setProgress(0.75);
            progressBar.setStyle("-fx-accent: red;");
            progressLabel.setText("Progress: 75%");
        } else {
            progressBar.setProgress(1.0);
            progressBar.setStyle("-fx-accent: green;");
            progressLabel.setText("Progress: 100%");
        }
    }
    private void updateGardenView() {
        updateGardenGrid();
        updateLog();
        updateWeatherImage();
        updateDirectory();
    }

    private void updateGardenGrid() {
        // Implementation to update the garden grid based on the garden state
    }

    private void updateTemperature(int temperature) {
        temperatureLabel.setText("Temperature: " + temperature + "°C");
    }
    private void updateLog() {
        // Implementation to update the log lists
    }

    private void updateWeatherImage() {
        // Implementation to update the weather image
    }

    private void updateDirectory() {
        // Implementation to update the garden directory
    }

    @FXML
    private void handleSimulateDay() {
        gardenApi.simulateDay();
        updateGardenView();
        if (gardenApi.isAllPlantsDead()) {
            gameOverLabel.setText("All plants have died. Plant more to continue.");
        }
    }

    @FXML
    private void handleRain() {
        gardenApi.rain(10);  // Example value for rainfall amount
        updateGardenView();
    }

    @FXML
    private void handleTemperature() {
        gardenApi.temperature(70);  // Example value for temperature
        updateGardenView();
    }

    @FXML
    private void handleParasite() {
        gardenApi.parasite("Spider");  // Example value for parasite
        updateGardenView();
    }

    @FXML
    private void handleGetState() {
        gardenApi.getState();
    }
    @FXML
    private void increaseProgress(double value) {
        double newProgress = progressBar.getProgress() + value;
        if (newProgress > 1.0) {
            newProgress = 1.0;
        } else if (newProgress < 0.0) {
            newProgress = 0.0;
        }
        progressBar.setProgress(newProgress);
        updateProgressBar((int) (newProgress * 100)); // Update the color based on the new progress
    }
    private void updateProgressBar(int temperature) {
        progressBar.setProgress(temperature / 100.0);
        if (temperature > 80) {
            progressBar.setStyle("-fx-accent: red;");
        } else {
            progressBar.setStyle("-fx-accent: green;");
        }
    }
}
