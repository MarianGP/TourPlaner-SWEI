package org.garcia.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import lombok.Data;

import java.util.List;

@Data
public class ToursViewModel {

    //tours
    private Tour currentTour = Tour.builder().description("").origin("").title("").build();
    private Tour newTour;
    private ObservableList<Tour> tourObservableList = FXCollections.observableArrayList();

    //logs
    private TourLog currentLog;
    private TourLog newLog;
    private ObservableList<TourLog> tourLogObservableList = FXCollections.observableArrayList();

    private StringProperty duration = new SimpleStringProperty("");
    private StringProperty distance = new SimpleStringProperty("");
    private StringProperty date = new SimpleStringProperty("");

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

    public void setCurrentLog(TableView<TourLog> logsTableView) { //to delete log usefull
        logsTableView.getSelectionModel().selectedItemProperty().addListener(
                (((observableValue, oldLog, newLog) -> {
                    if (newLog != null) {
                        currentLog = newLog;
                    }
                    System.out.println(currentLog);
                }))
        );
    }

    public void addTourLogs(List<TourLog> allLogs) {
        if (allLogs == null) {
            tourLogObservableList.addAll();
        } else {
            tourLogObservableList.addAll(allLogs);
        }
    }

    public void addOneTourLog(TourLog log) {
        tourLogObservableList.add(log);
    }

    public void clearLogsObservableList() {
        tourLogObservableList.clear();
    }
}
