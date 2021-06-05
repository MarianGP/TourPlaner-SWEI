package org.garcia.layerView.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.garcia.appVisitor.IVisitor;
import org.garcia.layerView.viewModel.IViewModel;
import org.garcia.layerView.viewModel.TourFormViewModel;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TourFormController implements Initializable, IController {

    private static final Logger logger = LogManager.getLogger(TourFormController.class);

    @FXML
    public HBox originHBOX;
    @FXML
    public HBox destinationHBOX;
    @FXML
    public Button addBtn = new Button();
    @FXML
    public Button editBtn;
    @FXML
    public TextField title = new TextField();
    @FXML
    public TextField origin = new TextField();
    @FXML
    public TextField destination = new TextField();
    @FXML
    public TextField description = new TextField();
    @FXML
    public Label alertLabel;
    private TourFormViewModel viewModel;

    @FXML
    public void addTour(ActionEvent actionEvent) throws IOException {
        if (viewModel.addNewTour() > 0) {
            closeDialog(actionEvent);
        }
    }

    public void editTour(ActionEvent actionEvent) {
        if (viewModel.editNewTour() > 0) {
            closeDialog(actionEvent);
        }
    }

    @FXML
    public void closeDialog(@NotNull ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        logger.info(stage.getTitle() + " closed");
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewModel = TourFormViewModel.getInstance();
        title.textProperty().bindBidirectional(viewModel.getTitle());
        origin.textProperty().bindBidirectional(viewModel.getOrigin());
        destination.textProperty().bindBidirectional(viewModel.getDestination());
        description.textProperty().bindBidirectional(viewModel.getDescription());
        alertLabel.textProperty().bindBidirectional(viewModel.getAlertMessage());
        alertLabel.visibleProperty().bind(viewModel.getAlertMessage().isNotEqualTo(""));
    }

    @Override
    public void accept(IVisitor visitor, IViewModel viewModel) {
        visitor.visit(this, viewModel);
    }

    public void initViewModel(IViewModel previousViewModel) {
        viewModel.init(previousViewModel);
        originHBOX.visibleProperty().bind(viewModel.getTourViewModel().getEditMode().not());
        destinationHBOX.visibleProperty().bind(viewModel.getTourViewModel().getEditMode().not());
        addBtn.visibleProperty().bind(viewModel.getTourViewModel().getEditMode().not());
        editBtn.visibleProperty().bind(viewModel.getTourViewModel().getEditMode());
    }
}
