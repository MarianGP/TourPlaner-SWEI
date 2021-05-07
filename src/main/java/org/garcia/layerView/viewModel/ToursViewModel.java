package org.garcia.layerView.viewModel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import lombok.Data;
import org.garcia.model.Tour;
import org.garcia.model.TourLog;

import java.time.LocalDate;
import java.util.List;

@Data
public class ToursViewModel {

    private static ToursViewModel viewModel;

    //tours
    private Tour currentTour;
    private ObservableList<Tour> tourObservableList = FXCollections.observableArrayList();
    private ObjectProperty<Image> tourImageProperty = new SimpleObjectProperty<>();
    private StringProperty currentTourDescription = new SimpleStringProperty("");

    //logs
    private TourLog currentLog;
    private ObservableList<TourLog> tourLogObservableList = FXCollections.observableArrayList();

    //new log
    private StringProperty duration = new SimpleStringProperty("");
    private StringProperty distance = new SimpleStringProperty("");
    private StringProperty date = new SimpleStringProperty("");

    //time
    private LocalDate localDate;

    //report
    private StringProperty reportTypeName = new SimpleStringProperty();
    private StringProperty reportUrl = new SimpleStringProperty();
    private StringProperty reportName = new SimpleStringProperty();

    private final String tourReportName = "tour";
    private final String summaryReportName = "summary";

    //menu buttons
    private BooleanProperty menuItemDisabled = new SimpleBooleanProperty();

    public void setCurrentTour(Tour tour) {
        currentTour = tour;
        setCurrentTourDescription();
    }

    public void addAllToursObsList(List<Tour> foundTours) {
        if (foundTours == null) {
            tourObservableList.addAll();
        } else {
            tourObservableList.addAll(foundTours);
        }
        menuItemDisabled.setValue(tourObservableList.size() == 0);
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

    private void setCurrentTourDescription() {
        if(currentTour != null)
            currentTourDescription.setValue(
                    "Title: \t\t" + currentTour.getTitle() + "\n" +
                            "Description: \t" + currentTour.getDescription() + "\n" +
                            "Origin: \t\t" + currentTour.getOrigin() + "\n" +
                            "Destination: \t" + currentTour.getDestination() + "\n" +
                            "Duration: \t\t" + currentTour.getDuration()
            );
        else
            currentTourDescription.setValue("");

    }
}
