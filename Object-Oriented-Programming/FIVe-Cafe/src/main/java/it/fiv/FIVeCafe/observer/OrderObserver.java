package it.fiv.FIVeCafe.observer;

import it.fiv.FIVeCafe.entity.BasicBeverage;
import it.fiv.FIVeCafe.entity.Order;

public interface OrderObserver {
    // notice for the observer: the order has changed
    void updateOrder(Order order);
}
