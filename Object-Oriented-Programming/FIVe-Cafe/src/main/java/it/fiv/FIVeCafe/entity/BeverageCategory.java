package it.fiv.FIVeCafe.entity;

public enum BeverageCategory {
    BASIC_COFFEE("Basic Coffee"),
    COLD_COFFEE("Cold Coffee"),
    TEA_AND_NON_COFFEE("Tea & Non Coffee"),
    SWEET_DRINKS("Sweet Drinks"),
    REFRESHERS("Refreshers"),
    SEASONAL_SPECIALS("Seasonal Specials");

    private final String displayName;

    BeverageCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
