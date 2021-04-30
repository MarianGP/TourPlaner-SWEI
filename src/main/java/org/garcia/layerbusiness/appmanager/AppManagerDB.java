package org.garcia.layerbusiness.appmanager;

import org.garcia.layerbusiness.validator.InputValidator;
import org.garcia.model.Tour;

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
    public boolean addTour(Tour tour) {
        if(validTour(tour)) {
            //add to db
            return true;
        }
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

}
