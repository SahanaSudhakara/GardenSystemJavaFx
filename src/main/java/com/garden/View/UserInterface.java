package com.garden.View;

import com.garden.Helpers.Cleaner;
import com.garden.Controller.GardenController;
import com.garden.Model.*;
import com.garden.Controller.PestControl;
import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class UserInterface extends Application{
    protected GridPane gardenGrid;
    protected ListView<TextFlow> dayLogList;
    protected ListView<TextFlow> wateringLogList;
    protected ListView<TextFlow> heatingLogList;
    protected ListView<TextFlow> insectLogList;
    protected ListView<TextFlow> cleanerLogList;
    protected Random random;
    protected ImageView weatherImageView;
    protected Label weatherLabel;
    protected ListView<String> directoryListView;
    protected Timeline simulationTimeline;
    protected PestControl pestControl;
    protected TableView<PlantDetails> plantTable;
    protected String currentWeather;
    protected ProgressBar waterProgressBar;
    protected Label waterProgressLabel;
    protected ProgressBar temperatureProgressBar;
    protected Label temperatureProgressLabel;
    private GardenController gardenController;

    @Override
    public void start(Stage primaryStage) {

        random = new Random();

        VBox root = new VBox();
        root.setSpacing(10);
        root.setStyle("-fx-padding: 10; -fx-background-image: url('file:src/main/java/com/garden/images/garden_background.png');"
                + "-fx-background-size: cover;");

        // Garden Grid with Transparent Background
        gardenGrid = new GridPane();
        createGardenGrid();
        gardenGrid.setStyle("-fx-background-color: transparent;");
        HBox.setHgrow(gardenGrid, Priority.ALWAYS);

        VBox gridBox = new VBox();
        if (gardenGrid != null) {
            gridBox.getChildren().addAll(gardenGrid);
        }
        gridBox.setStyle("-fx-border-color: #4682B4; -fx-border-width: 2; -fx-background-color: transparent;");
        VBox.setVgrow(gardenGrid, Priority.ALWAYS);

        dayLogList = new ListView<>();
        VBox.setVgrow(dayLogList, Priority.ALWAYS);

        wateringLogList = new ListView<>();
        VBox.setVgrow(wateringLogList, Priority.ALWAYS);

        heatingLogList = new ListView<>();
        VBox.setVgrow(heatingLogList, Priority.ALWAYS);

        insectLogList = new ListView<>();
        VBox.setVgrow(insectLogList, Priority.ALWAYS);

        cleanerLogList = new ListView<>();
        VBox.setVgrow(cleanerLogList, Priority.ALWAYS);

        TitledPane dayLogPane = new TitledPane("Day Logs", dayLogList);
        TitledPane wateringLogPane = new TitledPane("Watering Logs", wateringLogList);
        TitledPane heatingLogPane = new TitledPane("Heating Logs", heatingLogList);
        TitledPane insectLogPane = new TitledPane("Insect Attack Logs", insectLogList);
        TitledPane cleanerLogPane = new TitledPane("Cleaner Logs", cleanerLogList);

        Accordion logAccordion = new Accordion();
        logAccordion.getPanes().addAll(dayLogPane, wateringLogPane, heatingLogPane, insectLogPane, cleanerLogPane);
        VBox.setVgrow(logAccordion, Priority.ALWAYS);
        logAccordion.setStyle("-fx-background-color: transparent;");

        HBox topBox = new HBox();
        topBox.setSpacing(10);
        if (gridBox != null) {
            topBox.getChildren().addAll(gridBox);
        }
        HBox.setHgrow(gridBox, Priority.ALWAYS);

        VBox plantBox = createPlantBox();
        VBox.setVgrow(plantBox, Priority.ALWAYS);
        plantBox.setStyle("-fx-background-color: transparent;");

        VBox rightPane = new VBox();
        rightPane.setSpacing(10);

        Label weatherReportLabel = new Label("Weather Report");
        weatherReportLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        weatherReportLabel.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-padding: 5;");

        weatherImageView = new ImageView();
        weatherImageView.setFitWidth(100);
        weatherImageView.setFitHeight(100);

        weatherLabel = new Label("Day 1: Start");
        weatherLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        weatherLabel.setStyle("-fx-padding: 5;");

        VBox weatherBox = new VBox();
        weatherBox.setSpacing(10);
        weatherBox.setAlignment(Pos.CENTER);
        weatherBox.getChildren().addAll(weatherReportLabel, weatherImageView, weatherLabel);
        weatherBox.setStyle("-fx-background-color: transparent;");

        VBox progressBox = new VBox();
        progressBox.setSpacing(10);

        // Water Progress Bar and Label
         waterProgressBar = new ProgressBar(0);
        waterProgressBar.setPrefWidth(200); // Set preferred width of the ProgressBar
         waterProgressLabel = new Label("Water percentage: 0%");

        // Temperature Progress Bar and Label
        temperatureProgressBar = new ProgressBar(0);
        temperatureProgressBar.setPrefWidth(200); // Set preferred width of the ProgressBar
         temperatureProgressLabel = new Label("Temperature percentage: 0%");

        // Add Progress Bars and Labels to the progressBox
        progressBox.getChildren().addAll(temperatureProgressBar, temperatureProgressLabel, waterProgressBar, waterProgressLabel);

        // GIF ImageView
        Image image = new Image(new File("src/main/java/com/garden/images/farmer.gif").toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        // Arrange imageView to the left of progressBox
        HBox tempAndWaterBox = new HBox();
        tempAndWaterBox.setSpacing(10);
        tempAndWaterBox.setAlignment(Pos.CENTER);
        tempAndWaterBox.getChildren().addAll(imageView, progressBox);
        tempAndWaterBox.setStyle("-fx-background-color: transparent;");

        // Directory List View
        Label directoryLabel = new Label("Garden Directory");
        directoryLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        directoryListView = new ListView<>();
        VBox.setVgrow(directoryListView, Priority.ALWAYS);
        directoryListView.setStyle("-fx-background-color: transparent;");

        rightPane.getChildren().addAll(weatherBox, new Separator(), tempAndWaterBox, new Separator(), directoryLabel, directoryListView);
        HBox.setHgrow(rightPane, Priority.ALWAYS);
        rightPane.setStyle("-fx-background-color: transparent;");

        // Create Plant Table
        plantTable = new TableView<>();
        plantTable.setPlaceholder(new Label("No plants available"));
        TableColumn<PlantDetails, String> nameColumn = new TableColumn<>("Plant");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().plantNameProperty());
        TableColumn<PlantDetails, Integer> countColumn = new TableColumn<>("Count");
        countColumn.setCellValueFactory(cellData -> cellData.getValue().countProperty().asObject());
        TableColumn<PlantDetails, Integer> lifespanColumn = new TableColumn<>("Lifespan");
        lifespanColumn.setCellValueFactory(cellData -> cellData.getValue().lifespanProperty().asObject());
        TableColumn<PlantDetails, Integer> pestCountColumn = new TableColumn<>("Pest Attacks");
        pestCountColumn.setCellValueFactory(cellData -> cellData.getValue().pestAttacksProperty().asObject());

        plantTable.getColumns().addAll(nameColumn, countColumn, lifespanColumn, pestCountColumn);
        VBox.setVgrow(plantTable, Priority.ALWAYS);

        HBox mainBox = new HBox();
        mainBox.setSpacing(10);
        mainBox.getChildren().addAll(topBox, rightPane, plantTable);
        HBox.setHgrow(topBox, Priority.ALWAYS);

        VBox buttonBox = new VBox();
        buttonBox.setSpacing(10);
        Button simulateButton = new Button("Simulate Day");
        simulateButton.setStyle("-fx-background-color: #32CD32; -fx-text-fill: white; -fx-font-weight: bold;");
        simulateButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        simulateButton.setOnAction(e -> startSimulation());

        buttonBox.getChildren().addAll(simulateButton);
        buttonBox.setStyle("-fx-background-color: transparent;");

        root.getChildren().addAll(plantBox, mainBox, buttonBox, logAccordion);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Automated Gardening System");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true); // Make the scene full screen
        primaryStage.show();

        pestControl = new PestControl(gardenGrid);
        gardenController = new GardenController(pestControl);

        startAnimation();
    }


    private void updateWeatherImage() {
        String[] weathers = {"sunny.png", "rainy.png", "cloudy.png"};
        String[] weatherDescriptions = {"Sunny", "Rainy", "Cloudy"};
        int index = random.nextInt(weathers.length);
        String selectedWeather = weathers[index];
        currentWeather = weatherDescriptions[index];

        Image weatherImage = getImage(selectedWeather);
        weatherImageView.setImage(weatherImage);
        weatherLabel.setText("Day " + (gardenController.getDay()) + ": " + currentWeather);

        int temperature = getTemperatureForDay(currentWeather);
        temperatureProgressLabel.setText(temperature + " F");
        temperatureProgressLabel.setStyle(getLabelFontForWeather(currentWeather));

        waterProgressLabel.setText(getWaterLevelStringForLabelFontForWeather(currentWeather));
        waterProgressLabel.setStyle("-fx-text-fill: black;");

        // Update the progress bars
        updateProgressBars(temperature);
    }

    private int getTemperatureForDay(String currentWeather) {
        switch (currentWeather) {
            case "Sunny": return 90;
            case "Rainy": return 50;
            case "Cloudy": return 70;
            default: return 100;
        }
    }

    private String getLabelFontForWeather(String currentWeather) {
        switch (currentWeather) {
            case "Sunny": return "-fx-text-fill: red;";
            case "Rainy": return "-fx-text-fill: purple;";
            case "Cloudy": return "-fx-text-fill: green;";
            default: return "-fx-text-fill: black;";
        }
    }

    private String getWaterLevelStringForLabelFontForWeather(String currentWeather) {
        switch (currentWeather) {
            case "Sunny": return "80 %";
            case "Rainy": return "10 %";
            case "Cloudy": return "40 %";
            default: return "30 %";
        }
    }

    private void updateDirectory() {
        directoryListView.getItems().clear();
        directoryListView.getItems().add("Plants:");

        long tomatoCount = gardenController.getPlants().stream().filter(p -> p.getName().equals("Tomato")).count();
        long orangeCount = gardenController.getPlants().stream().filter(p -> p.getName().equals("Orange")).count();
        long sunflowerCount = gardenController.getPlants().stream().filter(p -> p.getName().equals("Sunflower")).count();

        directoryListView.getItems().add("  - Tomato: " + tomatoCount);
        directoryListView.getItems().add("  - Orange: " + orangeCount);
        directoryListView.getItems().add("  - Sunflower: " + sunflowerCount);

        directoryListView.getItems().add("Good Insects:");

        long beetleCount = gardenController.getInsects().stream().filter(i -> i.getName().equals("Beetle")).count();
        long butterflyCount = gardenController.getInsects().stream().filter(i -> i.getName().equals("Butterfly")).count();

        directoryListView.getItems().add("  - Beetle: " + beetleCount);
        directoryListView.getItems().add("  - Butterfly: " + butterflyCount);

        directoryListView.getItems().add("Pests:");

        long spiderCount = gardenController.getInsects().stream().filter(i -> i.getName().equals("Spider")).count();
        long caterpillarCount = gardenController.getInsects().stream().filter(i -> i.getName().equals("Caterpillar")).count();

        directoryListView.getItems().add("  - Spider: " + spiderCount);
        directoryListView.getItems().add("  - Caterpillar: " + caterpillarCount);

        // Update Plant Table
        plantTable.getItems().clear();
        Map<String, Long> plantCounts = gardenController.getPlants().stream()
                .collect(Collectors.groupingBy(Plant::getName, Collectors.counting()));
        for (Plant plant : gardenController.getPlants()) {
            PlantDetails details = new PlantDetails(
                    plant.getName(),
                    plantCounts.getOrDefault(plant.getName(), 0L).intValue(),
                    plant.getDaysToLive(),
                    plant.getPestAttacks()
            );
            plantTable.getItems().add(details);
        }
    }


    private VBox createPlantBox() {
        VBox plantBox = new VBox();
        plantBox.setSpacing(10);

        Label plantLabel = new Label("Plants");
        plantLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        plantLabel.setTextFill(Color.WHITE);  // Set text color to white for better visibility

        HBox plantImagesBox = new HBox();
        plantImagesBox.setSpacing(10);

        VBox tomatoBox = createPlantOption("tomato.png", "Tomato");
        VBox orangeBox = createPlantOption("orange.png", "Orange");
        VBox sunflowerBox = createPlantOption("sunflower.png", "Sunflower");

        plantImagesBox.getChildren().addAll(tomatoBox, orangeBox, sunflowerBox);
        plantBox.getChildren().addAll(plantLabel, plantImagesBox);

        return plantBox;
    }




    private VBox createPlantOption(String fileName, String plantType) {
        ImageView imageView = getImageView(fileName);
        Button button = new Button();
        button.setGraphic(imageView);
        button.setStyle("-fx-background-color: #4682B4; -fx-text-fill: white; -fx-font-weight: bold;");
        button.setOnAction(event -> {
            int row, col;
            do {
                row = random.nextInt(9);
                col = random.nextInt(9);
            } while (!isCellEmpty(row, col));
            Plant plant = null;
            if ("Tomato".equals(plantType)) {
                plant = new Tomato(row, col);
            } else if ("Orange".equals(plantType)) {
                plant = new Orange(row, col);
            } else if ("Sunflower".equals(plantType)) {
                plant = new Sunflower(row, col);
            }
            if (plant != null) {
                gardenController.addPlant(plant);
                animatePlant(plant);
                updateGardenGrid();
                updateLog();
                updateDirectory();
            }
        });

        Label nameLabel = new Label(plantType);
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        nameLabel.setTextFill(Color.WHITE);  // Set text color to white for better visibility
        nameLabel.setAlignment(Pos.CENTER);

        VBox plantBox = new VBox(imageView, button, nameLabel);
        plantBox.setAlignment(Pos.CENTER);
        plantBox.setSpacing(5);

        return plantBox;
    }

    private void animatePlant(Plant plant) {
        javafx.scene.Node node = getNodeByRowColumnIndex(plant.getRow(), plant.getCol(), gardenGrid);
        if (node != null) {
            ScaleTransition st = new ScaleTransition(Duration.millis(500), node);
            st.setByX(1.2);
            st.setByY(1.2);
            st.setCycleCount(2);
            st.setAutoReverse(true);
            st.play();
        }
    }

    private void createGardenGrid() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                StackPane cell = new StackPane();
                cell.setMinSize(50, 50);
                cell.setStyle("-fx-border-color: black; -fx-alignment: center; -fx-background-color: transparent;");
                gardenGrid.add(cell, i, j);

                cell.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> cell.setStyle("-fx-background-color: #E0FFFF; -fx-border-color: black;"));
                cell.addEventFilter(MouseEvent.MOUSE_EXITED, e -> cell.setStyle("-fx-background-color: transparent; -fx-border-color: black;"));
            }
        }
    }

    private void updateGardenGrid() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                StackPane cell = (StackPane) getNodeByRowColumnIndex(i, j, gardenGrid);
                cell.getChildren().clear();
                cell.setStyle("-fx-border-color: black; -fx-alignment: center; -fx-background-color: transparent;");
            }
        }

        for (Plant plant : gardenController.getPlants()) {
            StackPane cell = (StackPane) getNodeByRowColumnIndex(plant.getRow(), plant.getCol(), gardenGrid);
            if (!plant.isDead()) {
                ImageView plantImage = getImageView(plant.getName().toLowerCase() + ".png");
                if (plantImage != null) {
                    plantImage.setFitWidth(40);  // Increase plant image size for better visibility
                    plantImage.setFitHeight(40); // Increase plant image size for better visibility
                    StackPane.setAlignment(plantImage, Pos.TOP_CENTER);
                    cell.getChildren().add(plantImage);
                }
                Tooltip tooltip = new Tooltip(plant.getName());
                Tooltip.install(cell, tooltip);
                cell.setStyle("-fx-border-color: black; -fx-alignment: center; -fx-background-color: lightgreen;");
            } else {
                Label deadLabel = new Label("X");
                deadLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: brown;");
                cell.getChildren().add(deadLabel);
                cell.setStyle("-fx-border-color: black; -fx-alignment: center; -fx-background-color: brown;");
            }
        }

        for (Insect insect : gardenController.getInsects()) {
            StackPane cell = (StackPane) getNodeByRowColumnIndex(insect.getRow(), insect.getCol(), gardenGrid);
            if (cell.getChildren().size() < 2) { // Ensure only one insect at a time
                ImageView insectImage = getImageView(insect.getName().toLowerCase() + ".gif");
                if (insectImage != null) {
                    insectImage.setFitWidth(30);  // Adjust insect image size for better visibility
                    insectImage.setFitHeight(30); // Adjust insect image size for better visibility
                    StackPane.setAlignment(insectImage, Pos.BOTTOM_CENTER);
                    cell.getChildren().add(insectImage);
                    Tooltip tooltip = new Tooltip(insect.getName());
                    Tooltip.install(cell, tooltip);
                    if (insect.isPest()) {
                        cell.setStyle("-fx-border-color: black; -fx-alignment: center; -fx-background-color: red;");
                    } else {
                        cell.setStyle("-fx-border-color: black; -fx-alignment: center; -fx-background-color: blue;");
                    }
                }
            }
        }

        for (Cleaner cleaner : pestControl.getCleaners()) {
            if (cleaner.isBusy()) {
                StackPane cell = (StackPane) getNodeByRowColumnIndex(cleaner.getRow(), cleaner.getCol(), gardenGrid);
                ImageView cleanerImage = getImageView("cleaner.png"); // Ensure cleaner image is in your directory
                if (cleanerImage != null) {
                    cleanerImage.setFitWidth(30);
                    cleanerImage.setFitHeight(30);
                    StackPane.setAlignment(cleanerImage, Pos.CENTER);
                    cell.getChildren().add(cleanerImage);
                }
            }
        }
    }

    private boolean isCellEmpty(int row, int col) {
        StackPane cell = (StackPane) getNodeByRowColumnIndex(row, col, gardenGrid);
        return cell.getChildren().isEmpty();
    }

    private Image getImage(String fileName) {
        String filePath = "src/main/java/com/garden/images/" + fileName;
        try {
            FileInputStream input = new FileInputStream(filePath);
            return new Image(input);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
            return null;
        }
    }

    private ImageView getImageView(String fileName) {
        Image image = getImage(fileName);
        if (image != null) {
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(30);
            imageView.setFitHeight(30);
            return imageView;
        }
        return null;
    }

    private void updateLog() {
        dayLogList.getItems().clear();
        wateringLogList.getItems().clear();
        heatingLogList.getItems().clear();
        insectLogList.getItems().clear();
        cleanerLogList.getItems().clear();

        addLogEntries(dayLogList, gardenController.getLogger().getDayLogEntries(), Color.BLUE);
        addLogEntries(wateringLogList, gardenController.getLogger().getWateringLogEntries(), Color.GREEN);
        addLogEntries(heatingLogList, gardenController.getLogger().getHeatingLogEntries(), Color.ORANGE);
        addLogEntries(insectLogList, gardenController.getLogger().getInsectLogEntries(), Color.RED);
        addLogEntries(cleanerLogList, gardenController.getLogger().getCleanerLogEntries(), Color.PURPLE);
    }


    private void addLogEntries(ListView<TextFlow> logList, List<String> logEntries, Color color) {
        for (String entry : logEntries) {
            String[] parts = entry.split(": ");
            if (parts.length > 1) {
                String timestamp = parts[0];
                String message = parts[1];

                Text timestampText = new Text(timestamp + ": ");
                timestampText.setFill(Color.GRAY);
                timestampText.setFont(Font.font("Arial", FontWeight.BOLD, 14));

                Text messageText = new Text(message + "\n");
                messageText.setFill(color);
                messageText.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

                TextFlow textFlow = new TextFlow(timestampText, messageText);
                textFlow.setPadding(new Insets(5, 10, 5, 10));
                textFlow.setStyle("-fx-background-color: #F0F0F0; -fx-background-radius: 5; -fx-border-color: #D0D0D0; -fx-border-radius: 5; -fx-border-width: 1;");
                logList.getItems().add(textFlow);
            } else {
                Text text = new Text(entry + "\n");
                text.setFill(color);
                text.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
                TextFlow textFlow = new TextFlow(text);
                textFlow.setPadding(new Insets(5, 10, 5, 10));
                textFlow.setStyle("-fx-background-color: #F0F0F0; -fx-background-radius: 5; -fx-border-color: #D0D0D0; -fx-border-radius: 5; -fx-border-width: 1;");
                logList.getItems().add(textFlow);
            }
        }
    }


    private void startSimulation() {
        if (gardenController.getDay() == 0) {
            // Start simulation immediately on day 1
            simulateDay();
        }
        simulationTimeline = new Timeline(new KeyFrame(Duration.hours(1), e -> simulateDay()));
        //simulationTimeline = new Timeline(new KeyFrame(Duration.seconds(4), e -> simulateDay()));
        simulationTimeline.setCycleCount(Timeline.INDEFINITE);
        simulationTimeline.play();
    }


    private void simulateDay() {
        gardenController.simulateDay();
        pestControl.managePests(gardenController.getPlants(), gardenController.getInsects(), gardenController.getLogger(), gardenController.getDay());

        updateLog();
        updateGardenGrid();
        updateWeatherImage();
        updateDirectory();
        //increaseProgress();

        for (Plant plant : gardenController.getPlants()) {
            plant.adjustLifespanForWeather(currentWeather);
        }

    }




    private void startAnimation() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateGardenGrid();
            }
        };
        timer.start();
    }

    private static javafx.scene.Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                return node;
            }
        }
        return null;
    }
    private void updateProgressBars(int temperature) {
        // Update temperature progress bar
        double temperatureProgress = temperature / 100.0; // Assuming temperature ranges from 0 to 100
        temperatureProgressBar.setProgress(temperatureProgress);
        temperatureProgressLabel.setText("Temperature: " + temperature + " F");

        if (temperature <= 50) {
            temperatureProgressBar.setStyle("-fx-accent: blue;");
            temperatureProgressLabel.setTextFill(Color.BLUE);
        } else if (temperature <= 70) {
            temperatureProgressBar.setStyle("-fx-accent: lightblue;");
            temperatureProgressLabel.setTextFill(Color.LIGHTBLUE);
        } else if (temperature <= 90) {
            temperatureProgressBar.setStyle("-fx-accent: orange;");
            temperatureProgressLabel.setTextFill(Color.ORANGE);
        } else {
            temperatureProgressBar.setStyle("-fx-accent: red;");
            temperatureProgressLabel.setTextFill(Color.RED);
        }

        // Update water progress bar based on temperature
        if (temperature <= 50) {
            waterProgressBar.setProgress(0.25);
            waterProgressBar.setStyle("-fx-accent: blue;");
            waterProgressLabel.setText("Water Level: 25%");
            waterProgressLabel.setTextFill(Color.BLUE);
        } else if (temperature <= 70) {
            waterProgressBar.setProgress(0.5);
            waterProgressBar.setStyle("-fx-accent: lightblue;");
            waterProgressLabel.setText("Water Level: 50%");
            waterProgressLabel.setTextFill(Color.LIGHTBLUE);
        } else if (temperature <= 90) {
            waterProgressBar.setProgress(0.75);
            waterProgressBar.setStyle("-fx-accent: orange;");
            waterProgressLabel.setText("Water Level: 75%");
            waterProgressLabel.setTextFill(Color.ORANGE);
        } else {
            waterProgressBar.setProgress(1.0);
            waterProgressBar.setStyle("-fx-accent: red;");
            waterProgressLabel.setText("Water Level: 100%");
            waterProgressLabel.setTextFill(Color.RED);
        }
    }
}
