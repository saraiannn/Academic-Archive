package it.fiv.FIVecafe.control;

import it.fiv.FIVecafe.entity.BasicBeverage;
import it.fiv.FIVecafe.entity.Beverage;
import it.fiv.FIVecafe.entity.BeverageType;

public class BeverageFactory {

    private BeverageFactory() {  //utility clas (states that the given class won't be instantiated)
    }

    public static Beverage createBeverage(BeverageType type) {
        if (type == null) {
            throw new IllegalArgumentException("BeverageType cannot be null");
        }

        return new BasicBeverage(
                type.getDisplayName(),
                type.getPrice()
        );
    }
}

