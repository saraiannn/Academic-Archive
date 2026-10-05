package it.fiv.FIVeCafe.entity;

public enum OrderStatus {
    CREATED,
    RECEIVED,
    PREPARING,
    READY,
    DELIVERED;

    public OrderStatus next() {  //the only status that can follow this one (null if there is none)
        return switch(this) {
            case CREATED -> RECEIVED;
            case RECEIVED -> PREPARING;
            case PREPARING -> READY;
            case READY -> DELIVERED;
            case DELIVERED -> null;
        };
    }
}
