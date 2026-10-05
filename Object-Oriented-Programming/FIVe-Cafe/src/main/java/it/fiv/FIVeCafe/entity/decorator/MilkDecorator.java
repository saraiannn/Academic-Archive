package it.fiv.FIVeCafe.entity.decorator;

import it.fiv.FIVeCafe.entity.Beverage;

public class MilkDecorator extends BeverageDecorator {
    private static final double PRICE = 0.30;

    public MilkDecorator(Beverage beverage) {  //beverage field is inherited by BeverageDecorator superclass
        super(beverage);
    }

    @Override
    public String getBeverageName() {
        return beverage.getBeverageName() + " + milk";
    }

    @Override
    public double getBeveragePrice() {
        return beverage.getBeveragePrice() + PRICE;
    }
}
