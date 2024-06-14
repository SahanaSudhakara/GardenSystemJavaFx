package com.garden;

import java.util.Random;

public class WeatherSimulator {
    private static final String[] WEATHER_TYPES = {"Sunny", "Rainy", "Cold"};

    public String getCurrentWeather() {
        Random random = new Random();
        return WEATHER_TYPES[random.nextInt(WEATHER_TYPES.length)];
    }
}
