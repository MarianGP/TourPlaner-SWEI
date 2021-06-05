package org.garcia.layerDataAccess.service;

import org.garcia.model.TourDirection;

import java.util.List;

public interface ITourDirectionsService {
    List<TourDirection> findByTourId(int tourId);
    int addTourDirection(int tourId, String step, String iconUrl);
    int deleteByTourId(int tourId);
}
