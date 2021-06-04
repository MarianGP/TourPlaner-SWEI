package org.garcia.layerView.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.garcia.layerView.viewModel.IViewModel;
import org.garcia.layerView.viewModel.SaveReportViewModel;
import org.garcia.visitor.IVisitor;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SaveReportController implements Initializable, IController {

    private static SaveReportViewModel viewModel;
    public AnchorPane paneView = new AnchorPane();
    public TextField reportName = new TextField();
    public ChoiceBox<String> reportTypeChoice = new ChoiceBox<>();
    public Label filePath = new Label();
    public Label chosenReportType = new Label();

    private BooleanProperty menuItemDisabled = new SimpleBooleanProperty();

    @FXML
    public void createReport(ActionEvent actionEvent) throws IOException {
        if (viewModel.createReport()) {
            closeDialog(actionEvent);
            filePath.setStyle("-fx-font-size: 11; -fx-text-fill: green;");
        } else {
            filePath.setStyle("-fx-font-size: 11; -fx-text-fill: red;");
        }
        chosenReportType.setText("");
    }

    @FXML
    public void openDirectoryChooser() {
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("src"));

        Stage stage = (Stage) paneView.getScene().getWindow();
        File file = directoryChooser.showDialog(stage);

        if (file != null) {
            viewModel.setReportUrlValue(file.getAbsolutePath());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewModel = SaveReportViewModel.getInstance();
        Bindings.bindBidirectional(viewModel.getReportTypeName(), reportTypeChoice.valueProperty());
        Bindings.bindBidirectional(chosenReportType.textProperty(), viewModel.getReportTypeName());
        Bindings.bindBidirectional(reportName.textProperty(), viewModel.getReportName());
        Bindings.bindBidirectional(filePath.textProperty(), viewModel.getReportUrl());
        reportTypeChoice.disableProperty().bind(viewModel.getIsTourSelected());
    }

    public void closeDialog(@NotNull ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void initViewModel(IViewModel previousViewModel) {
        viewModel.init(previousViewModel);
    }

    @Override
    public void accept(IVisitor visitor, IViewModel viewModel) {
        visitor.visit(this, viewModel);
    }
}
