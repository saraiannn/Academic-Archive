package it.fiv.FIVeCafe.entity;

public class BasicBeverage implements Beverage {
    private final BeverageType type;

    public BasicBeverage(BeverageType type) {
        this.type = type;
    }

    @Override
    public String getBeverageName() {
        return type.getBeverageName();
    }

    @Override
    public double getBeveragePrice() {
        return type.getBeveragePrice();
    }

    @Override
    public String toString() {
        return type.toString();
    }

}
