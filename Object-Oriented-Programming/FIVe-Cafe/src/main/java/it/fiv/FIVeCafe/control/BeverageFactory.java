package it.fiv.FIVeCafe.control;

import it.fiv.FIVeCafe.entity.BasicBeverage;
import it.fiv.FIVeCafe.entity.Beverage;
import it.fiv.FIVeCafe.entity.BeverageType;
import it.fiv.FIVeCafe.entity.Extra;
import it.fiv.FIVeCafe.entity.decorator.CaramelDecorator;
import it.fiv.FIVeCafe.entity.decorator.CocoaDecorator;
import it.fiv.FIVeCafe.entity.decorator.MilkDecorator;
import it.fiv.FIVeCafe.entity.decorator.SugarDecorator;

import java.util.Objects;
import java.util.Set;

public final class BeverageFactory {

    private BeverageFactory() {
        //utility class: provides methods that are accessible all across the application without instantiating the class
    }

    //plain beverage, without extras
    /**/public static Beverage createBeverage(BeverageType type) {
        return createBeverage(type, Set.of());
    }

    //a beverage wrapped in one decorator for each requested extra
    /**/public static Beverage createBeverage(BeverageType type, Set<Extra> extras) {
        Objects.requireNonNull(type, "BeverageType cannot be null");
        Objects.requireNonNull(extras, "Extras cannot be null");

        if(!extras.isEmpty() && !type.allowsExtras()) {
            throw new IllegalArgumentException(type.getDisplayName() + " does not allow extras");
        }

        Beverage beverage = new BasicBeverage(type.getDisplayName(), type.getPrice());

        for (Extra extra : Extra.values()) {
            if(extras.contains(extra)) {
                beverage = decorate(beverage, extra);
            }
        }
        return beverage;
    }

    private static Beverage decorate(Beverage beverage, Extra extra) {
        return switch(extra) {
            case MILK -> new MilkDecorator(beverage);
            case SUGAR -> new SugarDecorator(beverage);
            case CARAMEL -> new CaramelDecorator(beverage);
            case COCOA -> new CocoaDecorator(beverage);
        };
    }
}
