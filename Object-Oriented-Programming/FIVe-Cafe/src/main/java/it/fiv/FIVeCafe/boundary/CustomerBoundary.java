package it.fiv.FIVeCafe.boundary;

import it.fiv.FIVeCafe.control.BeverageFactory;
import it.fiv.FIVeCafe.control.OrderController;
import it.fiv.FIVeCafe.entity.Beverage;
import it.fiv.FIVeCafe.entity.BeverageCategory;
import it.fiv.FIVeCafe.entity.BeverageType;
import it.fiv.FIVeCafe.entity.Extra;
import it.fiv.FIVeCafe.entity.Order;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class CustomerBoundary extends Application {

    private final OrderController orderController = new OrderController();
    private Order currentOrder;

    private Stage stage;
    private Scene homeScene;

    // widgets of the order screen (rebuilt every time a new order starts)
    private Button selectedCategoryBtn;
    private ListView<BeverageType> beverageList;
    private Label nameLabel;
    private Label descriptionLabel;
    private Label priceLabel;
    private final Map<Extra, CheckBox> extraChecks = new EnumMap<>(Extra.class);
    private VBox extrasBox;
    private Button addBtn;
    private final ObservableList<Beverage> cartItems = FXCollections.observableArrayList();
    private Label totalLabel;
    private Button payBtn;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        homeScene = createHomeScene();

        stage.setTitle("FIVe Cafè");
        stage.setScene(homeScene);
        stage.show();

        new BarmanBoundary(orderController).show();
    }

    // 1. creating home screen
    private Scene createHomeScene() {
        Label title = new Label("FIVe Cafè's Totem");
        title.getStyleClass().add("title-large");

        Button startBtn = new Button("Start Order");
        startBtn.getStyleClass().add("primary-btn");
        startBtn.setOnAction(e -> startOrder());

        VBox layout = new VBox(20, title, startBtn);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 600, 400);
        Styles.apply(scene);
        return scene;
    }

    private void startOrder() {
        currentOrder = orderController.startNewOrder();
        stage.setScene(createOrderScene());
    }

    // 2. constructing the order screen
    private Scene createOrderScene() {
        selectedCategoryBtn = null;

        BorderPane root = new BorderPane();
        root.setLeft(createCategoryBar());
        root.setCenter(createMenuPane());
        root.setRight(createCartPane());

        // setting the starting state
        showBeverageDetails(null);
        refreshCart();

        Scene scene = new Scene(root, 1050, 600);
        Styles.apply(scene);
        return scene;
    }

    // creating a button for each menu category
    private VBox createCategoryBar() {
        VBox bar = new VBox(14);
        bar.setPrefWidth(220);
        bar.setPadding(new Insets(18));
        bar.getStyleClass().add("sidebar");

        for (BeverageCategory category : BeverageCategory.values()) {
            Button button = new Button(category.getDisplayName());
            button.setMaxWidth(Double.MAX_VALUE);
            button.setPrefHeight(64);
            // assigning a CSS class for its style
            button.getStyleClass().add("category-btn");
            button.setOnAction(e -> showCategory(category, button));
            bar.getChildren().add(button);
        }
        return bar;
    }

    // creating beverages list and details panel
    private VBox createMenuPane() {
        beverageList = new ListView<>();
        beverageList.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(BeverageType type, boolean empty) {
                super.updateItem(type, empty);
                if (empty || type == null) {
                    setText(null);
                } else {
                    setText(String.format("%s  -  %.2f €", type.getDisplayName(), type.getPrice()));
                }
            }
        });
        beverageList.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldType, newType) -> showBeverageDetails(newType));
        VBox.setVgrow(beverageList, Priority.ALWAYS);

        nameLabel = new Label();
        nameLabel.getStyleClass().add("title");
        descriptionLabel = new Label();
        descriptionLabel.setWrapText(true);
        priceLabel = new Label();
        priceLabel.getStyleClass().add("price-label");

        extrasBox = new VBox(6, new Label("Extras"));
        extraChecks.clear();
        // creating a box for each extra and storing it into extraChecks, then linking it to updatePrice() method
        // so the price automatically updates itself when the box is checked
        for (Extra extra : Extra.values()) {
            CheckBox check = new CheckBox(formattingName(extra));
            check.setOnAction(e -> updatePrice());
            extraChecks.put(extra, check);
            extrasBox.getChildren().add(check);
        }

        addBtn = new Button("Add to cart");
        addBtn.getStyleClass().add("primary-btn");
        addBtn.setOnAction(e -> addToCart());

        VBox details = new VBox(10, nameLabel, descriptionLabel, extrasBox, priceLabel, addBtn);
        details.setPadding(new Insets(15));
        details.getStyleClass().add("card");

        VBox pane = new VBox(10, beverageList, details);
        pane.setPadding(new Insets(15));
        return pane;
    }

    // creating cart, total amount of an order, pay and cancel buttons
    private VBox createCartPane() {
        Label title = new Label("Your order");
        title.getStyleClass().add("title");

        // linking beverage list to items inside the cart
        ListView<Beverage> cartView = new ListView<>(cartItems);
        cartView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Beverage beverage, boolean empty) {
                super.updateItem(beverage, empty);
                if (empty || beverage == null) {
                    setText(null);
                } else {
                    setText(String.format("%s  -  %.2f €", beverage.getBeverageName(), beverage.getBeveragePrice()));
                }
            }
        });
        VBox.setVgrow(cartView, Priority.ALWAYS);

        totalLabel = new Label();
        totalLabel.getStyleClass().add("subtitle");

        payBtn = new Button("Pay");
        payBtn.getStyleClass().add("primary-btn");
        payBtn.setOnAction(e -> pay());

        Button cancelBtn = new Button("Cancel order");
        cancelBtn.getStyleClass().add("secondary-btn");
        cancelBtn.setOnAction(e -> cancelOrder());

        VBox pane = new VBox(10, title, cartView, totalLabel, new HBox(10, payBtn, cancelBtn));
        pane.setPadding(new Insets(15));
        pane.setPrefWidth(300);
        pane.getStyleClass().add("cart-pane");
        return pane;
    }

    // 3. defining order screen behaviour
    // highlights selected category and fills the list with its beverages
    private void showCategory(BeverageCategory category, Button button) {
        // removing "selected" class to the previous button and adds it to the next
        if (selectedCategoryBtn != null) {
            selectedCategoryBtn.getStyleClass().remove("selected");
        }
        button.getStyleClass().add("selected");
        selectedCategoryBtn = button;

        // returns beverages of a specific section
        beverageList.setItems(FXCollections.observableArrayList(BeverageType.byCategory(category)));
        beverageList.getSelectionModel().clearSelection();
        showBeverageDetails(null);
    }

    // 4. showing the details of the selected beverage
    private void showBeverageDetails(BeverageType type) {
        // unchecks all former boxes, so the extras don't get to the next selected beverage section
        for (CheckBox check : extraChecks.values()) {
            check.setSelected(false);
        }

        if (type == null) {
            nameLabel.setText("Select a beverage");
            descriptionLabel.setText("");
            priceLabel.setText("");
            extrasBox.setDisable(true);
            addBtn.setDisable(true);
            return;
        }

        nameLabel.setText(type.getDisplayName());
        descriptionLabel.setText(type.getDescription());
        extrasBox.setDisable(!type.allowsExtras());
        addBtn.setDisable(false);
        updatePrice();
    }

    // beverage price with extras currently ticked
    private void updatePrice() {
        BeverageType type = beverageList.getSelectionModel().getSelectedItem();
        if (type == null) {
            return;
        }
        // creating a temporary beverage so it doesn't have to be written a second time
        Beverage preview = BeverageFactory.createBeverage(type, collectExtras());
        priceLabel.setText(String.format("Price: %.2f €", preview.getBeveragePrice()));
    }

    private void addToCart() {
        BeverageType type = beverageList.getSelectionModel().getSelectedItem();
        if (type == null) {
            return;
        }
        // passing it to the factory inside the controller
        orderController.addBeverageToOrder(currentOrder, type, collectExtras());
        refreshCart();
        beverageList.getSelectionModel().clearSelection();
    }

    // copies all beverages in the cart into the list and turns off "pay" button
    private void refreshCart() {
        cartItems.setAll(currentOrder.getBeverages());
        totalLabel.setText(String.format("Total: %.2f €", currentOrder.getTotalPrice()));
        payBtn.setDisable(currentOrder.isEmpty());
    }

    private void pay() {
        if (currentOrder.isEmpty()) {
            return;
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Card", "Card", "Cash");
        dialog.setTitle("Payment");
        dialog.setHeaderText(String.format("Total: %.2f €", currentOrder.getTotalPrice()));
        dialog.setContentText("Select payment method:");
        Styles.apply(dialog);
        Optional<String> method = dialog.showAndWait();

        if (method.isEmpty()) {
            return;   // payment cancelled: the cart stays as it is
        }

        if (!orderController.submitOrder(currentOrder)) {
            createAlert(Alert.AlertType.WARNING, "The order could not be sent.").showAndWait();
            return;
        }

        int number = currentOrder.getOrderNumber();
        createAlert(Alert.AlertType.INFORMATION, "Payment successful!\nOrder #" + number + "\nPaid with: " + method.get()).showAndWait();

        currentOrder = null;
        stage.setScene(homeScene);
    }

    private void cancelOrder() {
        if (!currentOrder.isEmpty()) {
            Alert alert = createAlert(Alert.AlertType.CONFIRMATION, "Discard this order?");
            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
            if (alert.showAndWait().orElse(ButtonType.NO) != ButtonType.YES) {
                return;
            }
        }
        currentOrder = null;
        stage.setScene(homeScene);
    }

    // helper: checks every selected box abd adds it key to an EnumSet
    private Set<Extra> collectExtras() {
        Set<Extra> extras = EnumSet.noneOf(Extra.class);
        for (Map.Entry<Extra, CheckBox> entry : extraChecks.entrySet()) {
            if (entry.getValue().isSelected()) {
                extras.add(entry.getKey());
            }
        }
        return extras;
    }

    // returns extras name with just the first letter in upper-case
    private String formattingName(Extra extra) {
        String name = extra.name();
        return name.charAt(0) + name.substring(1).toLowerCase();
    }

    // creates an alert with no text or heading
    private Alert createAlert(Alert.AlertType type, String text) {
        Alert alert = new Alert(type, text);
        alert.setHeaderText(null);
        Styles.apply(alert);
        return alert;
    }
}
