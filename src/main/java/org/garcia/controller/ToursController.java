package org.garcia.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import org.garcia.App;
import org.garcia.layerbusiness.appmanager.AppManagerFactory;
import org.garcia.layerbusiness.appmanager.AppManagerMock;
import org.garcia.layerbusiness.appmanager.IAppManager;
import org.garcia.model.enums.ViewName;
import org.garcia.model.Tour;
import org.garcia.model.ToursViewModel;

import java.io.IOException;
import java.net.URL;
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

    //new log
    public TableColumn date;
    public TableColumn duration;
    public TableColumn distance;

    //tours
    public ListView<Tour> toursListView = new ListView<>();
    public TextField inputSearch;

    public void searchTours(ActionEvent actionEvent) {
        toursViewModel.clearObservableList();
        List<Tour> foundTours = tourManager.searchTours(inputSearch.textProperty().getValue(), false);
        toursViewModel.addAllToursObsList(foundTours);
    }

    public void clearTours(ActionEvent actionEvent) {
        toursViewModel.clearObservableList();
        inputSearch.textProperty().setValue("");
    }

    public void addTour(ActionEvent actionEvent) {
        tourManager.addTour(toursViewModel.getCurrentTour());
    }

    public void deleteTour(ActionEvent actionEvent) {
        tourManager.deleteTour(1);
    }

    @FXML
    public void openAddTourDialog(ActionEvent actionEvent) throws IOException {
        App.openDialog(ViewName.ADDTOUR.getViewName(), "Add Tour");
    }

    @FXML
    public void closeAddTourDialog(ActionEvent actionEvent) {
        App.closeDialog(actionEvent);
    }

    @FXML
    public void switchToPrimary() throws IOException {
        App.setRoot(ViewName.HOME.getViewName());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tourManager = AppManagerFactory.getInstance(new AppManagerMock());

        //tours
        toursViewModel.addAllToursObsList(null);
        toursListView.setItems(toursViewModel.getTourObservableList());

        setTourListViewFormatCells();
        toursViewModel.setCurrentTour(toursListView);

        //
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
                }
            }
        }));
    }
}
