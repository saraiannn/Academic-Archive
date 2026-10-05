package it.fiv.FIVeCafe.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Order {
    private final int orderNumber;
    private final List<Beverage> beverages = new ArrayList<>();  //list cannot be substituted but elements can be added to it
    private OrderStatus status = OrderStatus.CREATED;

    public Order(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public OrderStatus getStatus() {
        return status;
    }

    // read-only view of the beverages in the order
    public List<Beverage> getBeverages() {
        return Collections.unmodifiableList(beverages);
    }

    public boolean isEmpty() {
        return beverages.isEmpty();
    }

    public void addBeverage(Beverage beverage) {
        if (beverage == null) {
            throw new IllegalArgumentException("Beverage cannot be null");
        }
        if (status != OrderStatus.CREATED) {
            throw new IllegalArgumentException("Cannot add beverages to an order that is already " + status);
        }
        beverages.add(beverage);
    }

    public double getTotalPrice() {
        double total = 0;
        for(Beverage beverage : beverages) {
            total += beverage.getBeveragePrice();
        }
        return total;
    }

    public boolean canTransitionTo(OrderStatus next) {
        if(next == null || next != status.next()) {
            return false;
        }
        if(next == OrderStatus.RECEIVED && beverages.isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean transitionTo(OrderStatus next) {
        if(!canTransitionTo(next)) {
            return false;
        }
        status = next;
        return true;
    }
}
