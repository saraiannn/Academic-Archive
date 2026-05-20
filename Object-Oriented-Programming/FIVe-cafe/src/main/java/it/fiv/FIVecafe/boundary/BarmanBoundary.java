package it.fiv.FIVecafe.boundary;

import it.fiv.FIVecafe.control.OrderController;
import it.fiv.FIVecafe.entity.Order;
import it.fiv.FIVecafe.entity.OrderStatus;
import it.fiv.FIVecafe.observer.OrderObserver;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;

public class BarmanBoundary implements OrderObserver {

    private final OrderController orderController;

    private final ObservableList<Order> orderItems = FXCollections.observableArrayList();
    private ListView<Order> ordersView;

    public BarmanBoundary(OrderController orderController) {
        this.orderController = orderController;
    }

    private Button receivedBtn;
    private Button prepBtn;
    private Button readyBtn;
    private Button deliveredBtn;


    public void show() {
        Stage stage = new Stage();
        stage.setTitle("Barman's Order Display");

        orderItems.setAll(orderController.getOrders());

        ordersView = new ListView<>(orderItems);
        ordersView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Order o, boolean empty) {
                super.updateItem(o, empty);
                if (empty || o == null) {
                    setText(null);
                } else {
                    setText("Order #" + o.getOrderNumber() + " | " + o.getStatus());
                }
            }
        });

        receivedBtn = new Button("RECEIVED");
        prepBtn = new Button("PREPARING");
        readyBtn = new Button("READY");
        deliveredBtn = new Button("DELIVERED");

        ordersView.getSelectionModel().selectedItemProperty().addListener((obs, oldV, sel) -> {
            updateButtons(sel);
        });

        receivedBtn.getStyleClass().add("secondary-btn");
        prepBtn.getStyleClass().add("primary-btn");
        readyBtn.getStyleClass().add("primary-btn");
        deliveredBtn.getStyleClass().add("secondary-btn");

        receivedBtn.setOnAction(e -> changeStatus(OrderStatus.RECEIVED));
        prepBtn.setOnAction(e -> changeStatus(OrderStatus.PREPARING));
        readyBtn.setOnAction(e -> changeStatus(OrderStatus.READY));
        deliveredBtn.setOnAction(e -> changeStatus(OrderStatus.DELIVERED));

        HBox actions = new HBox(10, receivedBtn, prepBtn, readyBtn, deliveredBtn);
        actions.setPadding(new Insets(12));


        BorderPane root = new BorderPane();
        root.setCenter(ordersView);
        root.setBottom(actions);

        Scene scene = new Scene(root, 450, 600);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        stage.setScene(scene);
        stage.show();

        orderController.addObserver(this);
    }

    private void changeStatus(OrderStatus next) {
        Order selected = ordersView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            themedAlert(Alert.AlertType.WARNING, "Select an order first.").showAndWait();
            return;
        }

        OrderStatus before = selected.getStatus();
        orderController.updateOrderStatus(selected, next);

        if (selected.getStatus() == before) {
            themedAlert(Alert.AlertType.WARNING, "Invalid status change: " + before + " → " + next).showAndWait();
        }
        ordersView.refresh();
        updateButtons(selected);
    }


    @Override
    public void update(Order order) {
        Platform.runLater(() -> {

            if (!orderItems.contains(order)) {
                orderItems.add(order);
            }

            Order selected = ordersView.getSelectionModel().getSelectedItem();
            Integer selectedNumber = (selected != null) ? selected.getOrderNumber() : null;

            ordersView.refresh();

            if (selectedNumber != null) {
                for (Order o : orderItems) {
                    if (o.getOrderNumber() == selectedNumber) {
                        ordersView.getSelectionModel().select(o);
                        selected = o;
                        break;
                    }
                }
            }

            updateButtons(selected);
        });
    }


    private void updateButtons(Order sel) {
        if (sel == null) {
            receivedBtn.setDisable(true);
            prepBtn.setDisable(true);
            readyBtn.setDisable(true);
            deliveredBtn.setDisable(true);
            return;
        }

        receivedBtn.setDisable(sel.getStatus() != OrderStatus.CREATED);
        prepBtn.setDisable(sel.getStatus() != OrderStatus.RECEIVED);
        readyBtn.setDisable(sel.getStatus() != OrderStatus.PREPARING);
        deliveredBtn.setDisable(sel.getStatus() != OrderStatus.READY);
    }


    private Alert themedAlert(Alert.AlertType type, String text) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.getDialogPane().getStylesheets().add(
                getClass().getResource("/css/style.css").toExternalForm()
        );

        return alert;
    }


}


