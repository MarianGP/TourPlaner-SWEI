package org.garcia.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class TourLog {

    private int id;
    private int tourId;
    private LocalDate date;
    private int duration;
    private int distance;
//    private int rating;
//    private Sport sport;
//    private int avg_speed;
//    private LocalTime start;
//    private LocalTime end;
//    private int special; //steps or cadence
//    private int userId;

}
