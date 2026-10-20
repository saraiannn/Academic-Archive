package it.fiv.FIVeCafe.entity.decorator;

import it.fiv.FIVeCafe.entity.Beverage;

public class SugarDecorator extends BeverageDecorator {
    public SugarDecorator(Beverage wrappedBeverage) {
        super(wrappedBeverage);
    }

    @Override
    public String getBeverageName() {
        return wrappedBeverage.getBeverageName() + " + sugar";
    }

    @Override
    public double getBeveragePrice() {
        return wrappedBeverage.getBeveragePrice() + 0.10;
    }
}
