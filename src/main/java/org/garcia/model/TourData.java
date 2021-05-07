package org.garcia.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class TourData {
    private final Tour tour;
    private List<TourLog> tourLogList = new ArrayList<>();

    public void setLogs(List<TourLog> tourLogsList) {
        tourLogList = tourLogsList;
    }
}
