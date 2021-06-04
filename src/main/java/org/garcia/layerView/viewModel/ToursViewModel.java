package org.garcia.layerView.viewModel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import lombok.Data;
import org.garcia.config.ConfigurationManager;
import org.garcia.layerBusiness.appmanager.AppManagerDB;
import org.garcia.layerBusiness.appmanager.AppManagerFactory;
import org.garcia.layerBusiness.appmanager.AppManagerMock;
import org.garcia.layerBusiness.appmanager.IAppManager;
import org.garcia.layerBusiness.util.SecondsToTime;
import org.garcia.layerDataAccess.common.DALOracleFactory;
import org.garcia.layerDataAccess.common.DALPostgresFactory;
import org.garcia.layerDataAccess.common.IDALFactory;
import org.garcia.model.Tour;
import org.garcia.model.TourLog;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Data
public class ToursViewModel implements IViewModel {

    private IAppManager appManager;
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

    //menu buttons
    private BooleanProperty menuItemDisabled = new SimpleBooleanProperty();

    public static ToursViewModel getInstance() {
        if (viewModel == null) {
            viewModel = new ToursViewModel();
        }
        return viewModel;
    }

    public void setCurrentTour(Tour tour) {
        currentTour = tour;
        setCurrentTourDescription();
    }

    public void searchTours(String inputSearch) {
        tourObservableList.clear();
        tourLogObservableList.clear();

        List<Tour> foundTours = appManager.searchTours(inputSearch);
        if (foundTours == null) {
            tourObservableList.addAll();
        } else {
            tourObservableList.addAll(foundTours);
        }
        menuItemDisabled.setValue(tourObservableList.size() == 0);
    }

    public void clearObservableLists() {
        tourObservableList.clear();
        tourLogObservableList.clear();
        currentTourDescription.set("");
        String defaultImg = ConfigurationManager.getConfigProperty("defaultImg");
        tourImageProperty.set(new Image(defaultImg)); //TODO
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
        }

        else
            currentTourDescription.setValue("");

    }

    public void defineAppManager() {
        boolean mockedDB = Boolean.parseBoolean(ConfigurationManager.getConfigProperty("mockedDB"));
        String dbType = ConfigurationManager.getConfigProperty("dbType");
        IDALFactory factory;

        switch (dbType) {
            case "postgres":
                factory = new DALPostgresFactory();
                break;
            case "oracle":
                factory = new DALOracleFactory(); // not implemented only as example
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dbType);
        }
        if (!mockedDB) {
            this.appManager = AppManagerFactory.getInstance(new AppManagerDB(factory));
        } else {
            this.appManager =  AppManagerFactory.getInstance(new AppManagerMock(factory));
        }
    }

    public void deleteTour() {
        appManager.deleteTour(currentTour);
        currentTour = null;
    }

    public void exportTour() throws IOException {
        appManager.exportTourNLogs("data", "dir");
    }

    public void importTour() throws IOException {
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
        int logId = currentLog.getId();
        if (!appManager.deleteLogById(logId)) {
            System.out.println("Todo. Show alerts"); // TODO: alerts
        } else {
            searchLogsByTourId();
            System.out.println("delete: " + logId);
        }
    }

    public void tourSelected(Tour newTour) {
        setCurrentTour(newTour);
        searchLogsByTourId();
        String tourUrl = newTour.getImg();
        System.out.println("Current tour: " + tourUrl);
        try {
            tourImageProperty.set(new Image(tourUrl));
        } catch (Exception e) {
//            logger.log(Level.ERROR, e);
            System.out.println("Couldn't load img"); //TODO
        }
        // tourImageView.setImage(new Image(toursViewModel.getCurrentTour().getImg()));
    }

    public int editNewTour(String title, String description) {
        currentTour.setTitle(title);
        currentTour.setDescription(description);
        return appManager.editTour(currentTour);
    }

    @Override
    public void init(IViewModel viewModel) {
        appManager.searchTours("");
        System.out.println("TourViewModel init");
    }
}
