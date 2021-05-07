package org.garcia.layerbusiness.appmanager;

import org.garcia.model.Tour;
import org.garcia.model.TourLog;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IAppManager {

    List<Tour> searchTours(String inputSearch) throws SQLException;
    List<TourLog> searchLogsByTourId(int id) throws SQLException;

    boolean deleteTour(Tour tour) throws SQLException;
    boolean deleteLogById(int id) throws SQLException;

    int addTour(String[] parameters) throws SQLException, IOException;
    int addLog(Object[] parameters, int tourId) throws SQLException;

    boolean editTour(Tour tour);
    boolean validTour(Tour tour);

    boolean importTourNLogs(String fileName, String location) throws IOException, SQLException;
    boolean exportTourNLogs(String fileName, String location) throws SQLException, IOException;

    void createSummaryReport(String url);
    void createTourReport(Tour currentTour, String url);
}
