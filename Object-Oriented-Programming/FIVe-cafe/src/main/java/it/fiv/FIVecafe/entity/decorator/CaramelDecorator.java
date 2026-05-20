package it.fiv.FIVecafe.entity.decorator;

import it.fiv.FIVecafe.entity.Beverage;

public class CaramelDecorator extends BeverageDecorator {
    public CaramelDecorator(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getBeverageName() {
        return beverage.getBeverageName() + " + caramel";
    }

    @Override
    public double getBeveragePrice() {
        return beverage.getBeveragePrice() + 0.60;
    }
}

