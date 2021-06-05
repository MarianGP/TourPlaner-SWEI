package org.garcia.layerView.viewModel;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.garcia.layerBusiness.IAppManager;
import org.garcia.model.Tour;
import org.garcia.model.TourLog;
import org.garcia.model.enums.Sport;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class TourLogFormViewModel implements IViewModel {

    private static final Logger logger = LogManager.getLogger(TourLogFormViewModel.class);
    private static final String LOG_ADDED = "Tour added";
    private static final String LOG_EDITED = "Tour edited";
    private static final String LOG_ADDED_FAIL = "Couldn't add tour";
    private static final String LOG_EDIT_FAIL = "Couldn't edit tour";
    private static final String WRONG_INPUT = "Wrong input";

    private static TourLogFormViewModel viewModel;
    private ToursViewModel tourViewModel;
    private IAppManager appManager;
    private LocalDate localDate;
    private Tour currentTour;
    private TourLog currentTourLog;

    private final ObservableList<String> allSports = FXCollections.observableArrayList(Sport.getAllSport());

    private final ObservableList<String> allHour = FXCollections.observableArrayList(
                    IntStream.range(0, 23).boxed().map(Object::toString).collect(Collectors.toList()));

    private final ObservableList<String> allMinutes = FXCollections.observableArrayList(
                    IntStream.range(0, 59).boxed().map(Object::toString).collect(Collectors.toList()));

    private StringProperty sport = new SimpleStringProperty(Sport.getAllSport().get(0));
    private StringProperty duration = new SimpleStringProperty("");
    private StringProperty distance = new SimpleStringProperty("");
    private StringProperty special = new SimpleStringProperty("");
    private StringProperty avg = new SimpleStringProperty("");
    private StringProperty date = new SimpleStringProperty(LocalDate.now().toString());
    private StringProperty hour = new SimpleStringProperty("09");
    private StringProperty minutes = new SimpleStringProperty("30");
    private DoubleProperty rating = new SimpleDoubleProperty(1.0);
    private StringProperty ratingDisplay = new SimpleStringProperty();
    private StringProperty report = new SimpleStringProperty("");

    private StringProperty alertMessage = new SimpleStringProperty();

    public static TourLogFormViewModel getInstance() {
        if (viewModel == null) {
            viewModel = new TourLogFormViewModel();
        }
        return viewModel;
    }

    public int addTourLog() {
        TourLog tourLog = validateLog();
        if (tourLog == null) {
            return -1;
        }

        int tourLogId = appManager.addLog(tourLog);
        if (tourLogId != 0) {
            tourViewModel.searchTours();
            tourViewModel.tourSelected(tourViewModel.getCurrentTour());
            alertMessage.set("");
            logger.info(LOG_ADDED);
        } else {
            logger.error(LOG_ADDED_FAIL);
        }
        return tourLogId;
    }

    public int editTourLog() {
        TourLog tourLog = validateLog();
        if (tourLog == null) {
            return -1;
        }
        tourLog.setId(tourViewModel.getCurrentTourLog().getId());
        int tourLogId = appManager.editLog(tourLog);
        if (tourLogId != 0) {
            tourViewModel.searchTours();
            tourViewModel.getEditMode().set(false);
            tourViewModel.tourSelected(tourViewModel.getCurrentTour());
            alertMessage.set("");
            logger.info(LOG_EDITED);
        } else {
            logger.info(LOG_EDIT_FAIL);
        }
        return tourLogId;
    }

    public TourLog validateLog() {
        int durationInt, distanceInt, specialInt, avgInt, userId = 0;
        LocalTime startTime;
        LocalDate dateInput;

        try {
            durationInt = Integer.parseInt(duration.getValue());
            distanceInt = Integer.parseInt(distance.getValue());
            specialInt = (!special.getValue().equals("")) ? Integer.parseInt(special.getValue()) : 0;
            avgInt = (avg != null) ? Integer.parseInt(avg.getValue()) : 0;
            dateInput = (!date.getValue().equals("")) ? LocalDate.parse(date.getValue()) : LocalDate.now();
        } catch (Exception e) {
            alertMessage.set(WRONG_INPUT);
            logger.warn(WRONG_INPUT);
            return null;
        }

        if (!hour.getValue().equals("") && !minutes.getValue().equals(""))
            startTime = formatDate(hour.getValue(), minutes.getValue());
        else
            startTime = currentTourLog.getStart();

        return TourLog.builder()
                .tourId(currentTour.getId())
                .date(dateInput)
                .duration(durationInt)
                .distance(distanceInt)
                .rating((int) Math.round(rating.getValue()))
                .sport(Sport.valueOf(sport.getValue()))
                .avgSpeed(avgInt)
                .start(startTime)
                .end(LocalTime.now())
                .special(specialInt)
                .userId(userId)
                .report(report.getValue())
                .build();
    }

    private LocalTime formatDate(String aHour, String aMinutes) {
        String theHours = ((aHour.length() == 1) ? "0" : "") + aHour;
        String theMinutes = ((aMinutes.length() == 1) ? "0" : "") + aMinutes;
        String time = theHours + ":" + theMinutes;
        return LocalTime.parse(time);
    }

    public void init(IViewModel previousViewModel) {
        tourViewModel = (ToursViewModel) previousViewModel;
        currentTourLog = previousViewModel.getCurrentTourLog();
        appManager = previousViewModel.getAppManager();
        if (currentTour == null)
            currentTour = previousViewModel.getCurrentTour();
    }

}
