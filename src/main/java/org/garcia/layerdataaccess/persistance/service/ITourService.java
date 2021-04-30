package org.garcia.layerdataaccess.persistance.service;

import org.garcia.layerdataaccess.persistance.entity.TourEntity;
import org.garcia.model.Tour;

import java.util.List;

public interface ITourService {
    TourEntity findById(Integer id);
    List<TourEntity> findBySearchInput(String searchInput);
    List<TourEntity> findByOrigin(String origin);
    List<TourEntity> findByDestination(String destination);
    int addTour(Tour tour);
    int duplicateTour(Tour tour);
    int editTour(Tour tour);
}
