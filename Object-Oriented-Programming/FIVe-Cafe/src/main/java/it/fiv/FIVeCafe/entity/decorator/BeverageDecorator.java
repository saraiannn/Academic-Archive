package it.fiv.FIVeCafe.entity.decorator;

import it.fiv.FIVeCafe.entity.Beverage;

public abstract class BeverageDecorator implements Beverage {
    protected Beverage wrappedBeverage;

    protected BeverageDecorator(Beverage wrappedBeverage) {
        this.wrappedBeverage = wrappedBeverage;
    }

    @Override
    public String getBeverageName() {
        return wrappedBeverage.getBeverageName();
    }

    @Override
    public double getBeveragePrice() {
        return wrappedBeverage.getBeveragePrice();
    }
}
