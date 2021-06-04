package org.garcia.layerView.mapper;

import org.garcia.model.TourLog;
import org.garcia.model.enums.Sport;

import java.time.LocalTime;

public class TourLogViewMapper {
    public static TourLog GUIInputToLog(Object aDuration, Object aDistance, int tourId, int rating, Object avgSpeed, Object aSpecial) {
       int duration = 0;
       int distance = 0;
       int avg = 0;
       int special = 0;
       int userId = 1; // TODO: in db case

        return TourLog.builder()
                .tourId(tourId)
//                .date((LocalDate) param[0])
                .duration(duration)
                .distance(distance)
                .rating(rating)
                .sport(Sport.BIKE)
                .avgSpeed(avg)
                .start(LocalTime.now())
                .end(LocalTime.now())
                .special(special)
                .userId(userId)
                .build();
    }
}
