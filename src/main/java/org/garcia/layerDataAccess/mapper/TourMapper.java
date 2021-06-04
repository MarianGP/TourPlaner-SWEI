package org.garcia.layerDataAccess.mapper;

import org.garcia.layerDataAccess.entity.ResourceEntity;
import org.garcia.layerDataAccess.entity.TourEntity;
import org.garcia.model.Tour;

import java.sql.ResultSet;
import java.sql.SQLException;


public class TourMapper {

    public static Tour tourEntityToTour(ResourceEntity tourEntity) {
        Tour tour = null;
        if (tourEntity != null)
            tour = Tour.builder()
                    .id(((TourEntity) tourEntity).getId())
                    .author("Me")
                    .title(((TourEntity) tourEntity).getTitle())
                    .origin(((TourEntity) tourEntity).getOrigin())
                    .destination(((TourEntity) tourEntity).getDestination())
                    .description(((TourEntity) tourEntity).getDescription())
                    .duration(((TourEntity) tourEntity).getDuration())
                    .distance(((TourEntity) tourEntity).getDistance())
                    .img(((TourEntity) tourEntity).getImg())
                    .build();
        return tour;
    }

    public static TourEntity dbRowToTourEntity(ResultSet rs) throws SQLException {
        return TourEntity.builder()
                .id(rs.getInt("tour_id"))
                .title(rs.getString("title"))
                .description(rs.getString("description"))
                .origin(rs.getString("origin"))
                .destination(rs.getString("destination"))
                .user_id(rs.getInt("user_id"))
                .distance(rs.getInt("distance"))
                .duration(rs.getInt("duration"))
                .img(rs.getString("img"))
                .build();
    }

}
