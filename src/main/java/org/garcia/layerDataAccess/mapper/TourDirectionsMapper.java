package org.garcia.layerDataAccess.mapper;

import org.garcia.layerDataAccess.entity.ResourceEntity;
import org.garcia.layerDataAccess.entity.TourDirectionEntity;
import org.garcia.model.TourDirection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TourDirectionsMapper {

    public static ResourceEntity dbRowToTourDirectionEntity(ResultSet rs) throws SQLException {
        return TourDirectionEntity.builder()
                .icon_url(rs.getString("icon_url"))
                .step(rs.getString("step"))
                .icon_url(rs.getString("icon_url"))
                .tour_id(rs.getInt("tour_id"))
                .build();
    }

    public static TourDirection entityToTourDirections(TourDirectionEntity entity) {
        return TourDirection.builder()
                .iconUrl(entity.getIcon_url())
                .direction(entity.getStep())
                .tourId(entity.getTour_id())
                .build();
    }
}
