package it.fiv.FIVeCafe.entity.decorator;

import it.fiv.FIVeCafe.entity.Beverage;

public class MilkDecorator extends BeverageDecorator {
    public MilkDecorator(Beverage wrappedBeverage) {
        super(wrappedBeverage);
    }

    @Override
    public String getBeverageName() {
        return wrappedBeverage.getBeverageName() + " + milk";
    }

    @Override
    public double getBeveragePrice() {
        return wrappedBeverage.getBeveragePrice() + 0.20;
    }
}
