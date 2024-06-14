package com.garden;

public abstract class Plant {
    private String name;
    private int daysToLive;
    private int maxLifespan; // Maximum lifespan in days
    private int waterRequirement;
    private int row;
    private int col;
    private int pestAttacks;
    private boolean isDead;

    public Plant(String name, int maxLifespan, int waterRequirement, int row, int col) {
        this.name = name;
        this.maxLifespan = maxLifespan;
        this.daysToLive = maxLifespan; // Initialize with maximum lifespan
        this.waterRequirement = waterRequirement;
        this.row = row;
        this.col = col;
        this.pestAttacks = 0;
        this.isDead = false;
    }

    public String getName() {
        return name;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isDead() {
        return isDead;
    }

    public int getDaysToLive() {
        return daysToLive;
    }

    public int getWaterRequirement() {
        return waterRequirement;
    }

    public int getPestAttacks() {
        return pestAttacks;
    }

    public void decrementDaysToLive() {
        if (daysToLive > 0) {
            daysToLive--;
        }
        if (daysToLive <= 0) {
            isDead = true;
        }
    }

    public void incrementPestAttacks() {
        pestAttacks++;
        if (pestAttacks >= 12) {  // Plant dies after 12 pest attacks
            isDead = true;
        }
    }

    public void reducePestAttacks(int amount) {
        pestAttacks = Math.max(pestAttacks - amount, 0);  // Reduces pest attacks by the given amount
    }

    public void water(int amount) {
        if (!isDead && amount >= waterRequirement) {
            daysToLive = Math.min(daysToLive + 1, maxLifespan);  // Increase lifespan by 1 day if properly watered, up to maxLifespan
        }
    }

    public void adjustLifespanForWeather(String weather) {
        if (!isDead) {
            switch (weather) {
                case "Sunny":
                    // No additional days for sunny weather
                    break;
                case "Rainy":
                    daysToLive = Math.min(daysToLive + 1, maxLifespan);  // Rainy days are beneficial, increase lifespan by 1, up to maxLifespan
                    break;
                case "Cold":
                    daysToLive -= 2;  // Cold days are harmful, decrease lifespan by 2
                    if (daysToLive <= 0) {
                        isDead = true;
                    }
                    break;
            }
        }
    }

    public void heat() {
        if (!isDead) {
            daysToLive = Math.min(daysToLive + 1, maxLifespan);
        }
    }

    public void cool() {
        if (!isDead) {
            daysToLive = Math.min(daysToLive + 1, maxLifespan);
        }
    }
}
