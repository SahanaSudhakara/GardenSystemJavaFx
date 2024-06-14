package com.garden;

import java.util.ArrayList;
import java.util.List;

public class Garden {
    private List<Plant> plants;
    private List<Insect> insects;
    private WateringSystem wateringSystem;
    private HeatingSystem heatingSystem;
    private PestControl pestControl;
    private WeatherSimulator weatherSimulator;
    private Logger logger;
    private int dayCount;

    public Garden() {
        plants = new ArrayList<>();
        insects = new ArrayList<>();
        wateringSystem = new WateringSystem();
        heatingSystem = new HeatingSystem();
        pestControl = new PestControl();
        weatherSimulator = new WeatherSimulator();
        logger = new Logger();
        dayCount = 0;
    }

    public void addPlant(Plant plant) {
        plants.add(plant);
        logger.addDayLogEntry("Added plant: " + plant.getName());
    }

    public void simulateDay() {
        dayCount++;
        logger.addDayLogEntry("Day " + dayCount + " simulation started.");
        String weather = weatherSimulator.getCurrentWeather();
        logger.addDayLogEntry("Weather: " + weather);

        for (Plant plant : plants) {
            if (!plant.isDead()) {
                plant.decrementDaysToLive();
                switch (weather) {
                    case "Rainy":
                        wateringSystem.waterPlants(plants, 10); // Water less if rainy
                        logger.addWateringLogEntry("Day " + dayCount + ": Watering plant: " + plant.getName() + " with 10 units of water (rainy day)");
                        break;
                    case "Cold":
                        heatingSystem.heatPlants(plants);
                        logger.addHeatingLogEntry("Day " + dayCount + ": Heating plant: " + plant.getName());
                        logger.addWateringLogEntry("Day " + dayCount + ": Watering plant: " + plant.getName() + " with 20 units of water");
                        break;
                    case "Sunny":
                        heatingSystem.coolPlants(plants);
                        logger.addHeatingLogEntry("Day " + dayCount + ": Cooling plant: " + plant.getName() + " (sunny day)");
                        wateringSystem.waterPlants(plants, 20);
                        logger.addWateringLogEntry("Day " + dayCount + ": Watering plant: " + plant.getName() + " with 20 units of water (sunny day)");
                        break;
                    default:
                        wateringSystem.waterPlants(plants, 20);
                        logger.addWateringLogEntry("Day " + dayCount + ": Watering plant: " + plant.getName() + " with 20 units of water");
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

}
