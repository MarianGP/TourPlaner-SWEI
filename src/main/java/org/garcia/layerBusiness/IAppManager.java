package org.garcia.layerBusiness;

import org.garcia.model.Tour;
import org.garcia.model.TourDirection;
import org.garcia.model.TourLog;

import java.util.List;

public interface IAppManager {

    List<Tour> searchTours(String inputSearch);
    List<TourLog> searchLogsByTourId(int id);
    List<TourDirection> searchDirections(int tourId);

    boolean deleteTour(Tour tour);
    boolean deleteLogById(int id);

    int addTour(Tour newTour);
    int editTour(Tour tour);

    int addLog(TourLog tourLog);
    int editLog(TourLog tourLog);

    boolean importTourNLogs(String fileName, String location);
    boolean exportTourNLogs(String fileName, String location);

    void createSummaryReport(String url, List<Tour> allTours);
    void createTourReport(Tour currentTour, String url);

}
