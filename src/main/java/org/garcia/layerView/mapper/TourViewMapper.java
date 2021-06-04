package org.garcia.layerView.mapper;

import org.garcia.layerBusiness.util.InputValidator;
import org.garcia.model.Tour;


public class TourViewMapper {

    public static Tour ParametersToTour(String[] parameters) {
        if (InputValidator.validateStringList(parameters)) {
            Tour tour = Tour.builder()
                    .author("Me")
                    .title(parameters[0])
                    .origin(parameters[1])
                    .destination(parameters[2])
                    .description(parameters[3])
                    .distance(0) // set from mapApi
                    .duration(0)
                    .build();
            if(parameters.length > 4) {
                tour.setId(Integer.parseInt(parameters[4]));
            }
            return tour;
        } else {
            return null;
        }
    }

}
