package org.garcia.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import lombok.Data;

import java.util.List;

@Data
public class ToursViewModel {

    private ObservableList<Tour> tourObservableList = FXCollections.observableArrayList();
    private Tour currentTour;
    private Tour newTour;

    private ObservableList<TourLog> tourLogObservableList = FXCollections.observableArrayList();


    public void clearObservableList() {
        tourObservableList.clear();
    }

    public void addAllToursObsList(List<Tour> foundTours) {
        if (foundTours == null) {
            tourObservableList.addAll();
        } else {
            tourObservableList.addAll(foundTours);
        }
    }

    public void setCurrentTour(ListView<Tour> tourListView) {
        tourListView.getSelectionModel().selectedItemProperty().addListener(
                ((observableValue, oldTour, newTour) -> {
                    if ((newTour != null) && (oldTour != newTour)) {
                        currentTour = newTour;
                    }
                })
        );
    }
}
