package org.garcia.layerView.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.garcia.layerView.viewModel.AddLogViewModel;
import org.garcia.layerView.viewModel.IViewModel;
import org.garcia.visitor.IVisitor;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddTourLogController implements Initializable, IController {

    @FXML
    public ChoiceBox<String> startHourChoiceBox;
    @FXML
    public ChoiceBox<String> startMinutesChoiceBox;
    @FXML
    public Slider ratingSlider;
    @FXML
    public DatePicker datePicker;
    @FXML
    public TextField logDuration;
    @FXML
    public TextField logDistance;
    @FXML
    public TextField avgSpeed;
    @FXML
    public ChoiceBox<String> sportChoiceBox;
    @FXML
    public Label chosenRating = new Label();
    @FXML
    public Button addLogBtn;
    @FXML
    public Button editLogBtn;
    @FXML
    public HBox dateHBOX;
    @FXML
    public HBox startHBOX;
    @FXML
    public TextField report;
    @FXML
    public HBox reportHBOX;
    @FXML
    public HBox alertHBOX;

    public Label alertLabel;

    private AddLogViewModel viewModel;


    @FXML
    public void addTourLog(ActionEvent actionEvent) {
        if (viewModel.addTourLog() > 0)
            closeDialog(actionEvent);
    }

    @FXML
    public void editTourLog(ActionEvent actionEvent) {
        int editedLogId = viewModel.editTourLog();
        if (editedLogId > 0)
            closeDialog(actionEvent);
    }

    @FXML
    public void closeDialog(@NotNull ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        viewModel.setEditMode(false);
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
        report.textProperty().bindBidirectional(viewModel.getReport());
        avgSpeed.textProperty().bindBidirectional(viewModel.getAvg());
        sportChoiceBox.setItems(viewModel.getAllSports());
        sportChoiceBox.valueProperty().bindBidirectional(viewModel.getSport());
        chosenRating.textProperty().bindBidirectional(viewModel.getRatingDisplay());
        startHourChoiceBox.setItems(viewModel.getAllHour());
        startHourChoiceBox.valueProperty().bindBidirectional(viewModel.getHour());
        startMinutesChoiceBox.setItems(viewModel.getAllMinutes());
        startMinutesChoiceBox.valueProperty().bindBidirectional(viewModel.getMinutes());

        alertLabel.textProperty().bindBidirectional(viewModel.getAlertMessage());
        alertLabel.visibleProperty().bind(viewModel.getAlertMessage().isNotEqualTo(""));
    }

    @Override
    public void accept(IVisitor visitor, IViewModel viewModel) {
        visitor.visit(this, viewModel);
    }

    public void initViewModel(IViewModel previousViewModel) {
        viewModel.init(previousViewModel);

        if (viewModel.getTourViewModel().getEditMode().get() && viewModel.getCurrentTourLog() != null) {
            ratingSlider.setValue(viewModel.getCurrentTourLog().getRating());
            avgSpeed.setText(String.valueOf(viewModel.getCurrentTourLog().getAvgSpeed()));
            logDistance.setText(String.valueOf(viewModel.getCurrentTourLog().getDistance()));
            logDuration.setText(String.valueOf(viewModel.getCurrentTourLog().getDuration()));
            sportChoiceBox.setValue(viewModel.getCurrentTourLog().getSport().toString());
            ratingSlider.setValue(viewModel.getCurrentTourLog().getRating());
        }

        dateHBOX.visibleProperty().bind(viewModel.getTourViewModel().getEditMode().not());
        startHBOX.visibleProperty().bind(viewModel.getTourViewModel().getEditMode().not());
        addLogBtn.visibleProperty().bind(viewModel.getTourViewModel().getEditMode().not());
        reportHBOX.visibleProperty().bind(viewModel.getTourViewModel().getEditMode().not());
        editLogBtn.visibleProperty().bind(viewModel.getTourViewModel().getEditMode());
    }


}
