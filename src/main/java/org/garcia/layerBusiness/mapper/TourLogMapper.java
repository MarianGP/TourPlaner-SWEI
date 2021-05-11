package org.garcia.layerBusiness.mapper;

import org.garcia.layerBusiness.util.InputValidator;
import org.garcia.layerDataAccess.entity.ResourceEntity;
import org.garcia.layerDataAccess.entity.TourLogEntity;
import org.garcia.model.TourLog;
import org.garcia.model.enums.Sport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TourLogMapper {
    public static TourLog GUIInputToLog(Object[] param, int tourId) {
       int duration = InputValidator.validInt(param[1]);
       int distance = InputValidator.validInt(param[2]);
       int userId = 1; // TODO: in db case

        if(!(param[0] instanceof LocalDate))
            return null;

        return TourLog.builder()
                .tourId(tourId)
                .date((LocalDate) param[0])
                .duration(duration)
                .distance(distance)
                .rating(4)
                .sport(Sport.BIKE)
                .avgSpeed(21)
                .start(LocalTime.now())
                .end(LocalTime.now())
                .special(120)
                .userId(userId)
                .build();
    }

    public static List<TourLog> getMappedTourLogs(List<ResourceEntity> tourLogEntityList) {
        List<TourLog> tourLogList = new ArrayList<>();

        if(tourLogEntityList != null) {
            for(ResourceEntity resource: tourLogEntityList) {
                tourLogList.add(logEntityToTourLog((TourLogEntity) resource));
            }
        }

        return tourLogList;
    }

    public static TourLog logEntityToTourLog(TourLogEntity logEntity) {
        return TourLog.builder()
                .id(logEntity.getId()) //TODO: get from db
                .tourId(logEntity.getTourId())
                .date(logEntity.getDate())
                .duration(logEntity.getDuration())
                .distance(logEntity.getDistance())
                .rating(logEntity.getRating())
                .sport(logEntity.getSport())
                .avgSpeed(logEntity.getAvg_speed())
                .start(logEntity.getStart())
                .end(logEntity.getArrival())
                .special(logEntity.getSpecial())
                .userId(logEntity.getUserId())
                .build();
    }

    public static ResourceEntity dbRowToLogEntity(ResultSet rs) throws SQLException {
        return TourLogEntity.builder()
                .id(rs.getInt("log_id"))
                .date(rs.getDate("date").toLocalDate())
                .distance(rs.getInt("distance"))
                .duration(rs.getInt("duration"))
                .rating(rs.getInt("rating"))
                .sport(Sport.valueOf(rs.getString("sport")))
                .avg_speed(rs.getInt("avg_speed"))
                .start(rs.getTime("start").toLocalTime())
                .arrival(rs.getTime("arrival").toLocalTime())
                .special(rs.getInt("special"))
                .tourId(rs.getInt("tour_id"))
                .userId(rs.getInt("user_id"))
                .build();
    }
}
