package org.garcia.model;

import org.garcia.model.enums.Sport;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class TourLog {

    private LocalDate date;
    private int distance;
    private int duration;
    private int rating;
    private Sport sport;
    private int avg_speed;
    private LocalTime start;
    private LocalTime end;
    private int special; //steps or cadence
    private int tourId;
    private int userId;

}
