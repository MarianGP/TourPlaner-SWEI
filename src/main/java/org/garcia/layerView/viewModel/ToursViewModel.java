package org.garcia.layerView.viewModel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.garcia.AppConfiguration;
import org.garcia.layerBusiness.IAppManager;
import org.garcia.model.Tour;
import org.garcia.model.TourDirection;
import org.garcia.model.TourLog;
import org.garcia.model.util.SecondsToTime;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

@Data
public class ToursViewModel implements IViewModel {

    private static final Logger logger = LogManager.getLogger(ToursViewModel.class);
    private static final String WRONG_INPUT = "Wrong input";
    private static final String DB_POSTGRES = "postgres";
    private static final String DB_ORACLE = "oracle";
    private static final String DEFAULT_IMG = "org/garcia/img/dummy.jpg";

    private static ToursViewModel viewModel;
    private IAppManager appManager;
    private LocalDate localDate;

    //tours
    private Tour currentTour;
    private ObservableList<Tour> tourObservableList = FXCollections.observableArrayList();
    private ObservableList<TourLog> tourLogObservableList = FXCollections.observableArrayList();
    private ObservableList<TourDirection> tourDirections = FXCollections.observableArrayList();
    private ObjectProperty<Image> tourImageProperty = new SimpleObjectProperty<>();
    private StringProperty currentTourDescription = new SimpleStringProperty("");

    //log
    private TourLog currentTourLog;
    private StringProperty duration = new SimpleStringProperty("");
    private StringProperty distance = new SimpleStringProperty("");
    private StringProperty date = new SimpleStringProperty("");
    private StringProperty inputSearch = new SimpleStringProperty("");

    // disabled elements
    private BooleanProperty editMode = new SimpleBooleanProperty(false);
    private BooleanProperty menuItemDisabled = new SimpleBooleanProperty();

    public static ToursViewModel getInstance() {
        if (viewModel == null) {
            viewModel = new ToursViewModel();
        }
        return viewModel;
    }

    /**
     * appManager is initialized depending on config file setup
     */
    public void defineAppManager() {
        this.appManager = AppConfiguration.initAppConfiguration();
    }

    public void setCurrentTour(Tour tour) {
        currentTour = tour;
        setCurrentTourDescription();
    }

    public void searchTours() {
        tourObservableList.clear();
        tourLogObservableList.clear();
        if (inputSearch.getValue().length() < 180) {
            List<Tour> foundTours = appManager.searchTours(inputSearch.getValue());
            if (foundTours == null)
                tourObservableList.addAll();
            else
                tourObservableList.addAll(foundTours);

            menuItemDisabled.setValue(tourObservableList.size() == 0);
        }
    }

    public void clearObservableLists() {
        tourObservableList.clear();
        tourLogObservableList.clear();
        currentTourDescription.set("");
        tourImageProperty.set(new Image(DEFAULT_IMG)); //TODO
    }

    private void setCurrentTourDescription() {
        if (currentTour != null) {
            currentTourDescription.setValue(
                    "Title: \t\t" + currentTour.getTitle() + "\n" +
                    "Description: \t" + currentTour.getDescription() + "\n" +
                    "Origin: \t\t" + currentTour.getOrigin() + "\n" +
                    "Destination: \t" + currentTour.getDestination() + "\n" +
                    "Duration: \t\t" + SecondsToTime.convertSecToTime(currentTour.getDuration()) + "\n" +
                    "Distance: \t\t" + currentTour.getDistance()
            );
        } else {
            currentTourDescription.setValue("");
        }
    }

    public void deleteTour() {
        appManager.deleteTour(currentTour);
        currentTour = null;
    }

    public void exportTour() {
        appManager.exportTourNLogs("data", "dir");
    }

    public void importTour() {
        appManager.importTourNLogs("testing-import", "dir");
    }

    public void searchLogsByTourId() {
        List<TourLog> foundTourLogs = appManager.searchLogsByTourId(currentTour.getId());
        tourLogObservableList.clear();
        if (foundTourLogs.size() != 0) {
            tourLogObservableList.addAll(foundTourLogs);
        }
    }

    public void deleteTourLog() {
        int logId = currentTourLog.getId();
        if (!appManager.deleteLogById(logId)) {
            logger.error("Couldn't delete item");
        } else {
            searchLogsByTourId();
            logger.error("Tour Log with id: " + logId + " deleted from db");
        }
    }

    public void tourSelected(Tour newTour) {
        if (newTour != null) {
            setCurrentTour(newTour);
            searchLogsByTourId();
            String tourUrl = newTour.getImg();
            File file = new File("src\\main\\resources\\" + tourUrl);
            Image img = new Image(file.toURI().toString());
            try {
                tourImageProperty.setValue(img);
            } catch (Exception e) {
                System.out.println("Couldn't load img" + e); //TODO
            }
            tourDirections.clear();
            tourDirections.addAll(appManager.searchDirections(currentTour.getId()));
        }
    }

    @Override
    public void init(IViewModel previousViewModel) {
        appManager.searchTours("");
        currentTour = previousViewModel.getCurrentTour();
        System.out.println("TourViewModel init");
    }
}
