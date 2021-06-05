package org.garcia.layerBusiness.appmanager;

import org.garcia.model.Tour;
import org.garcia.model.TourLog;

import java.io.IOException;
import java.util.List;

public interface IAppManager {

    List<Tour> searchTours(String inputSearch);
    List<TourLog> searchLogsByTourId(int id) ;

    boolean deleteTour(Tour tour);
    boolean deleteLogById(int id);

    int addTour(Tour newTour) throws IOException;
    int editTour(Tour tour);

    int addLog(TourLog tourLog);
    int editLog(TourLog tourLog);

    boolean importTourNLogs(String fileName, String location) throws IOException;
    boolean exportTourNLogs(String fileName, String location) throws IOException;

    void createSummaryReport(String url, List<Tour> allTours) throws IOException;
    void createTourReport(Tour currentTour, String url) throws IOException;
}
