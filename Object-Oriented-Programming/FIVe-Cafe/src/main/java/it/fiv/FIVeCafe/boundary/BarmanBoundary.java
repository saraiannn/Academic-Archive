package it.fiv.FIVeCafe.boundary;

import it.fiv.FIVeCafe.control.OrderController;
import it.fiv.FIVeCafe.entity.Beverage;
import it.fiv.FIVeCafe.entity.Order;
import it.fiv.FIVeCafe.entity.OrderStatus;
import it.fiv.FIVeCafe.observer.OrderObserver;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BarmanBoundary implements OrderObserver {

    //same controller used by the totem
    private final OrderController orderController;
    //when a ordersView is added to an orderItems list, the observable
    //Listview notices it and automatically adds the new line *
    private final ObservableList<Order> orderItems = FXCollections.observableArrayList();

    //fields that represent the graphical components used by most methods of this class
    private ListView<Order> ordersView;
    private Label detailTitle;
    private Label detailBody;
    private Button preparingBtn;
    private Button readyBtn;
    private Button deliveredBtn;

    //1. saves the controller
    public BarmanBoundary(OrderController orderController) {
        this.orderController = orderController;
    }

    //2. building barman's window, connecting it to the controller and opening it
    public void show() {
        Stage stage = new Stage();
        stage.setTitle("Barman's Order Display");

        // orders list (center)
        // here *
        ordersView = new ListView<>(orderItems);
        //turns an Order object into a text line into the ListView
        ordersView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Order order, boolean empty) {
                super.updateItem(order, empty);
                if (empty || order == null) {
                    setText(null);
                } else {
                    setText("Order #" + order.getOrderNumber() + " | " + order.getStatus());
                }
            }
        });
        ordersView.getSelectionModel().selectedItemProperty().addListener((obs, oldOrder, newOrder) -> refreshSelection());

        // details of the selected order (right)
        detailTitle = new Label();
        detailTitle.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        detailBody = new Label();
        detailBody.setWrapText(true);
        VBox details = new VBox(10, detailTitle, detailBody);
        details.setPadding(new Insets(15));
        details.setPrefWidth(280);

        // status buttons (bottom)
        preparingBtn = createStatusButton(OrderStatus.PREPARING);
        readyBtn = createStatusButton(OrderStatus.READY);
        deliveredBtn = createStatusButton(OrderStatus.DELIVERED);
        HBox actions = new HBox(10, preparingBtn, readyBtn, deliveredBtn);
        actions.setPadding(new Insets(12));

        BorderPane root = new BorderPane();
        root.setCenter(ordersView);
        root.setRight(details);
        root.setBottom(actions);

        // copies inside orderItems all orders already sent before this window opened, then subscribe to the new ones
        orderItems.setAll(orderController.getSubmittedOrders());
        orderController.addObserver(this);

        refreshSelection();

        stage.setScene(new Scene(root, 700, 450));
        stage.show();
    }

    // 3. creating clickable buttons
    private Button createStatusButton(OrderStatus target) {
        Button button = new Button(target.name());
        button.setOnAction(e -> changeStatus(target));
        return button;
    }

    // 4. asking the controller to update the order status to its next
    private void changeStatus(OrderStatus next) {
        Order selected = ordersView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }
        boolean changed = orderController.updateOrderStatus(selected, next);
        // if the status hasn't been changed, shows a warning
        if (!changed) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Invalid status change: " + selected.getStatus() + " → " + next);
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        // no refresh here: on success the controller notifies update(), see below
    }

    // called by the controller when an order is sent or changes status
    @Override
    public void update(Order order) {
        Platform.runLater(() -> {
            // checks if whether the order is already in the list or not
            if (!orderItems.contains(order)) {
                orderItems.add(order);
            }
            //redraws list lines
            ordersView.refresh();
            refreshSelection();
        });
    }

    // redraws the detail panel and the buttons for the selected order
    private void refreshSelection() {
        Order selected = ordersView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            detailTitle.setText("No order selected");
            detailBody.setText("");
        } else {
            detailTitle.setText("Order #" + selected.getOrderNumber());
            StringBuilder text = new StringBuilder();
            for (Beverage beverage : selected.getBeverages()) {
                text.append("• ").append(beverage.getBeverageName()).append("\n");
            }
            text.append("\nStatus: ").append(selected.getStatus());
            detailBody.setText(text.toString());
        }

        preparingBtn.setDisable(!canMoveTo(selected, OrderStatus.PREPARING));
        readyBtn.setDisable(!canMoveTo(selected, OrderStatus.READY));
        deliveredBtn.setDisable(!canMoveTo(selected, OrderStatus.DELIVERED));
    }

    // checks if the selected order exists and so, if it can switch to the next status
    private boolean canMoveTo(Order order, OrderStatus target) {
        return order != null && order.canTransitionTo(target);
    }
}