package org.garcia.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ToursViewModel {

    private static ToursViewModel viewModel;

    //tours
    private Tour currentTour;
    private Tour newTour;
    private ObservableList<Tour> tourObservableList = FXCollections.observableArrayList();

    //logs
    private TourLog currentLog;
    private TourLog newLog;
    private ObservableList<TourLog> tourLogObservableList = FXCollections.observableArrayList();

    //new log
    private StringProperty duration = new SimpleStringProperty("");
    private StringProperty distance = new SimpleStringProperty("");
    private StringProperty date = new SimpleStringProperty("");

    //time
    private LocalDate localDate;

    //tour methods
    public void addAllToursObsList(List<Tour> foundTours) {
        if (foundTours == null) {
            tourObservableList.addAll();
        } else {
            tourObservableList.addAll(foundTours);
        }
    }

    public void clearObservableList() {
        tourObservableList.clear();
    }

    public void addTourLogs(List<TourLog> allLogs) {
        if (allLogs == null) {
            tourLogObservableList.addAll();
        } else {
            tourLogObservableList.addAll(allLogs);
        }
    }

    public void clearLogsObservableList() {
        tourLogObservableList.clear();
    }

    public static ToursViewModel getInstance() {
        if (viewModel == null)
            viewModel = new ToursViewModel();
        return viewModel;
    }
}
