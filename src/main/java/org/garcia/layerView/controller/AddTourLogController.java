package org.garcia.layerView.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.garcia.layerView.viewModel.AddLogViewModel;
import org.garcia.layerView.viewModel.IViewModel;
import org.garcia.visitor.IVisitor;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddTourLogController implements Initializable, IController {
    //new log
    private AddLogViewModel viewModel;

    @FXML
    public TextField end;
    @FXML
    public TextField start;
    @FXML
    public Slider ratingSlider = new Slider();
    @FXML
    public DatePicker datePicker;
    @FXML
    public TextField logDuration;
    @FXML
    public TextField logDistance;
    @FXML
    public ChoiceBox<String> sportChoiceBox;

    @FXML
    public void addTourLog(ActionEvent actionEvent) {
        if (viewModel.addTourLog() > 0)
            closeDialog(actionEvent);
    }

    @FXML
    public void closeDialog(@NotNull ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void setDatePicker() {
        Label l = new Label("no date selected");
        EventHandler<ActionEvent> event = e -> {
            LocalDate ld = datePicker.getValue();
            viewModel.setLocalDate(ld);
            l.setText(ld.toString());
        };
        datePicker.setValue(LocalDate.now());
        datePicker.setShowWeekNumbers(true);
        datePicker.setOnAction(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewModel = AddLogViewModel.getInstance();
        setDatePicker();
        // bindings
        ratingSlider.valueProperty().bindBidirectional(viewModel.getRating());
        logDuration.textProperty().bindBidirectional(viewModel.getDuration());
        logDistance.textProperty().bindBidirectional(viewModel.getDistance());
        sportChoiceBox.setItems(viewModel.getAllSports());
        sportChoiceBox.valueProperty().bindBidirectional(viewModel.getSport());
//        sportChoiceBox.effectProperty().bindBidirectional(viewModel.getSportEffectProp());
    }

    @Override
    public void accept(IVisitor visitor, IViewModel viewModel) {
        visitor.visit(this, viewModel);
    }

    public void initViewModel(IViewModel previousViewModel) {
        viewModel.init(previousViewModel);
    }
}
