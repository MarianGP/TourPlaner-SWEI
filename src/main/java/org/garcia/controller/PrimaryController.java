package org.garcia.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.garcia.App;

import java.io.IOException;

public class PrimaryController {

    @FXML
    public void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    public void searchTour(ActionEvent actionEvent) {
    }

    public void clearTours(ActionEvent actionEvent) {
    }
}
