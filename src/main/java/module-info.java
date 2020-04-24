module lv.javaguru {
    requires javafx.controls;
    requires javafx.fxml;

    opens lv.javaguru to javafx.fxml;
    exports lv.javaguru;
}