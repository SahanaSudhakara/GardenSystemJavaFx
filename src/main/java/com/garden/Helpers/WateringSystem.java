package com.garden.Helpers;

import com.garden.Model.Plant;

import java.util.List;

public class WateringSystem {
    public void waterPlants(List<Plant> plants, int waterAmount) {
        for (Plant plant : plants) {
            if (!plant.isDead()) {
                plant.water(waterAmount);
            }
        }
    }
}
