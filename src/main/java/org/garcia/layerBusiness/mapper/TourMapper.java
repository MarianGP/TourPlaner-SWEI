package org.garcia.layerBusiness.mapper;

import org.garcia.layerBusiness.util.InputValidator;
import org.garcia.layerDataAccess.entity.ResourceEntity;
import org.garcia.layerDataAccess.entity.TourEntity;
import org.garcia.model.Tour;

import java.sql.ResultSet;
import java.sql.SQLException;


public class TourMapper {

    public static Tour ParametersToTour(String[] parameters) {
        if (InputValidator.validateStringList(parameters)) {
            Tour tour = Tour.builder()
                    .author("Me")
                    .title(parameters[0])
                    .origin(parameters[1])
                    .destination(parameters[2])
                    .description(parameters[3])
                    .distance(0) // set from mapApi
                    .duration(0)
                    .build();
            if(parameters.length > 4) {
                tour.setId(Integer.parseInt(parameters[4]));
            }
            return tour;
        } else {
            return null;
        }
    }

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
