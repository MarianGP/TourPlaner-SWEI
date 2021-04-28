module org.garcia {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.garcia;
    exports org.garcia;
    exports org.garcia.controller;
}
