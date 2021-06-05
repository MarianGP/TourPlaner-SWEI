package org.garcia.layerDataAccess.entity;

import lombok.Builder;
import lombok.Getter;
import org.garcia.model.enums.Sport;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
public class TourLogEntity implements ResourceEntity {
    private final int id;
    private final LocalDate date;
    private final int distance;
    private final int duration;
    private final int rating;
    private final Sport sport;
    private final int avg_speed;
    private final LocalTime start;
    private final LocalTime arrival;
    private final int special; //steps or cadence
    private final int tourId;
    private final int userId;
    private final String report;
}
