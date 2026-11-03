package it.fiv.FIVeCafe.control;

import it.fiv.FIVeCafe.entity.*;
import it.fiv.FIVeCafe.observer.OrderObserver;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

public class OrderController {
    public OrderController() {}

    public List<Order> orders = new ArrayList<>();
    private int orderNumberCounter = 1;

    private final List <OrderObserver> observers = new ArrayList<>();

    public void addObserver(OrderObserver observer) {

        observers.add(observer);
    }

    public void removeObserver(OrderObserver observer) {

        observers.remove(observer);
    }

    public void notifyObservers(Order order) {
        for (OrderObserver observer : observers) {
            observer.updateOrder(order);
        }
    }

    public Order startOrder() {
        Order newOrder = new Order(orderNumberCounter++);
        orders.add(newOrder);
        return newOrder;
    }

    public void addToCart(BeverageType type, Set<Extra> extras, int orderId) {
        Order foundOrder = searchOrderById(orderId);
        Beverage beverageToCart = BeverageFactory.createBeverage(type, extras);
        foundOrder.addBeverage(beverageToCart);
    }

    public void pay(int orderId) {
        Order foundOrder = searchOrderById(orderId);
        // checking whether the cart is empty or not
        if(foundOrder.getBeverageList().isEmpty()) {
            throw new IllegalStateException("empty cart");
        }
        // checking whether the order has already been paid or not
        if(foundOrder.getStatus() != OrderStatus.CREATED) {
            throw new IllegalStateException("order already paid");
        }
        foundOrder.moveToNextStatus();
        notifyObservers(foundOrder);
    }

    public void cancelOrder(int orderId) {
        Order foundOrder = searchOrderById(orderId);
        if(foundOrder.getStatus() != OrderStatus.CREATED) {
            throw new IllegalStateException("cannot cancel an order already sent");
        }
        orders.remove(foundOrder);
    }

    // helper method
    private Order searchOrderById(int orderId) {
        for(Order order : orders) {
            if(order.getOrderId() == orderId) {
                return order;
            }
        }
        throw new IllegalArgumentException("order does not exist");
    }
}
