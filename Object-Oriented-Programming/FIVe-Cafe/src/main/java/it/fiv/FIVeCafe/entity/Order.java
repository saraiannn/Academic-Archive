package it.fiv.FIVeCafe.entity;

import java.util.ArrayList;
import java.util.List;

public class Order {
    // constructor with orderId ✅
    // list of beverages ✅
    // method to add a beverage to a list ✅
    // getTotalPrice() method ✅
    // getter for orderId, list and status ✅
    // using moveToNextStatus() method to go through its state ✅
    // controlling whether its status is DELIVERED or not

    private final int orderId;
    private List<Beverage> beverageList;
    private OrderStatus status;

    public Order(int orderId) {
        this.orderId = orderId;
        this.beverageList = new ArrayList<>();
        this.status = OrderStatus.CREATED;

    }

    public int getOrderId() {
        return orderId;
    }

    public List<Beverage> getBeverageList() {
        return beverageList;
    }

    public OrderStatus getStatus() {
        return status;
    }

    // safety controls: if passed beverage is null or order is already been delivered, a new beverage cannot be added
    public void addBeverage(Beverage beverageToAdd) {
       if (beverageToAdd == null) {
           throw new IllegalArgumentException("beverage is null");
       }
       if (status != OrderStatus.CREATED) {
           throw new IllegalStateException("cannot add beverage: order is not in CREATED state");
       }
       beverageList.add(beverageToAdd);
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        for (Beverage beverageInList :  beverageList) {
            totalPrice += beverageInList.getBeveragePrice();
        }
        return totalPrice;
    }

    public void moveToNextStatus() {
        status = status.next();
    }

    public boolean isDelivered() {
        return status == OrderStatus.DELIVERED;
    }

}
