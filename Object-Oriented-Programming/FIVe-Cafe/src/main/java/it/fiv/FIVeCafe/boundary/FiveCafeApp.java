package it.fiv.FIVeCafe.boundary;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class FiveCafeApp extends Application {
    @Override
    public void start(Stage stage) {
        Label label = new Label("Hello");
        Scene scene = new Scene(label, 400, 300);
        stage.setTitle("Five Cafe");
        stage.setScene(scene);
        stage.show();
    }
}
