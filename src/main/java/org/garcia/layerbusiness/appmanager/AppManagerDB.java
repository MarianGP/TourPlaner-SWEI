package org.garcia.layerbusiness.appmanager;

import org.garcia.layerbusiness.validator.InputValidator;
import org.garcia.model.Tour;
import org.garcia.model.TourLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AppManagerDB implements IAppManager {

    //private final db

    @Override
    public List<Tour> searchTours(String inputSearch, boolean isCaseSensitive) {
        if (InputValidator.validString(inputSearch)) {
            String inputLowerCase = inputSearch.toLowerCase(Locale.ROOT);
            List<Tour> foundTours = new ArrayList<>();
            // find in db
            return foundTours;
        }
        return null;
    }

    @Override
    public boolean deleteTour(int id) {
        //delete by id, doesn't need validation
        return true;
    }

    @Override
    public boolean addTour(String[] parameters) {
        return false;
    }

    @Override
    public boolean editTour(Tour tour) {
        if(validTour(tour)) {
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
    public List<TourLog> searchLogs(int id) {
        return null;
    }

    @Override
    public boolean addLog(Object[] parameters, int tourId) {
        return false;
    }

    @Override
    public boolean deleteLog(int id) {
        return false;
    }

}
