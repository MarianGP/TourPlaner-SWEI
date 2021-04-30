package org.garcia.layerbusiness.appmanager;

import org.garcia.layerbusiness.validator.InputValidator;
import org.garcia.model.Tour;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AppManagerMock implements IAppManager {

    private final List<Tour> foundTours = new ArrayList<>();

    @Override
    public List<Tour> searchTours(String inputSearch, boolean isCaseSensitive) {
        if (foundTours.isEmpty())
                addItems();
        if (InputValidator.validString(inputSearch)) {
            return foundTours
                    .stream()
                    .filter(x -> x.getTitle().toLowerCase().contains(inputSearch.toLowerCase()))
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

    private void addItems() {
        foundTours.add(new Tour(0,"first","nice long tour1","wien","graz","me","",10,60));
        foundTours.add(new Tour(1,"second","nice long tour2","wien","graz","me","",10,60));
        foundTours.add(new Tour(2,"third","nice long tour3","wien","graz","me","",10,60));
    }

    @Override
    public boolean deleteTour(int id) {
        foundTours.remove(id); // index position
        return true;
    }

    @Override
    public boolean addTour(Tour tour) {
        if(validTour(tour)) {
            foundTours.add(tour);
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
