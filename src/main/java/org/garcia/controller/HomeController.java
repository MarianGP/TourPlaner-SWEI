package org.garcia.controller;

import javafx.fxml.FXML;
import org.garcia.App;
import org.garcia.model.enums.ViewName;

import java.io.IOException;

public class HomeController {

    @FXML
    public void switchToSecondary() throws IOException {
        App.setRoot(ViewName.TOURS.getViewName());    }

}
