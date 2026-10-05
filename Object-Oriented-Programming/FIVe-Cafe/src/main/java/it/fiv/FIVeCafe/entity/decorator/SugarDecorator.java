package it.fiv.FIVeCafe.entity.decorator;

import it.fiv.FIVeCafe.entity.Beverage;

public class SugarDecorator extends BeverageDecorator {
    private static final double PRICE = 0.10;

    public SugarDecorator(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getBeverageName() {
        return beverage.getBeverageName() + " + sugar";
    }

    @Override
    public double getBeveragePrice() {
        return beverage.getBeveragePrice() + PRICE;
    }
}
