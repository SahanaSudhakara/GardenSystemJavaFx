package com.garden.Helpers;

import com.garden.Model.Plant;

import java.util.List;

public class HeatingSystem {
    public void increaseTemperature(List<Plant> plants) {
        for (Plant plant : plants) {
            if (!plant.isDead()) {
                plant.heat();
            }
        }
    }

    public void decreaseTemperature(List<Plant> plants) {
        for (Plant plant : plants) {
            if (!plant.isDead()) {
                plant.cool();
            }
        }
    }
}
