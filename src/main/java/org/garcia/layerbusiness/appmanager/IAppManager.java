package org.garcia.layerbusiness.appmanager;

import org.garcia.model.Tour;
import org.garcia.model.TourLog;

import java.util.List;

public interface IAppManager {

    List<Tour> searchTours(String inputSearch, boolean b);
    boolean deleteTour(int id);
    boolean addTour(String[] parameters);
    boolean editTour(Tour tour);
    boolean validTour(Tour tour);

    List<TourLog> searchLogs(int id);

    boolean addLog(Object[] parameters, int tourId);
    boolean deleteLog(int id);

}
