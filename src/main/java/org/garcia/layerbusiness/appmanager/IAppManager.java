package org.garcia.layerbusiness.appmanager;

import org.garcia.model.Tour;
import org.garcia.model.TourLog;

import java.sql.SQLException;
import java.util.List;

public interface IAppManager {

    List<Tour> searchTours(String inputSearch, boolean b) throws SQLException;
    boolean deleteTour(int id) throws SQLException;
    boolean addTour(String[] parameters) throws SQLException;
    boolean editTour(Tour tour);
    boolean validTour(Tour tour);

    List<TourLog> searchLogs(int id) throws SQLException;

    boolean addLog(Object[] parameters, int tourId) throws SQLException;
    boolean deleteLog(int id) throws SQLException;

}
