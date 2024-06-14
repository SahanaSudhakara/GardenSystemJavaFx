package com.garden;

import java.util.List;

public class HeatingSystem {
    public void heatPlants(List<Plant> plants) {
        for (Plant plant : plants) {
            if (!plant.isDead()) {
                plant.heat();
            }
        }
    }

    public void coolPlants(List<Plant> plants) {
        for (Plant plant : plants) {
            if (!plant.isDead()) {
                plant.cool();
            }
        }
    }
}
