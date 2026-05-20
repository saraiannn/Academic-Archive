package it.fiv.FIVecafe.entity.decorator;

import it.fiv.FIVecafe.entity.Beverage;

public class SugarDecorator extends BeverageDecorator {
    public SugarDecorator(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getBeverageName() {
        return beverage.getBeverageName() + " + sugar";
    }

    @Override
    public double getBeveragePrice() {
        return beverage.getBeveragePrice() + 0.10;
    }
}

