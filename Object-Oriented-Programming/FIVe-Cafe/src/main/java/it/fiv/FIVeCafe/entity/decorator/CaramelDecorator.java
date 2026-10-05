package it.fiv.FIVeCafe.entity.decorator;

import it.fiv.FIVeCafe.entity.Beverage;

public class CaramelDecorator extends BeverageDecorator {
    private static final double PRICE = 0.60;

    public CaramelDecorator(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getBeverageName() {
        return beverage.getBeverageName() + " + caramel";
    }

    @Override
    public double getBeveragePrice() {
        return beverage.getBeveragePrice() + PRICE;
    }
}
