package com.garden;

import java.util.List;
import java.util.Random;

public class PestControl {
    private static final String[] PESTS = {"Spider", "Caterpillar"};
    private static final String[] BENEFICIAL_INSECTS = {"Beetle", "Butterfly"};
    private List<Cleaner> cleaners;

    public PestControl() {
        cleaners = List.of(new Cleaner(), new Cleaner(), new Cleaner()); // Initialize with 3 cleaners
    }

    public List<Cleaner> getCleaners() {
        return cleaners;
    }

    public void managePests(List<Plant> plants, List<Insect> insects, Logger logger, int dayCount) {
        Random random = new Random();
        for (Plant plant : plants) {
            if (plant.isDead()) continue;
            if (random.nextBoolean()) {
                String pestName = PESTS[random.nextInt(PESTS.length)];
                Insect pest = new Pest(pestName, plant.getRow(), plant.getCol());
                if (!isInsectPresent(plant, insects)) {
                    insects.add(pest);
                    plant.incrementPestAttacks();
                    logger.addInsectLogEntry("Day " + dayCount + ": Pest attack by " + pestName + " on plant: " + plant.getName());
                }
            } else {
                String insectName = BENEFICIAL_INSECTS[random.nextInt(BENEFICIAL_INSECTS.length)];
                Insect beneficialInsect = new BeneficialInsect(insectName, plant.getRow(), plant.getCol());
                if (!isInsectPresent(plant, insects)) {
                    insects.add(beneficialInsect);
                    logger.addInsectLogEntry("Day " + dayCount + ": Beneficial insect " + insectName + " found near plant: " + plant.getName());
                }
            }

            // Check if any cleaner can visit the plant
            if (plant.getPestAttacks() >= 10 && !plant.isDead()) {
                Cleaner availableCleaner = cleaners.stream().filter(cleaner -> !cleaner.isBusy()).findFirst().orElse(null);
                if (availableCleaner != null) {
                    availableCleaner.visitPlant(plant);
                    logger.addCleanerLogEntry("Day " + dayCount + ": Cleaner visiting plant: " + plant.getName());
                }
            }
        }

        // Finish cleaner visits
        for (Cleaner cleaner : cleaners) {
            if (cleaner.isBusy()) {
                cleaner.finishVisit();
            }
        }
    }

    private boolean isInsectPresent(Plant plant, List<Insect> insects) {
        for (Insect insect : insects) {
            if (insect.getRow() == plant.getRow() && insect.getCol() == plant.getCol()) {
                return true;
            }
        }
        return false;
    }
}
