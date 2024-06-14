package com.garden.Model;

public abstract class Insect {
    private String name;
    private int row;
    private int col;

    public Insect(String name, int row, int col) {
        this.name = name;
        this.row = row;
        this.col = col;
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

    public abstract boolean isPest();
}
