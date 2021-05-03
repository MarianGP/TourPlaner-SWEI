package org.garcia.layerdataaccess.persistance.entity;

import lombok.Builder;
import lombok.Getter;
import org.garcia.model.enums.Sport;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
public class TourLogEntity extends ResourceEntity {
    private int id;
    private LocalDate date;
    private int distance;
    private int duration;
    private int rating;
    private Sport sport;
    private int avg_speed;
    private LocalTime start;
    private LocalTime arrival;
    private int special; //steps or cadence
    private int tourId;
    private int userId;
}
