package org.garcia.layerView.controller;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
    public Button addBtn;
    @FXML
    public TextField title;
    @FXML
    public TextField origin;
    @FXML
    public TextField destination;
    @FXML
    public TextField description;

    @FXML
    public void addTour(ActionEvent actionEvent) throws IOException {
        if (viewModel.addNewTour() > 0) {
            closeDialog(actionEvent);
        } else {
            //TODO: display error
            System.out.println("Couldn't add new tour");
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
        title.textProperty().bind(viewModel.getTitle());
        origin.textProperty().bind(viewModel.getOrigin());
        destination.textProperty().bind(viewModel.getDestination());
        description.textProperty().bind(viewModel.getDescription());

        BooleanBinding booleanBind = title.textProperty().isEmpty()
                .or(origin.textProperty().isEmpty())
                .or(description.textProperty().isEmpty())
                .or(destination.textProperty().isEmpty());

        addBtn.disableProperty().bind(booleanBind);
    }

    @Override
    public void accept(IVisitor visitor, IViewModel viewModel) {
        visitor.visit(this, viewModel);
    }

    public void initViewModel(IViewModel previousViewModel) {
        viewModel.init(previousViewModel);
    }
}
