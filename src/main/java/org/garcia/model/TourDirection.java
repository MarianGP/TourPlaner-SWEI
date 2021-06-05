package org.garcia.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TourDirection {
    private int tourId;
    private String iconUrl;
    private String direction;
}
