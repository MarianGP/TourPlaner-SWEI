package org.garcia.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.garcia.App;

import java.io.IOException;

public class SecondaryController {

    @FXML
    public void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    public void searchTour(ActionEvent actionEvent) {
    }

    public void clearTours(ActionEvent actionEvent) {
    }
}
