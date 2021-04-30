package org.garcia.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.garcia.App;
import org.garcia.layerbusiness.appmanager.AppManagerFactory;
import org.garcia.layerbusiness.appmanager.AppManagerMock;
import org.garcia.layerbusiness.appmanager.IAppManager;
import org.garcia.model.Tour;
import org.garcia.model.TourLog;
import org.garcia.model.ToursViewModel;
import org.garcia.model.enums.ViewName;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ToursController implements Initializable {

    IAppManager tourManager;
    ToursViewModel toursViewModel = new ToursViewModel();

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
    public TableView<TourLog> tourLogTableView = new TableView<>();
    public TableColumn<TourLog, LocalDate> dateColumn = new TableColumn<>();
    public TableColumn<TourLog, Integer> durationColumn = new TableColumn<>();
    public TableColumn<TourLog, Integer> distanceColumn = new TableColumn<>();

    public void searchTours(ActionEvent actionEvent) {
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
    }

    public void addTour(ActionEvent actionEvent) {
        String[] inputFields = {title.getText(), origin.getText(), destination.getText(), description.getText()};
        if (tourManager.addTour(inputFields)) {
            closeDialog(actionEvent);
            System.out.println("Added");
            searchTours(null);
        } else {
            System.out.println("Wrong input");
        }
    }

    public void deleteTour(ActionEvent actionEvent) {
        int idTourToDelete = toursViewModel.getCurrentTour().getId();
        tourManager.deleteTour(idTourToDelete);
        searchTours(null);
    }

    public void searchTourLogs(int tourId) {
        toursViewModel.clearLogsObservableList();
        List<TourLog> foundTourLogs = tourManager.searchLogs(tourId);
        toursViewModel.addTourLogs(foundTourLogs);
    }

    @FXML
    public void openAddTourDialog(ActionEvent actionEvent) throws IOException {
        App.openDialog(ViewName.ADDTOUR.getViewName(), "Add Tour");
    }

    public void closeDialog(@NotNull ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tourManager = AppManagerFactory.getInstance(new AppManagerMock());
        initializeTours();
        initializeLogs();
    }

    private void initializeLogs() {
        toursViewModel.setCurrentLog(tourLogTableView);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
        tourLogTableView.setItems(toursViewModel.getTourLogObservableList());
    }

    private void initializeTours() {
        toursListView.setItems(toursViewModel.getTourObservableList());
        setTourListViewFormatCells();
        selectTourListener();
        toursViewModel.addAllToursObsList(tourManager.searchTours("", false));
    }

    public void selectTourListener() {
        toursListView.getSelectionModel().selectedItemProperty().addListener(
                ((observableValue, oldTour, newTour) -> {
                    if ((newTour != null) && (oldTour != newTour)) {
                        toursViewModel.setCurrentTour(newTour);
                        setTourDescription();
                        searchTourLogs(toursViewModel.getCurrentTour().getId());
                        tourImageView.setImage(new Image(toursViewModel.getCurrentTour().getImg()));
                    }
                    System.out.println(toursViewModel.getCurrentTour());
                })
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
