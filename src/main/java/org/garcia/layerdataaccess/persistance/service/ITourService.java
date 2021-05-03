package org.garcia.layerdataaccess.persistance.service;

import org.garcia.layerdataaccess.persistance.entity.TourEntity;
import org.garcia.model.Tour;

import java.sql.SQLException;
import java.util.List;

public interface ITourService extends IService {
    TourEntity findById(Integer id);
    List<Tour> findBySearchInput(String searchInput) throws SQLException;
    List<TourEntity> findByOrigin(String origin);
    List<TourEntity> findByDestination(String destination);
    int addTour(Tour tour) throws SQLException;
    int deleteTour(int id) throws SQLException;
    int duplicateTour(Tour tour);
    int editTour(Tour tour);
}
