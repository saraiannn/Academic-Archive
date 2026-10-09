
package it.fiv.FIVeCafe.boundary;

import javafx.scene.Scene;
import javafx.scene.control.Dialog;

import java.util.Objects;

// the only place that knows where the stylesheet is
final class Styles {

    private static final String STYLESHEET = Objects.requireNonNull(
            Styles.class.getResource("/css/style.css"),
            "Stylesheet /css/style.css not found").toExternalForm();

    private Styles() {
        // utility class: never instantiated
    }

    static void apply(Scene scene) {
        scene.getStylesheets().add(STYLESHEET);
    }

    // Alert and ChoiceDialog are Dialogs: their style goes on the dialog pane
    static void apply(Dialog<?> dialog) {
        dialog.getDialogPane().getStylesheets().add(STYLESHEET);
    }
}