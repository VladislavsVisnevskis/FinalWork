package lv.javaguru;

import javafx.scene.control.Alert;

public class WarningDialog {

    public static void showWarning(String text){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Incorrect input");
        alert.setContentText(text);

        alert.showAndWait();
    }
}
