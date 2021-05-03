package org.garcia.layerdataaccess.persistance.entity;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TourEntity extends ResourceEntity {
    private int id;
    private String title;
    private String description;
    private String origin;
    private String destination;
    private int user_id;
    private String img;
    private int distance;
    private int duration;
}
