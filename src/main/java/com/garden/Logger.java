package com.garden;

import java.util.ArrayList;
import java.util.List;

public class Logger {
    private List<String> dayLogEntries;
    private List<String> wateringLogEntries;
    private List<String> heatingLogEntries;
    private List<String> insectLogEntries;
    private List<String> cleanerLogEntries;

    public Logger() {
        dayLogEntries = new ArrayList<>();
        wateringLogEntries = new ArrayList<>();
        heatingLogEntries = new ArrayList<>();
        insectLogEntries = new ArrayList<>();
        cleanerLogEntries = new ArrayList<>();
    }

    public void addDayLogEntry(String entry) {
        dayLogEntries.add(entry);
    }

    public void addWateringLogEntry(String entry) {
        wateringLogEntries.add(entry);
    }

    public void addHeatingLogEntry(String entry) {
        heatingLogEntries.add(entry);
    }

    public void addInsectLogEntry(String entry) {
        insectLogEntries.add(entry);
    }

    public void addCleanerLogEntry(String entry) {
        cleanerLogEntries.add(entry);
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
}
