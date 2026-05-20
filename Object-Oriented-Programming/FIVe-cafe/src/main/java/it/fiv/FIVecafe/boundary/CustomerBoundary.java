package it.fiv.FIVecafe.boundary;

import it.fiv.FIVecafe.control.OrderController;
import it.fiv.FIVecafe.entity.Extra;
import it.fiv.FIVecafe.entity.Order;
import it.fiv.FIVecafe.entity.BeverageType;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javafx.scene.control.CheckBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.EnumSet;
import java.util.Set;
import java.util.Optional;

public class CustomerBoundary extends Application {  //GUI entry point

    private OrderController orderController = new OrderController();
    private Order currentOrder;

    private Button sendToBarmanBtn;

    @Override
    public void start(Stage stage) {

        Label title = new Label("FIVe Cafè's Totem");
        title.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");

        Button startOrderBtn = new Button("Start Order");

        VBox homeLayout = new VBox(20, title, startOrderBtn);
        homeLayout.setStyle("-fx-alignment: center;");

        Scene homeScene = new Scene(homeLayout, 600, 400);
        homeScene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        stage.setScene(homeScene);
        stage.show();

        // opens BarmanDisplay window when the program starts
        new BarmanBoundary(orderController).show();

        startOrderBtn.setOnAction(e -> {
            currentOrder = orderController.startNewOrder();
            Scene orderScene = createOrderScene(stage);
            stage.setScene(orderScene);
        });

        stage.setScene(homeScene);
        stage.setTitle("FIVe Cafè");
        stage.show();
    }

    private Scene createOrderScene(Stage stage) {

        BorderPane root = new BorderPane();

        // categories (left side)
        VBox categoryBox = new VBox(14);
        categoryBox.setPrefWidth(220);
        categoryBox.getStyleClass().add("sidebar");

        // beverages (right side)
        ListView<String> beverageList = new ListView<>();
        beverageList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // beverage details
        Label bevName = new Label("");
        bevName.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        Label bevDesc = new Label("");
        bevDesc.setWrapText(true);

        CheckBox milkCb = new CheckBox("Milk (+0.30€)");
        CheckBox sugarCb = new CheckBox("Sugar (+0.10€)");
        CheckBox caramelCb = new CheckBox("Caramel (+0.60€)");
        CheckBox cocoaCb = new CheckBox("Cocoa (+0.40€)");

        VBox extrasBox = new VBox(6, new Label("Extras"), milkCb, sugarCb, caramelCb, cocoaCb);

        extrasBox.setDisable(true);
        milkCb.setSelected(false);
        sugarCb.setSelected(false);
        caramelCb.setSelected(false);
        cocoaCb.setSelected(false);

        sendToBarmanBtn = new Button("Pay");
        sendToBarmanBtn.setDisable(true);
        sendToBarmanBtn.getStyleClass().add("primary-btn"); // css

        sendToBarmanBtn.setOnAction(e -> {

            if (currentOrder == null) return;

            if (currentOrder.getTotalPrice() <= 0) {
                themedAlert(Alert.AlertType.WARNING,
                        "Your cart is empty. Add at least one item.")
                        .showAndWait();
                return;
            }

            boolean paid = showPaymentDialog(currentOrder.getTotalPrice());

            if (!paid) {
                themedAlert(Alert.AlertType.INFORMATION,
                        "Payment cancelled.")
                        .showAndWait();
                return;
            }

            orderController.submitOrder(currentOrder);

            themedAlert(Alert.AlertType.INFORMATION,
                    "Payment successful!\n" +
                            "Order ID: #" + currentOrder.getOrderNumber() +
                            "\nStatus: " + currentOrder.getStatus())
                    .showAndWait();

            sendToBarmanBtn.setDisable(true);
        });



        Button addToCartBtn = new Button("Add to cart");
        Button cancelBtn = new Button("Cancel");

        addToCartBtn.getStyleClass().add("primary-btn");
        cancelBtn.getStyleClass().add("secondary-btn");

        addToCartBtn.setDisable(true);
        cancelBtn.setDisable(true);

        HBox actions = new HBox(10, addToCartBtn, cancelBtn, sendToBarmanBtn);

        VBox detailsBox = new VBox(10, bevName, bevDesc, extrasBox, actions);
        detailsBox.setPadding(new Insets(15));
        detailsBox.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1;");


        detailsBox.setVisible(false);
        detailsBox.setManaged(false);


        VBox rightBox = new VBox(10, beverageList, detailsBox);
        rightBox.setPadding(new Insets(15));
        root.setCenter(rightBox);

        Map<String, String[]> categoryToBeverages = new LinkedHashMap<>();
        categoryToBeverages.put("Basic Coffee", new String[]{"Espresso", "Americano", "Cappuccino", "Macchiato", "Flat White", "Latte", "Double Espresso"});
        categoryToBeverages.put("Cold Coffee", new String[]{"Iced Latte", "Cold Brew", "Iced Americano", "Iced Mocha", "Iced Cappuccino", "Espresso Tonic", "Iced Chocolate Latte"});
        categoryToBeverages.put("Tea & Non Coffee", new String[]{"Green Tea", "Hot Chocolate", "Black Tea", "Chai Latte", "Herbal Tea", "Matcha Latte", "Golden Milk"});
        categoryToBeverages.put("Sweet Drinks", new String[]{"Vanilla Latte", "Caramel Latte", "Hazelnut Latte", "Mocha", "White Mocha", "Cinnamon Latte"});
        categoryToBeverages.put("Refreshers", new String[]{"Lemonade", "Sparkling Lemonade", "Peach Iced Tea", "Lemon Iced Tea", "Orange Juice", "Sparkling Water", "Still Water"});
        categoryToBeverages.put("Seasonal Specials", new String[]{"Pumpkin Spice Latte", "Gingerbread Latte", "Peppermint Mocha", "Salted Caramel Mocha", "Affogato"});

        final Button[] selectedCategory = new Button[1];

        final String[] currentCategory = new String[]{""};

        // category buttons
        for (String categoryName : categoryToBeverages.keySet()) {
            Button catBtn = new Button(categoryName);
            catBtn.setMaxWidth(Double.MAX_VALUE);
            catBtn.setPrefHeight(64);
            catBtn.setAlignment(Pos.CENTER_LEFT);
            catBtn.setPadding(new Insets(12, 16, 12, 16));
            catBtn.getStyleClass().add("category-btn");
            catBtn.setPrefHeight(64);


            catBtn.setOnAction(e -> {
                currentCategory[0] = categoryName;

                if (selectedCategory[0] != null) {
                    selectedCategory[0].getStyleClass().remove("selected");
                }
                catBtn.getStyleClass().add("selected");
                selectedCategory[0] = catBtn;

                ObservableList<String> items = FXCollections.observableArrayList(categoryToBeverages.get(categoryName));
                beverageList.setItems(items);

                detailsBox.setVisible(true);
                detailsBox.setManaged(true);

                beverageList.getSelectionModel().clearSelection();
                resetExtras(milkCb, sugarCb, caramelCb, cocoaCb);
                bevName.setText("Select a beverage");
                bevDesc.setText("");
                addToCartBtn.setDisable(true);
                cancelBtn.setDisable(true);

                sendToBarmanBtn.setDisable(currentOrder == null || currentOrder.getTotalPrice() <= 0);
            });

            categoryBox.getChildren().add(catBtn);
        }

        root.setLeft(categoryBox);

        beverageList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selected) -> {
            if (selected == null) {
                bevName.setText("Select a beverage");
                bevDesc.setText("");
                addToCartBtn.setDisable(true);
                cancelBtn.setDisable(true);

                extrasBox.setDisable(true);
                milkCb.setSelected(false);
                sugarCb.setSelected(false);
                caramelCb.setSelected(false);
                cocoaCb.setSelected(false);

                return;
            }
            resetExtras(milkCb, sugarCb, caramelCb, cocoaCb);

            bevName.setText(selected);
            bevDesc.setText(getDescription(selected));
            addToCartBtn.setDisable(false);
            cancelBtn.setDisable(false);

            BeverageType type = BeverageType.fromDisplayName(selected);
            boolean allowsExtras = type.allowsExtras();

            extrasBox.setDisable(!allowsExtras);
            if(!allowsExtras) {
                milkCb.setSelected(false);
                sugarCb.setSelected(false);
                caramelCb.setSelected(false);
                cocoaCb.setSelected(false);
            }

        });

        addToCartBtn.setOnAction(e -> {
            String selected = beverageList.getSelectionModel().getSelectedItem();
            if (selected == null || currentOrder == null) return;

            Set<Extra> extras = collectExtras(milkCb, sugarCb, caramelCb, cocoaCb);

            orderController.addBeverageToOrder(currentOrder, selected, extras);

            sendToBarmanBtn.setDisable(currentOrder.getTotalPrice() <= 0);

            themedAlert(
                    Alert.AlertType.INFORMATION,
                    selected + " added!\nOrder total: " + String.format("%.2f €", currentOrder.getTotalPrice())
            ).showAndWait();

            resetExtras(milkCb, sugarCb, caramelCb, cocoaCb);
            beverageList.getSelectionModel().clearSelection();
            extrasBox.setDisable(true);
            addToCartBtn.setDisable(true);
            cancelBtn.setDisable(true);


        });

        cancelBtn.setOnAction(e -> {
            beverageList.getSelectionModel().clearSelection();
            beverageList.setItems(FXCollections.observableArrayList());

            detailsBox.setVisible(false);
            detailsBox.setManaged(false);

            bevName.setText("");
            bevDesc.setText("");
            addToCartBtn.setDisable(true);
            cancelBtn.setDisable(true);
        });

        Scene scene = new Scene(root, 900, 550);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        return scene;

    }


    private String getDescription(String bev) {
        switch (bev) {

            // basic coffee
            case "Espresso":
                return "A short and intense coffee made with hot water under pressure. Strong flavor and rich aroma.";
            case "Americano":
                return "An espresso diluted with hot water. Lighter and smoother, perfect to sip slowly.";
            case "Cappuccino":
                return "Espresso with steamed milk and milk foam. Creamy, balanced, and very popular.";
            case "Macchiato":
                return "Espresso 'stained' with a small amount of steamed milk or foam. Strong coffee taste with a softer touch.";
            case "Flat White":
                return "A smooth coffee made with espresso and finely textured steamed milk. Creamy and balanced.";
            case "Latte":
                return "Espresso with plenty of steamed milk and a light layer of foam. Mild and creamy.";
            case "Double Espresso":
                return "A stronger version of espresso made with double the coffee. Bold, intense, and full-bodied.";

            // cold coffee
            case "Iced Latte":
                return "Cold milk with espresso and ice. Refreshing and creamy, ideal for hot days.";
            case "Cold Brew":
                return "Coffee brewed cold over several hours. Smooth, less acidic, and highly aromatic.";
            case "Iced Americano":
                return "Espresso diluted with cold water and ice. Light and refreshing with a classic coffee taste.";
            case "Iced Mocha":
                return "Iced coffee with milk and chocolate. Sweet, rich, and creamy, perfect for chocolate lovers.";
            case "Iced Cappuccino":
                return "Espresso served cold with milk and foam over ice. Refreshing and creamy with a classic taste.";
            case "Espresso Tonic":
                return "Espresso poured over ice and tonic water. Fresh, slightly bitter, and very aromatic.";
            case "Iced Chocolate Latte":
                return "Cold milk with espresso and chocolate over ice. Sweet, rich, and refreshing.";

            // tea & non coffee
            case "Green Tea":
                return "Delicate green tea with a light, slightly herbal flavor. A refreshing and light beverage.";
            case "Hot Chocolate":
                return "Thick and creamy hot chocolate. Sweet, comforting, and perfect on cold days.";
            case "Black Tea":
                return "Strong and full-bodied black tea. Can be enjoyed plain or with milk and sugar.";
            case "Chai Latte":
                return "Spiced black tea mixed with steamed milk. Warm, aromatic, and slightly sweet.";
            case "Herbal Tea":
                return "A caffeine-free infusion made from herbs and flowers. Light, relaxing, and soothing.";
            case "Matcha Latte":
                return "Japanese green tea powder blended with milk. Creamy, earthy, and energizing.";
            case "Golden Milk":
                return "Warm milk with turmeric and spices. Comforting, aromatic, and slightly spicy.";

            // sweet drinks
            case "Vanilla Latte":
                return "Espresso with steamed milk and vanilla syrup. Sweet, smooth, and fragrant.";
            case "Caramel Latte":
                return "Espresso with milk and caramel syrup. Rich, sweet, and indulgent.";
            case "Hazelnut Latte":
                return "Espresso with milk and hazelnut flavor. Nutty, smooth, and aromatic.";
            case "Mocha":
                return "Espresso with milk and chocolate. A perfect balance between coffee and chocolate.";
            case "White Mocha":
                return "Espresso with milk and white chocolate. Sweet, creamy, and velvety.";
            case "Cinnamon Latte":
                return "Espresso with milk and cinnamon flavor. Warm, spicy, and comforting.";

            // refreshers
            case "Lemonade":
                return "Fresh lemon juice mixed with water and sugar. Crisp, refreshing, and slightly sweet.";
            case "Sparkling Lemonade":
                return "Lemonade with sparkling water. Bubbly, fresh, and refreshing.";
            case "Peach Iced Tea":
                return "Black tea served cold with peach flavor. Sweet, fruity, and refreshing.";
            case "Lemon Iced Tea":
                return "Cold black tea with lemon. Light, fresh, and thirst-quenching.";
            case "Orange Juice":
                return "Fresh orange juice. Naturally sweet and rich in flavor.";
            case "Sparkling Water":
                return "Carbonated mineral water. Clean, refreshing, and light.";
            case "Still Water":
                return "Natural still mineral water. Pure, neutral, and refreshing.";

            // seasonal specials
            case "Pumpkin Spice Latte":
                return "Espresso with milk and pumpkin spice flavors. Warm, sweet, and seasonal.";
            case "Gingerbread Latte":
                return "Espresso with milk and gingerbread spices. Festive, spicy, and comforting.";
            case "Peppermint Mocha":
                return "Espresso with chocolate and peppermint flavor. Fresh, sweet, and rich.";
            case "Salted Caramel Mocha":
                return "Chocolate coffee with caramel and a hint of salt. Sweet, rich, and balanced.";
            case "Affogato":
                return "Hot espresso poured over vanilla ice cream. A classic Italian dessert-drink combination.";

            default:
                return "Description not available.";
        }
    }


    private Alert themedAlert(Alert.AlertType type, String text) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.getDialogPane().getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        return alert;
    }

    private boolean showPaymentDialog(double total) {

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Card", "Card", "Cash");

        dialog.setTitle("Payment");
        dialog.setHeaderText("Total: " + String.format("%.2f €", total));
        dialog.setContentText("Select payment method:");

        dialog.getDialogPane().getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        Optional<String> result = dialog.showAndWait();

        return result.isPresent();
    }

    private Set<Extra> collectExtras(CheckBox milkCb, CheckBox sugarCb, CheckBox caramelCb, CheckBox cocoaCb) {
        Set<Extra> extras = EnumSet.noneOf(Extra.class);
        if (milkCb.isSelected()) extras.add(Extra.MILK);
        if (sugarCb.isSelected()) extras.add(Extra.SUGAR);
        if (caramelCb.isSelected()) extras.add(Extra.CARAMEL);
        if (cocoaCb.isSelected()) extras.add(Extra.COCOA);
        return extras;
    }

    private void resetExtras(CheckBox milkCb, CheckBox sugarCb, CheckBox caramelCb, CheckBox cocoaCb) {
        milkCb.setSelected(false);
        sugarCb.setSelected(false);
        caramelCb.setSelected(false);
        cocoaCb.setSelected(false);
    }



}


