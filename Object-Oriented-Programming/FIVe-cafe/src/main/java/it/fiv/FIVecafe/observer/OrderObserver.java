package it.fiv.FIVecafe.observer;

import it.fiv.FIVecafe.entity.Order;

public interface OrderObserver {  //observer
    void update(Order orders);
}

