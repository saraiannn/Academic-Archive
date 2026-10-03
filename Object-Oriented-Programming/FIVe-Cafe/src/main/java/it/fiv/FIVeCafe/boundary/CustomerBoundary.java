package it.fiv.FIVeCafe.boundary;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CustomerBoundary extends Application {
    @Override
    public void start(Stage stage) {
        Label title = new Label("FIVe Cafè's Order Totem");
        Scene scene = new Scene(title, 600, 400);
        stage.setTitle("FIVe Cafè");
        stage.setScene(scene);
        stage.show();
    }
}
