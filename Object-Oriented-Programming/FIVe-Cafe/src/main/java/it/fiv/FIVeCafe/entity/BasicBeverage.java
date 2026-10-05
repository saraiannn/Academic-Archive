package it.fiv.FIVeCafe.entity;

public class BasicBeverage implements Beverage {
    private final String beverageName;
    private final double beveragePrice;

    public BasicBeverage(String beverageName, double beveragePrice) {
        this.beverageName = beverageName;
        this.beveragePrice = beveragePrice;
    }

    @Override
    public String getBeverageName() {
        return beverageName;
    }

    @Override
    public double getBeveragePrice() {
        return beveragePrice;
    }
}
