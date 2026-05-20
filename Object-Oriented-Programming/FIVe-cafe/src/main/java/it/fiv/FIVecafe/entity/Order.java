package it.fiv.FIVecafe.entity;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final int orderNumber;
    private final List<Beverage> beverages;
    private OrderStatus status = OrderStatus.CREATED;


    public Order(int orderNumber) {
        this.orderNumber = orderNumber;
        this.beverages = new ArrayList<>();
        this.status = OrderStatus.CREATED;

    }

    public void addBeverage(Beverage beverage) {
        beverages.add(beverage);
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    /*public List<Beverage> getBeverages() {
        return beverages;
    }*/

    public OrderStatus getStatus() {
        return status;
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        for(Beverage beverage : beverages) {
            totalPrice += beverage.getBeveragePrice();
        }
        return totalPrice;
    }

    public boolean canTransitionTo(OrderStatus next) {
        if (next == null) return false;
        OrderStatus current = this.status;

        if (current == next) return false;

        return (current == OrderStatus.CREATED   && next == OrderStatus.RECEIVED) ||
                (current == OrderStatus.RECEIVED  && next == OrderStatus.PREPARING) ||
                (current == OrderStatus.PREPARING && next == OrderStatus.READY) ||
                (current == OrderStatus.READY     && next == OrderStatus.DELIVERED);
    }

    public boolean transitionTo(OrderStatus next) {
        if (!canTransitionTo(next)) return false;
        this.status = next;
        return true;
    }


}

