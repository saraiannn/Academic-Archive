package it.fiv.FIVeCafe.entity.decorator;

import it.fiv.FIVeCafe.entity.Beverage;

public class CocoaDecorator extends BeverageDecorator {
    public CocoaDecorator(Beverage wrappedbeverage) {
        super(wrappedbeverage);
    }

    @Override
    public String getBeverageName() {
        return wrappedBeverage.getBeverageName() + " + cocoa";
    }

    @Override
    public double getBeveragePrice() {
        return wrappedBeverage.getBeveragePrice() + 0.40;
    }
}
