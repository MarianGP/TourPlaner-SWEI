module org.garcia {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.garcia to javafx.fxml;
    exports org.garcia;
}