package com.garden.Model;

public class BeneficialInsect extends Insect {
    public BeneficialInsect(String name, int row, int col) {
        super(name, row, col);
    }

    @Override
    public boolean isPest() {
        return false;
    }
}
