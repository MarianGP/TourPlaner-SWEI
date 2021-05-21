package org.garcia.layerDataAccess.service;

import org.garcia.model.Tour;

import java.util.List;

public interface ITourService extends IService {
    List<Tour> findAll();
    List<Tour> findBySearchInput(String searchInput);
    int addTour(Tour tour);
    int deleteTour(int id);
    int editTour(Tour tour);
}
