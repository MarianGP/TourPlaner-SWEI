package org.garcia.layerdataaccess.persistance.service;

import org.garcia.layerdataaccess.persistance.entity.TourEntity;
import org.garcia.model.Tour;

import java.util.List;

public class TourService implements ITourService {

    @Override
    public TourEntity findById(Integer id) {
        return null;
    }

    @Override
    public List<TourEntity> findBySearchInput(String searchInput) {
        return null;
    }

    @Override
    public List<TourEntity> findByOrigin(String origin) {
        return null;
    }

    @Override
    public List<TourEntity> findByDestination(String destination) {
        return null;
    }

    @Override
    public int addTour(Tour tour) {
        return 0;
    }

    @Override
    public int duplicateTour(Tour tour) {
        return 0;
    }

    @Override
    public int editTour(Tour tour) {
        return 0;
    }
}
