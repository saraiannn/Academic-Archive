package it.fiv.FIVecafe.entity;

public class BasicBeverage implements Beverage {  //(concrete component) basic beverage without extras that implements the Component
    private final String beverageName;
    private final double beveragePrice;

    public BasicBeverage(String beverageName, double beveragePrice) {
        this.beverageName = beverageName;
        this.beveragePrice = beveragePrice;
    }

    @Override
    public String getBeverageName() {  //implements Beverage interface getBeverageName() method
        return beverageName;
    }

    @Override
    public double getBeveragePrice() {  //implements Beverage interface getBeveragePrice() method
        return beveragePrice;
    }

}
