package org.garcia.layerDataAccess.service;

import org.garcia.model.TourLog;

import java.util.List;

public interface ITourLogService {
    int addTourLog(TourLog tourLog);
    int editTourLog(TourLog tourLog);
    int deleteById(int id);
    List<TourLog> findByTourId(int tourId);
}
