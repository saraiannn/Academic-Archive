package it.fiv.FIVeCafe.control;

import it.fiv.FIVeCafe.entity.Beverage;
import it.fiv.FIVeCafe.entity.BeverageType;
import it.fiv.FIVeCafe.entity.Extra;
import it.fiv.FIVeCafe.entity.Order;
import it.fiv.FIVeCafe.entity.OrderStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class OrderController {

    //orders that have been paid and sent to the bar
    private final List<Order> submittedOrders = new ArrayList<>();
    private int nextOrderNumber = 1;

    //a new order, still in the customer's chart (the bar doesn't know anything about it)
    public Order startNewOrder() {
        return new Order(nextOrderNumber++);
    }

    public void addBeverageToOrder(Order order, BeverageType type, Set<Extra> extras) {
        Objects.requireNonNull(order, "Order cannot be null");
        Beverage beverage = BeverageFactory.createBeverage(type, extras);
        order.addBeverage(beverage);
    }

    //the order is sent to the bar. It is the ONLY way an order gets into the list
    public boolean submitOrder(Order order) {
        Objects.requireNonNull(order, "Order cannot be null");
        if(!order.transitionTo(OrderStatus.RECEIVED)) {  //checks whether the order's state is CREATED or not
            return false;
        }
        submittedOrders.add(order);
        return true;
    }

    //method used by the barman only for orders that have already been submitted
    public boolean updateOrderStatus(Order order, OrderStatus next) {
        Objects.requireNonNull(order, "Order cannot be null");
        if(!submittedOrders.contains(order)) {  //checks on unsubmitted orders
            return false;
        }
        return order.transitionTo(next);
    }

    public List<Order> getSubmittedOrders() {
        return Collections.unmodifiableList(submittedOrders);
    }
}
