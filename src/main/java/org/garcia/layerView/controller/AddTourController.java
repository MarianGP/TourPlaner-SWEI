package org.garcia.layerView.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.garcia.layerBusiness.util.InputValidator;
import org.garcia.layerView.viewModel.AddTourViewModel;
import org.garcia.layerView.viewModel.IViewModel;
import org.garcia.visitor.IVisitor;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddTourController implements Initializable, IController {

    private AddTourViewModel viewModel;

    @FXML
    public Button addBtn = new Button();
    @FXML
    public TextField title = new TextField();
    @FXML
    public TextField origin = new TextField();
    @FXML
    public TextField destination = new TextField();
    @FXML
    public TextField description = new TextField();

    @FXML
    public void addTour(ActionEvent actionEvent) throws IOException {
        if (viewModel.addNewTour() > 0) {
            closeDialog(actionEvent);
        } else {
            System.out.println("Couldn't add new tour");
        }
    }

    public void editTour(ActionEvent actionEvent) {
        if (InputValidator.validString(title.getText())
                && InputValidator.validString(description.getText())) {
            if (viewModel.editNewTour() > 0) {
                closeDialog(actionEvent);
            } else {
                System.out.println("Couldn't edit tour");
            }
        }
    }

    @FXML
    public void closeDialog(@NotNull ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewModel = AddTourViewModel.getInstance();
        title.textProperty().bindBidirectional(viewModel.getTitle());
        origin.textProperty().bindBidirectional(viewModel.getOrigin());
        destination.textProperty().bindBidirectional(viewModel.getDestination());
        description.textProperty().bindBidirectional(viewModel.getDescription());
    }

    @Override
    public void accept(IVisitor visitor, IViewModel viewModel) {
        visitor.visit(this, viewModel);
    }

    public void initViewModel(IViewModel previousViewModel) {
        viewModel.init(previousViewModel);
    }
}
