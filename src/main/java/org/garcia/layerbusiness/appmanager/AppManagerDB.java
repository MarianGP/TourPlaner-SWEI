package org.garcia.layerbusiness.appmanager;

import org.garcia.layerbusiness.mapper.TourLogMapper;
import org.garcia.layerbusiness.mapper.TourMapper;
import org.garcia.layerbusiness.util.InputValidator;
import org.garcia.layerdataaccess.common.dbconnection.DBConnection;
import org.garcia.layerdataaccess.common.dbconnection.PostgresDBConnection;
import org.garcia.layerdataaccess.mapAPI.MapAPIConnection;
import org.garcia.layerdataaccess.repository.Repository;
import org.garcia.layerdataaccess.service.ServiceFactory;
import org.garcia.layerdataaccess.service.TourLogService;
import org.garcia.layerdataaccess.service.TourService;
import org.garcia.model.Tour;
import org.garcia.model.TourLog;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AppManagerDB implements IAppManager {

    private final TourService tourService;
    private final TourLogService tourLogService;
    private final MapAPIConnection mapAPI = new MapAPIConnection();

    public AppManagerDB() throws IOException {
        DBConnection dbConnection = new PostgresDBConnection();
        Repository repository = new Repository(dbConnection);
        ServiceFactory serviceFactory = new ServiceFactory(repository);
        tourService = (TourService) serviceFactory.getTourServiceInstance();
        tourLogService = (TourLogService) serviceFactory.getTourLogServiceInstance();
    }

    @Override
    public List<Tour> searchTours(String inputSearch, boolean isCaseSensitive) throws SQLException {
        if (inputSearch.equals(""))
            return tourService.findAll();
        if (InputValidator.validString(inputSearch)) {
            return tourService.findBySearchInput(inputSearch);
        }
        return null;
    }

    @Override
    public boolean deleteTour(Tour tour) throws SQLException {
        if(tourService.deleteTour(tour.getId()) != 0 && !tour.getImg().equals("")) {
            mapAPI.deleteFile("img", tour.getImg());
            return true;
        }
        return false;
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
    public boolean validTour(Tour tour) {
        return (InputValidator.validString(tour.getDescription()) &&
                InputValidator.validString(tour.getTitle()) &&
                InputValidator.validString(tour.getDestination()) &&
                InputValidator.validString(tour.getOrigin())
        );
    }

    @Override
    public List<TourLog> searchLogs(int id) throws SQLException {
        return tourLogService.findByTourId(id);
    }

    @Override
    public boolean addLog(Object[] parameters, int tourId) throws SQLException {
        TourLog tourLog = TourLogMapper.GUIInputToLog(parameters, tourId);
        if (tourLog != null) {
            return tourLogService.addTourLog(tourLog) != 0;
        }
        return false;
    }

    @Override
    public boolean deleteLog(int id) throws SQLException {
        if(id != 0) {
            return tourLogService.deleteById(id) != 0;
        }
        return false;
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
