
package com.garden.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PlantDetails {
    private final StringProperty plantName;
    private final IntegerProperty count;
    private final IntegerProperty lifespan;
    private final IntegerProperty pestAttacks;

    public PlantDetails(String plantName, int count, int lifespan, int pestAttacks) {
        this.plantName = new SimpleStringProperty(plantName);
        this.count = new SimpleIntegerProperty(count);
        this.lifespan = new SimpleIntegerProperty(lifespan);
        this.pestAttacks = new SimpleIntegerProperty(pestAttacks);
    }

    public StringProperty plantNameProperty() {
        return plantName;
    }

    public IntegerProperty countProperty() {
        return count;
    }

    public IntegerProperty lifespanProperty() {
        return lifespan;
    }

    public IntegerProperty pestAttacksProperty() {
        return pestAttacks;
    }
}
