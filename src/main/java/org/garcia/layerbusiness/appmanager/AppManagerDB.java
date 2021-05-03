package org.garcia.layerbusiness.appmanager;

import org.garcia.layerbusiness.mapper.TourLogMapper;
import org.garcia.layerbusiness.mapper.TourMapper;
import org.garcia.layerbusiness.validator.InputValidator;
import org.garcia.layerdataaccess.persistance.dbconnection.DBConnection;
import org.garcia.layerdataaccess.persistance.dbconnection.PostgresDBConnection;
import org.garcia.layerdataaccess.persistance.repository.Repository;
import org.garcia.layerdataaccess.persistance.service.ServiceFactory;
import org.garcia.layerdataaccess.persistance.service.TourLogService;
import org.garcia.layerdataaccess.persistance.service.TourService;
import org.garcia.model.Tour;
import org.garcia.model.TourLog;

import java.sql.SQLException;
import java.util.List;

public class AppManagerDB implements IAppManager {

    private final TourService tourService;
    private final TourLogService tourLogService;

    public AppManagerDB() {
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
    public boolean deleteTour(int id) throws SQLException {
        if(tourLogService.deleteByTourId(id) != 0) {
            return tourService.deleteTour(id) != 0;
        }
        return false;
    }

    @Override
    public boolean addTour(String[] parameters) throws SQLException {
        Tour newTour = TourMapper.ParametersToTour(parameters);
        if (newTour != null) {
            tourService.addTour(newTour);
            return true;
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

}
