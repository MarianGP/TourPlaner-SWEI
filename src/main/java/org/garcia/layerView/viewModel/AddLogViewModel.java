package org.garcia.layerView.viewModel;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Data;
import org.garcia.layerBusiness.appmanager.IAppManager;
import org.garcia.model.Tour;
import org.garcia.model.TourLog;
import org.garcia.model.enums.Sport;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class AddLogViewModel implements IViewModel {

    private static AddLogViewModel viewModel;
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

    public static AddLogViewModel getInstance() {
        if (viewModel == null) {
            viewModel = new AddLogViewModel();
        }
        return viewModel;
    }

    public int addTourLog() {
        //validate
        TourLog log = validateLog();
        if (log == null) {
            alertMessage.set("Wrong input");
            return -1;
        }

        int tourLogId = appManager.addLog(log);
        if (tourLogId != 0) {
            tourViewModel.searchTours("");
            tourViewModel.tourSelected(tourViewModel.getCurrentTour());
            alertMessage.set("");
        } else {
            alertMessage.set("Wrong input");
        }
        return tourLogId;
    }

    public int editTourLog() {
        TourLog log = validateLog();
        if (log == null) {
            alertMessage.set("Wrong input");
            return -1;
        }
        log.setId(tourViewModel.getCurrentTourLog().getId());
        int tourLogId = appManager.editLog(log);
        if (tourLogId != 0) {
            tourViewModel.searchTours("");
            tourViewModel.tourSelected(tourViewModel.getCurrentTour());
            alertMessage.set("");
        } else {
            alertMessage.set("Something went wrong");
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
        } catch (RuntimeException e) {
            System.out.println(e);
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

    public void setEditMode(boolean b) {
        tourViewModel.getEditMode().set(b);
    }
}
