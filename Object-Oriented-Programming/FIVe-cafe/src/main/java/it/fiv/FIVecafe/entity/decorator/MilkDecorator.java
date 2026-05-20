package it.fiv.FIVecafe.entity.decorator;

import it.fiv.FIVecafe.entity.Beverage;

public class MilkDecorator extends BeverageDecorator {  //(concrete decorator) adds "milk" functionality
    public MilkDecorator(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getBeverageName() {
        return beverage.getBeverageName() + " + milk";
    }

    @Override
    public double getBeveragePrice() {
        return beverage.getBeveragePrice() + 0.30;
    }
}

