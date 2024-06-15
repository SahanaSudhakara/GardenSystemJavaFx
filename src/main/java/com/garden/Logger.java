package com.garden;

import java.util.ArrayList;
import java.util.List;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Logger {
    private List<String> dayLogEntries;
    private List<String> wateringLogEntries;
    private List<String> heatingLogEntries;
    private List<String> insectLogEntries;
    private List<String> cleanerLogEntries;
    private static final String LOG_FILE_NAME = "garden_logs.txt";

    public Logger() {
        dayLogEntries = new ArrayList<>();
        wateringLogEntries = new ArrayList<>();
        heatingLogEntries = new ArrayList<>();
        insectLogEntries = new ArrayList<>();
        cleanerLogEntries = new ArrayList<>();
    }

    public void addDayLogEntry(String entry) {
        dayLogEntries.add(entry);
        saveLogToFile("Day Log: " + entry);
    }

    public void addWateringLogEntry(String entry) {
        wateringLogEntries.add(entry);
        saveLogToFile("Watering Log: " + entry);
    }

    public void addHeatingLogEntry(String entry) {
        heatingLogEntries.add(entry);
        saveLogToFile("Heating Log: " + entry);
    }

    public void addInsectLogEntry(String entry) {
        insectLogEntries.add(entry);
        saveLogToFile("Insect Log: " + entry);
    }

    public void addCleanerLogEntry(String entry) {
        cleanerLogEntries.add(entry);
        saveLogToFile("Cleaner Log: " + entry);
    }

    public List<String> getDayLogEntries() {
        return dayLogEntries;
    }

    public List<String> getWateringLogEntries() {
        return wateringLogEntries;
    }

    public List<String> getHeatingLogEntries() {
        return heatingLogEntries;
    }

    public List<String> getInsectLogEntries() {
        return insectLogEntries;
    }

    public List<String> getCleanerLogEntries() {
        return cleanerLogEntries;
    }

    private void saveLogToFile(String entry) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_NAME, true))) {
            writer.write(entry);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}
