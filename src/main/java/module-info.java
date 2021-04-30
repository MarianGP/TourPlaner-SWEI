module org.garcia {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.sql;
    requires org.postgresql.jdbc;

    exports org.garcia;
    exports org.garcia.controller;
    exports org.garcia.model;
    exports org.garcia.layerbusiness.appmanager;
    exports org.garcia.layerbusiness.validator;
    exports org.garcia.model.enums;
    exports org.garcia.layerdataaccess.persistance.entity;
}
