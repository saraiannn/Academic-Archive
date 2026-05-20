package it.fiv.FIVecafe.entity.decorator;

import it.fiv.FIVecafe.entity.Beverage;

public class CocoaDecorator extends BeverageDecorator {
    public CocoaDecorator(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getBeverageName() {
        return beverage.getBeverageName() + " + cocoa";
    }

    @Override
    public double getBeveragePrice() {
        return beverage.getBeveragePrice() + 0.40;
    }
}
