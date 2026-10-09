package it.fiv.FIVeCafe.control;

import it.fiv.FIVeCafe.entity.Beverage;
import it.fiv.FIVeCafe.entity.BeverageType;
import it.fiv.FIVeCafe.entity.Extra;
import it.fiv.FIVeCafe.entity.Order;
import it.fiv.FIVeCafe.entity.OrderStatus;
import it.fiv.FIVeCafe.observer.OrderObserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class OrderController {

    //orders that have been paid and sent to the bar
    private final List<Order> submittedOrders = new ArrayList<>();
    private final List<OrderObserver> observers = new ArrayList<>();
    private int nextOrderNumber = 1;

    //observer subscription
    public void addObserver(OrderObserver observer) {
        Objects.requireNonNull(observer, "Observer cannot be null");
        if(!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    //unsubscribe observer
    public void removeObserver(OrderObserver observer) {
        observers.remove(observer);
    }

    //observer's pattern method that tells every subscriber that the order has changed
    private void notifyObservers(Order order) {
        for(OrderObserver observer : new ArrayList<>(observers)) {
            observer.update(order);
        }
    }

    //a new order, still in the customer's chart (the bar doesn't know anything about it)
    public Order startNewOrder() {
        return new Order();
    }

    public void addBeverageToOrder(Order order, BeverageType type, Set<Extra> extras) {
        Objects.requireNonNull(order, "Order cannot be null");
        Beverage beverage = BeverageFactory.createBeverage(type, extras);
        order.addBeverage(beverage);
    }

    //the order is sent to the bar. It is the ONLY way an order gets into the list
    public boolean submitOrder(Order order) {
        Objects.requireNonNull(order, "Order cannot be null");
        if(!order.canTransitionTo(OrderStatus.RECEIVED)) {  //checks whether the order's state is CREATED or not
            return false;
        }
        order.assignNumber(nextOrderNumber++);
        order.transitionTo(OrderStatus.RECEIVED);
        submittedOrders.add(order);
        notifyObservers(order);
        return true;
    }

    //method used by the barman only for orders that have already been submitted
    public boolean updateOrderStatus(Order order, OrderStatus next) {
        Objects.requireNonNull(order, "Order cannot be null");
        if(!submittedOrders.contains(order)) {  //checks on unsubmitted orders
            return false;
        }
        if(!order.transitionTo(next)) {  //checks if the status has been changed correctly
            return false;
        }
        notifyObservers(order);
        return true;
    }

    public List<Order> getSubmittedOrders() {
        return Collections.unmodifiableList(submittedOrders);
    }
}
