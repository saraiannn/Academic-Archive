package it.fiv.FIVeCafe.entity.decorator;

import it.fiv.FIVeCafe.entity.Beverage;

public class CocoaDecorator extends BeverageDecorator {
    private static final double PRICE = 0.40;

    public CocoaDecorator(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getBeverageName() {
        return beverage.getBeverageName() + " + cocoa";
    }

    @Override
    public double getBeveragePrice() {
        return beverage.getBeveragePrice() + PRICE;
    }
}
