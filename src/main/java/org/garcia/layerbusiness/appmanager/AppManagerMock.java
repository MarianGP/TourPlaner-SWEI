package org.garcia.layerbusiness.appmanager;

import org.garcia.layerbusiness.mapper.TourMapper;
import org.garcia.layerbusiness.validator.InputValidator;
import org.garcia.model.Tour;
import org.garcia.model.TourLog;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AppManagerMock implements IAppManager {

    private List<Tour> foundTours = new ArrayList<>();
    private List<TourLog> foundTourLogs = new ArrayList<>();

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

    @Override
    public List<TourLog> searchLogs(int id) {
        addLogs();
        return foundTourLogs
                .stream()
                .filter(x -> x.getTourId() == id)
                .collect(Collectors.toList());
    }

    private void addItems() {
        foundTours.add(new Tour(0,"first","nice long tour1","wien","graz","me","org/garcia/img/kupo.jpg",10,60));
        foundTours.add(new Tour(1,"second","nice long tour2","wien","graz","me","org/garcia/img/chocobo.jpg",10,60));
        foundTours.add(new Tour(2,"third","nice long tour3","wien","graz","me","org/garcia/img/360.jpg",10,60));
    }

    private void addLogs() {
        foundTourLogs.add(new TourLog(0,LocalDate.of(2012, 6, 30), 25, 60));
        foundTourLogs.add(new TourLog(0,LocalDate.of(2015, 8, 15), 50, 60));
        foundTourLogs.add(new TourLog(1, LocalDate.of(2018, 9, 30), 30, 60));
    }

    @Override
    public boolean deleteTour(int id) {
        foundTours.remove(id); // index position
        return true;
    }

    @Override
    public boolean addTour(String[] parameters) {
        Tour newTour = TourMapper.GUIInputToTour(parameters);
        if(newTour != null) {
            foundTours.add(newTour);
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
