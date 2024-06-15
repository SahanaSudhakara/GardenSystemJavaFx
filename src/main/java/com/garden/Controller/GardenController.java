package com.garden.Controller;

import com.garden.Helpers.HeatingSystem;
import com.garden.Logger;
import com.garden.Model.Insect;
import com.garden.Model.Plant;
import com.garden.Helpers.WateringSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GardenController {
    private List<Plant> plants;
    private List<Insect> insects;
    private WateringSystem wateringSystem;
    private HeatingSystem heatingSystem;
    private PestControl pestControl;
    private Logger logger;
    private int dayCount;
    private static final String[] WEATHER_TYPES = {"Sunny", "Rainy", "Cold"};

    public GardenController(PestControl pestControl) {
        plants = new ArrayList<>();
        insects = new ArrayList<>();
        wateringSystem = new WateringSystem();
        heatingSystem = new HeatingSystem();
        this.pestControl = pestControl;
        logger = new Logger();
        dayCount = 0;
    }

    public void addPlant(Plant plant) {
        plants.add(plant);
        logger.addDayLogEntry("Added plant: " + plant.getName() + " at grid (" + plant.getRow() + "," + plant.getCol() + ")");
    }

    public void simulateDay() {
        dayCount++;
        logger.addDayLogEntry("Day " + dayCount + " simulation started.");
        String weather = getCurrentWeather();
        logger.addDayLogEntry("Weather: " + weather);

        for (Plant plant : plants) {
            if (!plant.isDead()) {
                plant.decrementDaysToLive();
                switch (weather) {
                    case "Rainy":
                        wateringSystem.waterPlants(plants, 10); // Water less if rainy
                        logger.addWateringLogEntry("Day " + dayCount + ": Watering plant: " + plant.getName() + " at grid (" + plant.getRow() + "," + plant.getCol() + ") (rainy day)");
                        break;
                    case "Cold":
                        heatingSystem.increaseTemperature(plants);
                        logger.addHeatingLogEntry("Day " + dayCount + ": Heating plant: " + plant.getName() + " at grid (" + plant.getRow() + "," + plant.getCol() + ")");
                        wateringSystem.waterPlants(plants, 20);
                        logger.addWateringLogEntry("Day " + dayCount + ": Watering plant: " + plant.getName() + " at grid (" + plant.getRow() + "," + plant.getCol() + ")");
                        break;
                    case "Sunny":
                        heatingSystem.decreaseTemperature(plants);
                        logger.addHeatingLogEntry("Day " + dayCount + ": Cooling plant: " + plant.getName() + " at grid (" + plant.getRow() + "," + plant.getCol() + ") (sunny day)");
                        wateringSystem.waterPlants(plants, 20);
                        logger.addWateringLogEntry("Day " + dayCount + ": Watering plant: " + plant.getName() + " at grid (" + plant.getRow() + "," + plant.getCol() + ") (sunny day)");
                        break;
                    default:
                        wateringSystem.waterPlants(plants, 20);
                        logger.addWateringLogEntry("Day " + dayCount + ": Watering plant: " + plant.getName() + " at grid (" + plant.getRow() + "," + plant.getCol() + ")");
                }
            }
        }

        insects.clear();
        pestControl.managePests(plants, insects, logger, dayCount);

        logger.addDayLogEntry("Day " + dayCount + " simulation completed.");
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public List<Insect> getInsects() {
        return insects;
    }

    public Logger getLogger() {
        return logger;
    }

    public int getDay() {
        return dayCount;
    }

    public String getCurrentWeather() {
        Random random = new Random();
        return WEATHER_TYPES[random.nextInt(WEATHER_TYPES.length)];
    }
}
