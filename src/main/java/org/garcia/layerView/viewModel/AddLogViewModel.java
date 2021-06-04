package org.garcia.layerView.viewModel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.effect.Effect;
import lombok.Data;
import org.garcia.layerBusiness.appmanager.IAppManager;
import org.garcia.model.Tour;
import org.garcia.model.TourLog;
import org.garcia.model.enums.Sport;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AddLogViewModel implements IViewModel {

    private static AddLogViewModel viewModel;

    private Tour currentTour;
    private LocalDate localDate;
    private IAppManager appManager;

    private final ObservableList<String> allSports =
            FXCollections.observableArrayList(Sport.getAllSport());

//    private final ObservableList<String> allMoods =
//            FXCollections.observableArrayList("Happy","Grumpy","Disappointed");
//
//    private final ObservableList<String> allWeather =
//            FXCollections.observableArrayList("Rainy","Sunny","Cloudy","Snowing");
//
//    private final ObservableList<String> allDifficulty =
//            FXCollections.observableArrayList("Easy","Moderate","Difficult","Insane");

//    private ObjectProperty<Effect> sportEffectProp = new SimpleObjectProperty<>();
    private ObjectProperty<Effect> moodEffectProp = new SimpleObjectProperty<>();
//    private StringProperty sport = new SimpleStringProperty();
//    private StringProperty mood = new SimpleStringProperty();
//    private StringProperty difficulty = new SimpleStringProperty();
    private StringProperty duration = new SimpleStringProperty();
    private StringProperty distance = new SimpleStringProperty();
    private StringProperty special = new SimpleStringProperty();
    private StringProperty avg = new SimpleStringProperty();
    private StringProperty date = new SimpleStringProperty("");
    private DoubleProperty rating = new SimpleDoubleProperty();

    public static AddLogViewModel getInstance() {
        if (viewModel == null) {
            viewModel = new AddLogViewModel();
        }
        return viewModel;
    }

    public int addTourLog() {
        //validate
        TourLog log = validateLog();
        int tourLogId = appManager.addLog(log);
        if (tourLogId != 0) {
            System.out.println("Log added");
//            searchLogsByTourId();
        } else {
            System.out.println("Wrong input");
        }
        return tourLogId;
    }

    public TourLog validateLog() {

        int durationInt = 0, distanceInt = 0, avgInt = 0;
        int specialInt = 0, userId = 0, ratingInt = 0;

        try {
            durationInt = Integer.parseInt(duration.getValue());
            distanceInt = Integer.parseInt(distance.getValue());
            specialInt = Integer.parseInt(special.getValue());
            avgInt = Integer.parseInt(avg.getValue());
            ratingInt = (int) Math.round(rating.getValue());
        } catch (RuntimeException e) {
            System.out.println(e);
            return null;
        }

        return TourLog.builder()
                .tourId(currentTour.getId())
                .date(LocalDate.parse(date.getValue()))
                .duration(durationInt)
                .distance(distanceInt)
                .rating(ratingInt)
                .sport(Sport.valueOf(sport.getValue()))
                .avgSpeed(avgInt)
                .start(LocalTime.now())
                .end(LocalTime.now())
                .special(specialInt)
                .userId(userId)
                .build();
    }

    public void init(IViewModel previousViewModel) {
        appManager = previousViewModel.getAppManager();
        if (currentTour == null)
            currentTour = previousViewModel.getCurrentTour();
    }
}
