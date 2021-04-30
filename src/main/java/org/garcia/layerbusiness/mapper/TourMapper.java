package org.garcia.layerbusiness.mapper;

import org.garcia.layerbusiness.validator.InputValidator;
import org.garcia.model.Tour;


public class TourMapper {

    public static Tour GUIInputToTour(String[] parameters) {
        if(InputValidator.validateStringList(parameters)) {
            Tour tour = Tour.builder()
                    .author("Me")
                    .title(parameters[0])
                    .origin(parameters[1])
                    .destination(parameters[2])
                    .description(parameters[3])
                    .img("")
                    .build();
            return tour;
        } else {
            return null;
        }
    }
}
