package org.garcia.layerView.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.garcia.layerBusiness.appmanager.IAppManager;
import org.garcia.layerBusiness.util.InputValidator;
import org.garcia.layerView.viewModel.IViewModel;
import org.garcia.layerView.viewModel.ToursViewModel;
import org.garcia.visitor.IVisitor;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.ResourceBundle;

public class EditTourController implements Initializable, IController {

    private ToursViewModel viewModel;
    @FXML
    public TextField title;
    @FXML
    public TextField description;

    @FXML
    public void editTour(ActionEvent actionEvent) {
        if (InputValidator.validString(title.getText())
                && InputValidator.validString(description.getText())) {
            if (viewModel.editNewTour(title.getText(), description.getText()) > 0) {
                closeDialog(actionEvent);
            } else {
                System.out.println("Couldn't add new tour");
            }
        }
    }

    @FXML
    public void closeDialog(@NotNull ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        viewModel.searchTours("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewModel = ToursViewModel.getInstance();

    }

    @Override
    public void accept(IVisitor visitor, IViewModel viewModel) {
        visitor.visit(this, viewModel);
    }

    public void initViewModel(IAppManager aAppManager) {
        System.out.println("Add tour view model initialized");
    }
}
