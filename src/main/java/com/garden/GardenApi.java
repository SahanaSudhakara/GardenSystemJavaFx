package com.garden;

import java.util.*;

public class GardenApi {
    private Garden garden;
    private WeatherSimulator weatherSimulator;
    private int currentTemperature;

    public GardenApi() {
        this.garden = new Garden();
        this.weatherSimulator = new WeatherSimulator();
        this.currentTemperature = 100; // Default temperature
    }

    public void initializeGarden() {
        // Initialize the garden with predefined set of plants
        garden.addPlant(new Tomato(0, 0));
        garden.addPlant(new Orange(1, 1));
        garden.addPlant(new Sunflower(2, 2));
    }

    public Map<String, Object> getPlants() {
        Map<String, Object> plantDetails = new HashMap<>();
        List<String> plantNames = new ArrayList<>();
        List<Integer> waterRequirements = new ArrayList<>();
        List<List<String>> parasites = new ArrayList<>();

        for (Plant plant : garden.getPlants()) {
            plantNames.add(plant.getName());
            waterRequirements.add(plant.getWaterRequirement());
            parasites.add(Arrays.asList("Spider", "Caterpillar")); // Example parasites
        }

        plantDetails.put("plants", plantNames);
        plantDetails.put("waterRequirement", waterRequirements);
        plantDetails.put("parasites", parasites);

        return plantDetails;
    }

    public void rain(int amount) {
        for (Plant plant : garden.getPlants()) {
            if (!plant.isDead()) {
                plant.water(amount);
                garden.getLogger().addWateringLogEntry("Day " + garden.getDay() + ": Rained " + amount + " units on plant: " + plant.getName());
            }
        }
    }

    public int temperature(int temp) {
        currentTemperature = temp;
        for (Plant plant : garden.getPlants()) {
            if (!plant.isDead()) {
                if (temp < 60) {
                    plant.cool();
                } else if (temp > 80) {
                    plant.heat();
                }
                garden.getLogger().addHeatingLogEntry("Day " + garden.getDay() + ": Temperature was " + temp + "F for plant: " + plant.getName());
            }
        }
        return temp;
    }

    public void parasite(String parasiteName) {
        for (Plant plant : garden.getPlants()) {
            if (!plant.isDead() && Arrays.asList("Spider", "Caterpillar").contains(parasiteName)) {
                plant.incrementPestAttacks();
                garden.getLogger().addInsectLogEntry("Day " + garden.getDay() + ": Parasite " + parasiteName + " attacked plant: " + plant.getName());
            }
        }
    }

    public void getState() {
        for (Plant plant : garden.getPlants()) {
            if (!plant.isDead()) {
                System.out.println("Plant: " + plant.getName() + ", Lifespan: " + plant.getDaysToLive() + ", Pest Attacks: " + plant.getPestAttacks());
            } else {
                System.out.println("Plant: " + plant.getName() + " has died.");
            }
        }
    }

    public void simulateDay() {
        garden.simulateDay();
        for (Plant plant : garden.getPlants()) {
            plant.decrementDaysToLive(); // Decrement days to live for each plant daily
        }
        // Additional logic for simulation per day can be added here
    }

    public boolean isAllPlantsDead() {
        return garden.getPlants().stream().allMatch(Plant::isDead);
    }

    public int getCurrentTemperature() {
        return currentTemperature;
    }
}
