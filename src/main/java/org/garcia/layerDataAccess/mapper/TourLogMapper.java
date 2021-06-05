package org.garcia.layerDataAccess.mapper;

import org.garcia.layerDataAccess.entity.ResourceEntity;
import org.garcia.layerDataAccess.entity.TourLogEntity;
import org.garcia.model.TourLog;
import org.garcia.model.enums.Sport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TourLogMapper {

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
                .id(logEntity.getId())
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
                .report(logEntity.getReport())
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
                .report(rs.getString("report"))
                .build();
    }
}
