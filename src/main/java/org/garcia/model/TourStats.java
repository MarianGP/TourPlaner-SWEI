package org.garcia.model;

import lombok.Data;

@Data
public class TourStats {

    private int totalDistance;
    private int totalTime;

    private int totalTour;
    private int totalLogs;

    private int bikedKm;
    private int bikedTime;
    private int hikedKm;
    private int hikedTime;
    private int walkedKm;
    private int walkedTime;
    private int runKm;
    private int runTime;
}
