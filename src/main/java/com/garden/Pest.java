package com.garden;

public class Pest extends Insect {
    public Pest(String name, int row, int col) {
        super(name, row, col);
    }

    @Override
    public boolean isPest() {
        return true;
    }
}
