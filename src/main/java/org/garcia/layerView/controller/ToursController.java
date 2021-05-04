package org.garcia.layerView.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.garcia.App;
import org.garcia.layerbusiness.appmanager.AppManagerDB;
import org.garcia.layerbusiness.appmanager.AppManagerFactory;
import org.garcia.layerbusiness.appmanager.IAppManager;
import org.garcia.model.Tour;
import org.garcia.model.TourLog;
import org.garcia.layerView.viewModel.ToursViewModel;
import org.garcia.model.enums.ViewName;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ToursController implements Initializable {

    IAppManager tourManager;
    ToursViewModel toursViewModel;

    //new tour
    public TextField title;
    public TextField origin;
    public TextField destination;
    public TextField description;

    //tours
    public ListView<Tour> toursListView = new ListView<>();
    public TextField inputSearch = new TextField("");

    //selected tour
    public ImageView tourImageView;
    public Text tourDescription;

    //new log
    public TilePane datePicker = new TilePane();
    public TextField logDuration;
    public TextField logDistance;
    public Button addLogBtn = new Button();
    public Button deleteLogBtn = new Button();

    //logs
    public TableView<TourLog> tourLogTableView = new TableView<>();
    public TableColumn<TourLog, LocalDate> dateColumn = new TableColumn<>();
    public TableColumn<TourLog, Integer> durationColumn = new TableColumn<>();
    public TableColumn<TourLog, Integer> distanceColumn = new TableColumn<>();

    public void searchTours(ActionEvent actionEvent) throws SQLException {
        toursViewModel.clearObservableList();
        toursViewModel.clearLogsObservableList();
        List<Tour> foundTours = tourManager.searchTours(inputSearch.textProperty().getValue(), false);
        toursViewModel.addAllToursObsList(foundTours);
        inputSearch.textProperty().setValue("");
    }

    public void clearTours(ActionEvent actionEvent) {
        toursViewModel.clearObservableList();
        toursViewModel.clearLogsObservableList();
        inputSearch.textProperty().setValue("");
        tourDescription.textProperty().setValue("");
        tourImageView.setImage(null);
    }

    public void addTour(ActionEvent actionEvent) throws SQLException, IOException {
        String[] inputFields = {title.getText(), origin.getText(), destination.getText(), description.getText()};
        int tourId = tourManager.addTour(inputFields);
        if (tourId > 0) {
            searchTours(null);
//            toursViewModel.setCurrentTour(toursViewModel.getTourObservableList().get(toursViewModel.getTourObservableList().size()-1));
        } else {
            System.out.println("Wrong input");
        }
        closeDialog(actionEvent);
    }

    public void deleteTour(ActionEvent actionEvent) throws SQLException {
        tourManager.deleteTour(toursViewModel.getCurrentTour());
        searchTours(null);
    }

    public void searchTourLogs(int tourId) throws SQLException {
        toursViewModel.clearLogsObservableList();
        List<TourLog> foundTourLogs = tourManager.searchLogs(tourId);
        if (foundTourLogs != null) {
            toursViewModel.addTourLogs(foundTourLogs);
            addLogBtn.setDisable(false);
        } else {
            addLogBtn.setDisable(true);
        }
    }

    public void addLog(ActionEvent actionEvent) throws SQLException {
        int tourId = toursViewModel.getCurrentTour().getId();
        Object[] inputFields = {
                toursViewModel.getLocalDate(),
                Integer.parseInt(logDuration.getText()),
                Integer.parseInt(logDistance.getText())};
        if (tourManager.addLog(inputFields, tourId)) {
            closeDialog(actionEvent);
            searchTourLogs(tourId);
        } else {
            System.out.println("Wrong input");
        }
    }

    public void deleteLogTour(ActionEvent actionEvent) throws SQLException {
        int logId = toursViewModel.getCurrentLog().getId();
        if(!tourManager.deleteLog(logId)) {
            //show alert
        }
        searchTourLogs(toursViewModel.getCurrentLog().getTourId());
        deleteLogBtn.setDisable(true);
        System.out.println("delete: " + logId);
    }

    @FXML
    public void openAddTourDialog(ActionEvent actionEvent) throws IOException {
        DatePicker dp = new DatePicker();
        datePicker.getChildren().add(dp);
        App.openDialog(ViewName.ADD_TOUR.getViewName(), "Add Tour");
    }

    @FXML
    public void openAddLogDialog(ActionEvent actionEvent) throws IOException {
        if (toursViewModel.getCurrentTour().getOrigin() != null) {
            App.openDialog(ViewName.ADD_LOG.getViewName(), "Add Tour");
        }
    }

    public void closeDialog(@NotNull ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        toursViewModel = ToursViewModel.getInstance();
        // tourManager = AppManagerFactory.getInstance(new AppManagerMock());
        tourManager = AppManagerFactory.getInstance(new AppManagerDB());
        initializeTours();
        initializeLogs();
    }

    private void initializeLogs() {
        setDatePicker();
        selectTourLogListener();
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
        tourLogTableView.setItems(toursViewModel.getTourLogObservableList());
    }

    private void initializeTours() throws SQLException {
        toursListView.setItems(toursViewModel.getTourObservableList());
        setTourListViewFormatCells();
        selectTourListener();
        toursViewModel.clearObservableList();
        toursViewModel.addAllToursObsList(tourManager.searchTours("", false));
    }

    public void selectTourListener() {
        toursListView.getSelectionModel().selectedItemProperty().addListener(
                ((observableValue, oldTour, newTour) -> {
                    if ((newTour != null) && (oldTour != newTour) && (newTour.getId() != 0)) {
                        toursViewModel.setCurrentTour(newTour);
                        setTourDescription();

                        try {
                            searchTourLogs(toursViewModel.getCurrentTour().getId());
                            System.out.println("Current tour: " + toursViewModel.getCurrentTour().getImg());

                            tourImageView.setImage(new Image(toursViewModel.getCurrentTour().getImg())); // TODO: ask
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                })
        );
    }

    public void selectTourLogListener() {
        tourLogTableView.getSelectionModel().selectedItemProperty().addListener(
                (((observableValue, oldLog, newLog) -> {
                    if (newLog != null && (oldLog != newLog)) {
                        toursViewModel.setCurrentLog(newLog);
                        deleteLogBtn.setDisable(false);
                    }
                    System.out.println("current log: " + toursViewModel.getCurrentLog());
                }))
        );
    }

    private void setTourDescription() {
        tourDescription.setText(
                "Title: \t\t" + toursViewModel.getCurrentTour().getTitle() + "\n" +
                        "Description: \t" + toursViewModel.getCurrentTour().getDescription() + "\n" +
                        "Origin: \t\t" + toursViewModel.getCurrentTour().getOrigin() + "\n" +
                        "Destination: \t" + toursViewModel.getCurrentTour().getDestination() + "\n" +
                        "Duration: \t\t" + toursViewModel.getCurrentTour().getDuration()
        );
    }

    private void setDatePicker() {
        Label l = new Label("no date selected");
        DatePicker dp = new DatePicker();

        EventHandler<ActionEvent> event = e -> {
            LocalDate ld = dp.getValue();
            toursViewModel.setLocalDate(ld);
            l.setText(ld.toString());
        };

        // show week numbers
        dp.setShowWeekNumbers(true);

        // when datePicker is pressed
        dp.setOnAction(event);

        // add button and label
        datePicker.getChildren().add(dp);
        datePicker.getChildren().add(l);
    }

    public void setTourListViewFormatCells() {
        toursListView.setCellFactory((param -> new ListCell<>() {
            @Override
            protected void updateItem(Tour tour, boolean empty) {
                super.updateItem(tour, empty);
                if (empty || (tour == null) || tour.getTitle() == null) { // TODO: validation in business layer
                    setText(null);
                } else {
                    setText(tour.getTitle() + ": " + tour.getOrigin() + " -> " + tour.getDestination());
                    setId(String.valueOf(tour.getId()));
                }
            }
        }));
    }


}
