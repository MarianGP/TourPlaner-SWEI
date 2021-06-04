package org.garcia.layerBusiness.appmanager;

import org.garcia.layerDataAccess.common.IDALFactory;
import org.garcia.model.Tour;
import org.garcia.model.TourLog;

import java.io.IOException;
import java.util.List;

public class AppManagerMock implements  IAppManager {
    public AppManagerMock(IDALFactory factory) {
    }

    @Override
    public List<Tour> searchTours(String inputSearch) {
        return null;
    }

    @Override
    public List<TourLog> searchLogsByTourId(int id) {
        return null;
    }

    @Override
    public boolean deleteTour(Tour tour) {
        return false;
    }

    @Override
    public boolean deleteLogById(int id) {
        return false;
    }

    @Override
    public int addTour(Tour newTour) throws IOException {
        return 0;
    }

    @Override
    public int addLog(TourLog tourLog) {
        return 0;
    }

    @Override
    public int editTour(Tour tour) {
        return 0;
    }

    @Override
    public boolean validTour(Tour tour) {
        return false;
    }

    @Override
    public boolean importTourNLogs(String fileName, String location) throws IOException {
        return false;
    }

    @Override
    public boolean exportTourNLogs(String fileName, String location) throws IOException {
        return false;
    }

    @Override
    public void createSummaryReport(String url, List<Tour> allTours) throws IOException {

    }

    @Override
    public void createTourReport(Tour currentTour, String url) throws IOException {

    }
}
