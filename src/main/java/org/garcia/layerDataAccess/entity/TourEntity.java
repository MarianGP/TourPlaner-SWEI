package org.garcia.layerDataAccess.entity;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TourEntity implements ResourceEntity {
    private final int id;
    private final String title;
    private final String description;
    private final String origin;
    private final String destination;
    private final int user_id;
    private final String img;
    private final int distance;
    private final int duration;
}
