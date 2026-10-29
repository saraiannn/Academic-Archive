package it.fiv.FIVeCafe.control;

import it.fiv.FIVeCafe.entity.BasicBeverage;
import it.fiv.FIVeCafe.entity.Beverage;
import it.fiv.FIVeCafe.entity.BeverageType;
import it.fiv.FIVeCafe.entity.Extra;
import it.fiv.FIVeCafe.entity.decorator.CaramelDecorator;
import it.fiv.FIVeCafe.entity.decorator.CocoaDecorator;
import it.fiv.FIVeCafe.entity.decorator.MilkDecorator;
import it.fiv.FIVeCafe.entity.decorator.SugarDecorator;

import java.util.Set;

public class BeverageFactory {
    private BeverageFactory() {}

    public static Beverage createBeverage(BeverageType type, Set<Extra> extras) {
        // validation: refuse invalid input before building anything

        // 1. a beverage cannot be created without knowing its type
        if(type == null) {
            throw new IllegalArgumentException("beverage type is null");
        }
        // 2. extras must always be provided: pass an empty set for a plain beverage
        if (extras == null) {
            throw new IllegalArgumentException("extras are null");
        }
        // 3. beverages like water and juices do not accept extras
        if (!extras.isEmpty() && !type.allowsExtras()) {
            throw new IllegalArgumentException("extras not allowed for type: " + type);
        }

        Beverage beverage = new BasicBeverage(type);

        for (Extra extra : extras) {
            beverage = decorateBeverage(beverage, extra);
        }
        return beverage;
    }

    private static Beverage decorateBeverage(Beverage beverage, Extra extra) {
        switch(extra) {
            case MILK:
                return new MilkDecorator(beverage);
            case SUGAR:
                return new SugarDecorator(beverage);
            case CARAMEL:
                return new CaramelDecorator(beverage);
            case COCOA:
                return new CocoaDecorator(beverage);
            default:
                throw new IllegalArgumentException("unknown extra: " + extra);
        }
    }
}
