package org.garcia.layerView.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.garcia.layerView.viewModel.ToursViewModel;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddTourLogController implements Initializable {
    //new log
    private ToursViewModel viewModel;

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
    public String tourStringChoiceBox;

    public void addTourLog(ActionEvent actionEvent) {
        Object[] inputFields = {
                viewModel.getLocalDate(),
                Integer.parseInt(logDuration.getText()),
                Integer.parseInt(logDistance.getText())};

        if (viewModel.addTourLog(inputFields) > 0)
            closeDialog(actionEvent);
    }

    public void closeDialog(@NotNull ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private void setDatePicker() {
        Label l = new Label("no date selected");
        EventHandler<ActionEvent> event = e -> {
            LocalDate ld = datePicker.getValue();
            viewModel.setLocalDate(ld);
            l.setText(ld.toString());
        };

        // show week numbers
        datePicker.setShowWeekNumbers(true);
        // when datePicker is pressed
        datePicker.setOnAction(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewModel = ToursViewModel.getInstance();
        setDatePicker();
    }
}
