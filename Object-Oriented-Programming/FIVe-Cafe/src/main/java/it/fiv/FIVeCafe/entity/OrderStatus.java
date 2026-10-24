package it.fiv.FIVeCafe.entity;

public enum OrderStatus {
    CREATED,
    RECEIVED,
    PREPARING,
    READY,
    DELIVERED;

    public OrderStatus next() {
        switch(this) {
            case CREATED:
                return OrderStatus.RECEIVED;
            case RECEIVED:
                return OrderStatus.PREPARING;
            case PREPARING:
                return OrderStatus.READY;
            case READY:
                return OrderStatus.DELIVERED;
            default:
                throw new IllegalStateException("no status after DELIVERED");
        }
    }
}


