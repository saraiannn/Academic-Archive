package it.fiv.FIVeCafe.entity;

import java.util.ArrayList;
import java.util.List;

import static it.fiv.FIVeCafe.entity.BeverageCategory.*;

public enum BeverageType {  //all values are created when the program gets executed
    // BASIC COFFEE
    ESPRESSO("Espresso", BASIC_COFFEE, 1.20, true,
            "A short and intense coffee made with hot water under pressure. Strong flavor and rich aroma."),
    AMERICANO("Americano", BASIC_COFFEE, 1.40, true,
            "An espresso diluted with hot water. Lighter and smoother, perfect to sip slowly."),
    CAPPUCCINO("Cappuccino", BASIC_COFFEE, 1.80, true,
            "Espresso with steamed milk and milk foam. Creamy, balanced, and very popular."),
    MACCHIATO("Macchiato", BASIC_COFFEE, 1.30, true,
            "Espresso 'stained' with a small amount of steamed milk or foam. Strong coffee taste with a softer touch."),
    FLAT_WHITE("Flat White", BASIC_COFFEE, 2.00, true,
            "A smooth coffee made with espresso and finely textured steamed milk. Creamy and balanced."),
    LATTE("Latte", BASIC_COFFEE, 2.10, true,
            "Espresso with plenty of steamed milk and a light layer of foam. Mild and creamy."),
    DOUBLE_ESPRESSO("Double Espresso", BASIC_COFFEE, 1.70, true,
            "A stronger version of espresso made with double the coffee. Bold, intense, and full-bodied."),

    // COLD COFFEE
    ICED_LATTE("Iced Latte", COLD_COFFEE, 2.00, true,
            "Cold milk with espresso and ice. Refreshing and creamy, ideal for hot days."),
    COLD_BREW("Cold Brew", COLD_COFFEE, 2.20, true,
            "Coffee brewed cold over several hours. Smooth, less acidic, and highly aromatic."),
    ICED_AMERICANO("Iced Americano", COLD_COFFEE, 2.30, true,
            "Espresso diluted with cold water and ice. Light and refreshing with a classic coffee taste."),
    ICED_MOCHA("Iced Mocha", COLD_COFFEE, 2.70, true,
            "Iced coffee with milk and chocolate. Sweet, rich, and creamy, perfect for chocolate lovers."),
    ICED_CAPPUCCINO("Iced Cappuccino", COLD_COFFEE, 2.40, true,
            "Espresso served cold with milk and foam over ice. Refreshing and creamy with a classic taste."),
    ESPRESSO_TONIC("Espresso Tonic", COLD_COFFEE, 2.60, true,
            "Espresso poured over ice and tonic water. Fresh, slightly bitter, and very aromatic."),
    ICED_CHOCOLATE_LATTE("Iced Chocolate Latte", COLD_COFFEE, 2.80, true,
            "Cold milk with espresso and chocolate over ice. Sweet, rich, and refreshing."),

    // TEA & NON COFFEE
    GREEN_TEA("Green Tea", TEA_AND_NON_COFFEE, 1.20, true,
            "Delicate green tea with a light, slightly herbal flavor. A refreshing and light beverage."),
    HOT_CHOCOLATE("Hot Chocolate", TEA_AND_NON_COFFEE, 2.50, true,
            "Thick and creamy hot chocolate. Sweet, comforting, and perfect on cold days."),
    BLACK_TEA("Black Tea", TEA_AND_NON_COFFEE, 1.20, true,
            "Strong and full-bodied black tea. Can be enjoyed plain or with milk and sugar."),
    CHAI_LATTE("Chai Latte", TEA_AND_NON_COFFEE, 2.40, true,
            "Spiced black tea mixed with steamed milk. Warm, aromatic, and slightly sweet."),
    HERBAL_TEA("Herbal Tea", TEA_AND_NON_COFFEE, 1.30, true,
            "A caffeine-free infusion made from herbs and flowers. Light, relaxing, and soothing."),
    MATCHA_LATTE("Matcha Latte", TEA_AND_NON_COFFEE, 2.90, true,
            "Japanese green tea powder blended with milk. Creamy, earthy, and energizing."),
    GOLDEN_MILK("Golden Milk", TEA_AND_NON_COFFEE, 2.80, true,
            "Warm milk with turmeric and spices. Comforting, aromatic, and slightly spicy."),

    // SWEET DRINKS
    VANILLA_LATTE("Vanilla Latte", SWEET_DRINKS, 2.40, true,
            "Espresso with steamed milk and vanilla syrup. Sweet, smooth, and fragrant."),
    CARAMEL_LATTE("Caramel Latte", SWEET_DRINKS, 2.50, true,
            "Espresso with milk and caramel syrup. Rich, sweet, and indulgent."),
    HAZELNUT_LATTE("Hazelnut Latte", SWEET_DRINKS, 2.90, true,
            "Espresso with milk and hazelnut flavor. Nutty, smooth, and aromatic."),
    MOCHA("Mocha", SWEET_DRINKS, 2.70, true,
            "Espresso with milk and chocolate. A perfect balance between coffee and chocolate."),
    WHITE_MOCHA("White Mocha", SWEET_DRINKS, 2.80, true,
            "Espresso with milk and white chocolate. Sweet, creamy, and velvety."),
    CINNAMON_LATTE("Cinnamon Latte", SWEET_DRINKS, 2.40, true,
            "Espresso with milk and cinnamon flavor. Warm, spicy, and comforting."),

    // REFRESHERS (no extras)
    LEMONADE("Lemonade", REFRESHERS, 2.00, false,
            "Fresh lemon juice mixed with water and sugar. Crisp, refreshing, and slightly sweet."),
    SPARKLING_LEMONADE("Sparkling Lemonade", REFRESHERS, 2.20, false,
            "Lemonade with sparkling water. Bubbly, fresh, and refreshing."),
    PEACH_ICED_TEA("Peach Iced Tea", REFRESHERS, 2.30, false,
            "Black tea served cold with peach flavor. Sweet, fruity, and refreshing."),
    LEMON_ICED_TEA("Lemon Iced Tea", REFRESHERS, 2.20, false,
            "Cold black tea with lemon. Light, fresh, and thirst-quenching."),
    ORANGE_JUICE("Orange Juice", REFRESHERS, 2.50, false,
            "Fresh orange juice. Naturally sweet and rich in flavor."),
    SPARKLING_WATER("Sparkling Water", REFRESHERS, 1.50, false,
            "Carbonated mineral water. Clean, refreshing, and light."),
    STILL_WATER("Still Water", REFRESHERS, 1.20, false,
            "Natural still mineral water. Pure, neutral, and refreshing."),

    // SEASONAL SPECIALS
    PUMPKIN_SPICE_LATTE("Pumpkin Spice Latte", SEASONAL_SPECIALS, 4.50, true,
            "Espresso with milk and pumpkin spice flavors. Warm, sweet, and seasonal."),
    GINGERBREAD_LATTE("Gingerbread Latte", SEASONAL_SPECIALS, 4.40, true,
            "Espresso with milk and gingerbread spices. Festive, spicy, and comforting."),
    PEPPERMINT_MOCHA("Peppermint Mocha", SEASONAL_SPECIALS, 4.70, true,
            "Espresso with chocolate and peppermint flavor. Fresh, sweet, and rich."),
    SALTED_CARAMEL_MOCHA("Salted Caramel Mocha", SEASONAL_SPECIALS, 4.80, true,
            "Chocolate coffee with caramel and a hint of salt. Sweet, rich, and balanced."),
    AFFOGATO("Affogato", SEASONAL_SPECIALS, 3.80, true,
            "Hot espresso poured over vanilla ice cream. A classic Italian dessert-drink combination.");

    private final String displayName;
    private final BeverageCategory category;
    private final double price;
    private final boolean allowsExtras;
    private final String description;

    BeverageType(String displayName, BeverageCategory category, double price, boolean allowsExtras, String description) {
        this.displayName = displayName;
        this.category = category;
        this.price = price;
        this.allowsExtras = allowsExtras;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public BeverageCategory getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public boolean allowsExtras() {
        return allowsExtras;
    }

    public String getDescription() {
        return description;
    }

    //all the beverages of one category, in menu order
    public static List<BeverageType> byCategory(BeverageCategory category) {
        List<BeverageType> result = new ArrayList<>();
        for(BeverageType type : values()) {
            if(type.category == category) {  //confronts if both type and category are the same object in memory
                result.add(type);
            }
        }
        return result;
    }
}
