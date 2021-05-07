package org.garcia.layerbusiness.appmanager;

import org.garcia.layerbusiness.mapper.TourLogMapper;
import org.garcia.layerbusiness.mapper.TourMapper;
import org.garcia.layerbusiness.util.InputValidator;
import org.garcia.layerdataaccess.common.DALFactory;
import org.garcia.layerdataaccess.fileaccess.FileAccess;
import org.garcia.layerdataaccess.fileaccess.PDFBuilder;
import org.garcia.layerdataaccess.mapAPI.MapAPIConnection;
import org.garcia.layerdataaccess.service.TourLogService;
import org.garcia.layerdataaccess.service.TourService;
import org.garcia.model.Tour;
import org.garcia.model.TourData;
import org.garcia.model.TourLog;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppManagerDB implements IAppManager {

    private final static String CONTAINER_NAME = "postgresDB1";
    private final static String API_NAME = "mapQuestApi";
    private final TourService tourService;
    private final TourLogService tourLogService;
    private final MapAPIConnection mapAPI;
    private final FileAccess fileAccess;


    public AppManagerDB() throws IOException {
        DALFactory.init(CONTAINER_NAME);
        tourService = DALFactory.createTourService();
        tourLogService = DALFactory.createTourLogService();
        mapAPI = DALFactory.createMapAPIConnection(API_NAME);
        fileAccess = DALFactory.createFileAccess();
    }

    @Override
    public List<Tour> searchTours(String inputSearch) throws SQLException {
        if (inputSearch.equals(""))
            return tourService.findAll();
        if (InputValidator.validString(inputSearch)) {
            return tourService.findBySearchInput(inputSearch);
        }
        return null;
    }

    @Override
    public List<TourLog> searchLogsByTourId(int id) throws SQLException {
        return tourLogService.findByTourId(id);
    }

    @Override
    public int addTour(String[] parameters) throws SQLException, IOException {
        Tour newTour = TourMapper.ParametersToTour(parameters);
        if (newTour != null) {
            String imgPath = mapAPI.getMap(newTour);
            newTour.setImg(imgPath);
            newTour.setDistance(200);
            newTour.setDuration(3);
            return tourService.addTour(newTour);
        }
        return 0;
    }

    @Override
    public int addLog(Object[] parameters, int tourId) throws SQLException {
        int logId = 0;
        TourLog tourLog = TourLogMapper.GUIInputToLog(parameters, tourId);
        if (tourLog != null)
            logId = tourLogService.addTourLog(tourLog);
        return logId;
    }

    @Override
    public boolean deleteTour(Tour tour) throws SQLException {
        if (tourService.deleteTour(tour.getId()) != 0) {
            fileAccess.deleteFile("img", tour.getImg());
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteLogById(int id) throws SQLException {
        if (id != 0) {
            return tourLogService.deleteById(id) != 0;
        }
        return false;
    }

    @Override
    public boolean validTour(Tour tour) {
        return (InputValidator.validString(tour.getDescription()) &&
                InputValidator.validString(tour.getTitle()) &&
                InputValidator.validString(tour.getDestination()) &&
                InputValidator.validString(tour.getOrigin())
        );
    }

    @Override
    public boolean importTourNLogs(String fileName, String location){
        try {
            List<TourData> tourDataList = fileAccess.getTourDataFromFile(fileName, location);
            for (TourData tourData : tourDataList) {
                String[] parameters = {
                        tourData.getTour().getTitle(),
                        tourData.getTour().getOrigin(),
                        tourData.getTour().getDestination(),
                        tourData.getTour().getDescription(),
                        String.valueOf(tourData.getTour().getId())};
                addTour(parameters);
                for (TourLog tourLog : tourData.getTourLogList()) {
                    tourLogService.addTourLog(tourLog);
                }
            }
            return true;
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean exportTourNLogs(String fileName, String location) throws SQLException {
        List<TourData> tourDataList = new ArrayList<>();
        List<Tour> allTours = tourService.findBySearchInput("");

        for (Tour tour : allTours) {
            List<TourLog> tourLogList = tourLogService.findByTourId(tour.getId());
            TourData tourPair = TourData.builder()
                    .tourLogList(tourLogList)
                    .tour(tour)
                    .build();
            tourDataList.add(tourPair);
        }

        try {
            fileAccess.exportTours(fileName, location, tourDataList);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void createSummaryReport(String url) {
        PDFBuilder.createSummaryPdf(url);
    }

    @Override
    public void createTourReport(Tour currentTour, String url) {
        PDFBuilder.createTourPdf(currentTour, url);
    }

    @Override
    public boolean editTour(Tour tour) {
        if (validTour(tour)) {
            //edit by id
            return true;
        }
        return false;
    }

}
