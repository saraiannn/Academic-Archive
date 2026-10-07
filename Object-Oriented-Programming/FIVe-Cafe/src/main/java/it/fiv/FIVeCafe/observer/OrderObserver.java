package it.fiv.FIVeCafe.observer;

import it.fiv.FIVeCafe.entity.Order;

public interface OrderObserver {

    //called everytime an order is sent to the bar or changes its status
    void update(Order order);
}
