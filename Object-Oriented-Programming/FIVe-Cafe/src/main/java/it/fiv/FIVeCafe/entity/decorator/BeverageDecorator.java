package it.fiv.FIVeCafe.entity.decorator;

import it.fiv.FIVeCafe.entity.Beverage;

public abstract class BeverageDecorator implements Beverage {  //this class exists just to be extended by each extra
    protected final Beverage beverage;  //wrapped beverage

    protected BeverageDecorator(Beverage beverage) {
        this.beverage = beverage;
    }
}
