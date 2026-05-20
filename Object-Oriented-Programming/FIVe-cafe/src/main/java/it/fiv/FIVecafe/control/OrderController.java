package it.fiv.FIVecafe.control;

import it.fiv.FIVecafe.entity.Order;
import it.fiv.FIVecafe.entity.OrderStatus;
import it.fiv.FIVecafe.entity.Extra;
import it.fiv.FIVecafe.entity.Beverage;
import it.fiv.FIVecafe.entity.decorator.*;
import it.fiv.FIVecafe.observer.OrderObserver;
import it.fiv.FIVecafe.entity.BeverageType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OrderController {  //subject

    private List<OrderObserver> observers = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();
    private int nextOrderNumber = 1;

    public void addObserver(OrderObserver observer){
        observers.add(observer);
    }


    public void notifyObservers(Order order){
        for(OrderObserver observer : observers){
            observer.update(order);
        }
    }

    public Order startNewOrder(){
        Order order = new Order(nextOrderNumber++);
        orders.add(order);
        notifyObservers(order);
        return order;
    }


    public void updateOrderStatus(Order order, OrderStatus next) {
        if (order == null || next == null) return;

        if (!order.transitionTo(next)) {
            notifyObservers(order);
        }
    }



    public List<Order> getOrders() {
        return orders;
    }

    public void submitOrder(Order order) {
        if (order == null) return;

        if (order.transitionTo(OrderStatus.RECEIVED)) {
            notifyObservers(order);
        }
    }


    public void addBeverageToOrder(Order order, String beverageName, Set<Extra> extras) {
        if (order == null || beverageName == null) return;

        BeverageType type = BeverageType.fromDisplayName(beverageName);

        if (!type.allowsExtras()) {
            extras = Set.of();
        }

        Beverage beverage = BeverageFactory.createBeverage(type);


        if (beverage == null) return;


        if (extras != null) {
            if (extras.contains(Extra.MILK)) {
                beverage = new MilkDecorator(beverage);
            }
            if (extras.contains(Extra.SUGAR)) {
                beverage = new SugarDecorator(beverage);
            }
            if (extras.contains(Extra.CARAMEL)) {
                beverage = new CaramelDecorator(beverage);
            }
            if (extras.contains(Extra.COCOA)) {
                beverage = new CocoaDecorator(beverage);
            }
        }

        order.addBeverage(beverage);

        notifyObservers(order);
    }
}
