package it.fiv.FIVeCafe.entity.decorator;

import it.fiv.FIVeCafe.entity.Beverage;

public class CaramelDecorator extends BeverageDecorator {
    public CaramelDecorator(Beverage wrappedBeverage) {
        super(wrappedBeverage);
    }

    @Override
    public String getBeverageName() {
        return wrappedBeverage.getBeverageName() + " + caramel";
    }

    @Override
    public double getBeveragePrice() {
        return wrappedBeverage.getBeveragePrice() + 0.60;
    }

}
