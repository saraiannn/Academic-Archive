package it.fiv.FIVecafe.entity;

public enum BeverageType {

    // BASIC COFFEE
    ESPRESSO("Espresso", 1.20, true),
    AMERICANO("Americano", 1.40, true),
    CAPPUCCINO("Cappuccino", 1.80, true),
    MACCHIATO("Macchiato", 1.30, true),
    FLAT_WHITE("Flat White", 2.00, true),
    LATTE("Latte", 2.10, true),
    DOUBLE_ESPRESSO("Double Espresso", 1.70, true),

    // COLD COFFEE
    ICED_LATTE("Iced Latte", 2.00, true),
    COLD_BREW("Cold Brew", 2.20, true),
    ICED_AMERICANO("Iced Americano", 2.30, true),
    ICED_MOCHA("Iced Mocha", 2.70, true),
    ICED_CAPPUCCINO("Iced Cappuccino", 2.40, true),
    ESPRESSO_TONIC("Espresso Tonic", 2.60, true),
    ICED_CHOCOLATE_LATTE("Iced Chocolate Latte", 2.80, true),

    // TEA & NON COFFEE
    GREEN_TEA("Green Tea", 1.20, true),
    HOT_CHOCOLATE("Hot Chocolate", 2.50, true),
    BLACK_TEA("Black Tea", 1.20, true),
    CHAI_LATTE("Chai Latte", 2.40, true),
    HERBAL_TEA("Herbal Tea", 1.30, true),
    MATCHA_LATTE("Matcha Latte", 2.90, true),
    GOLDEN_MILK("Golden Milk", 2.80, true),

    // SWEET DRINKS
    VANILLA_LATTE("Vanilla Latte", 2.40, true),
    CARAMEL_LATTE("Caramel Latte", 2.50, true),
    HAZELNUT_LATTE("Hazelnut Latte", 2.90, true),
    MOCHA("Mocha", 2.70, true),
    WHITE_MOCHA("White Mocha", 2.80, true),
    CINNAMON_LATTE("Cinnamon Latte", 2.40, true),

    // REFRESHERS
    LEMONADE("Lemonade", 2.00, false),
    SPARKLING_LEMONADE("Sparkling Lemonade", 2.20, false),
    PEACH_ICED_TEA("Peach Iced Tea", 2.30, false),
    LEMON_ICED_TEA("Lemon Iced Tea", 2.20, false),
    ORANGE_JUICE("Orange Juice", 2.50, false),
    SPARKLING_WATER("Sparkling Water", 1.50, false),
    STILL_WATER("Still Water", 1.20, false),

    //SEASONAL SPECIALS
    PUMPKIN_SPICE_LATTE("Pumpkin Spice Latte", 4.50, true),
    GINGERBREAD_LATTE("Gingerbread Latte", 4.40, true),
    PEPPERMINT_MOCHA("Peppermint Mocha", 4.70, true),
    SALTED_CARAMEL_MOCHA("Salted Caramel Mocha", 4.80, true),
    AFFOGATO("Affogato", 3.80, true);

    private final String displayName;
    private final double price;
    private final boolean allowsExtras;

    BeverageType(String displayName, double price, boolean allowsExtras) {
        this.displayName = displayName;
        this.price = price;
        this.allowsExtras = allowsExtras;
    }

    public boolean allowsExtras() {
        return allowsExtras;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getPrice() {
        return price;
    }

    public static BeverageType fromDisplayName(String name) {
        for (BeverageType type : values()) {
            if (type.displayName.equals(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown beverage: " + name);
    }
}
