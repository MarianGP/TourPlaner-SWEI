package org.garcia.layerbusiness.mapper;

import org.garcia.model.TourLog;

import java.time.LocalDate;

public class TourLogMapper {
    public static TourLog GUIInputToLog(Object[] param, int tourId) {
        return TourLog.builder()
                .id(20) //TODO: get from db
                .tourId(tourId)
                .date((LocalDate) param[0])
                .duration((Integer) param[1])
                .distance((Integer) param[2])
                .build();
    }
}
