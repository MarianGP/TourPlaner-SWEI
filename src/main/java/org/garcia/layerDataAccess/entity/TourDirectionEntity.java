package org.garcia.layerDataAccess.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TourDirectionEntity implements ResourceEntity {
    private final int direccion_id;
    private final int tour_id;
    private final String icon_url;
    private final String step;
}
