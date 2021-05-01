package org.garcia.layerbusiness.appmanager;

import org.garcia.layerbusiness.mapper.TourLogMapper;
import org.garcia.layerbusiness.mapper.TourMapper;
import org.garcia.layerbusiness.validator.InputValidator;
import org.garcia.model.Tour;
import org.garcia.model.TourLog;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AppManagerMock implements IAppManager {

    private final List<Tour> foundTours = new ArrayList<>();
    private final List<TourLog> foundTourLogs = new ArrayList<>();

    @Override
    public List<Tour> searchTours(String inputSearch, boolean isCaseSensitive) {
        if (foundTours.size() == 0)
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
        if (foundTourLogs.size() == 0)
            addLogs();

        return foundTourLogs
                .stream()
                .filter(x -> x.getTourId() == id)
                .collect(Collectors.toList());
    }

    @Override
    public boolean addLog(Object[] parameters, int tourId) {
        TourLog log = TourLogMapper.GUIInputToLog(parameters, tourId);
        if (log != null) {
            foundTourLogs.add(log);
            System.out.println(log);
            return true;
        }
        System.out.println("invalid input");
        return false;
    }

    @Override
    public boolean deleteLog(int id) {
        List<TourLog> operatedList = new ArrayList<>();
        foundTourLogs.stream()
                .filter(item -> item.getId() == id)
                .forEach(item -> {
                    operatedList.add(item);
                });
        foundTourLogs.removeAll(operatedList);
        return true;
    }

    private void addItems() {
        foundTours.add(new Tour(1, "first", "nice long tour1", "wien", "graz", "me", "org/garcia/img/kupo.jpg", 10, 60));
        foundTours.add(new Tour(2, "second", "nice long tour2", "wien", "graz", "me", "org/garcia/img/chocobo.jpg", 10, 60));
        foundTours.add(new Tour(3, "third", "nice long tour3", "wien", "graz", "me", "org/garcia/img/360.jpg", 10, 60));
    }

    private void addLogs() {
        foundTourLogs.add(new TourLog(0, 0, LocalDate.of(2012, 6, 30), 25, 60));
        foundTourLogs.add(new TourLog(1, 0, LocalDate.of(2015, 8, 15), 50, 60));
        foundTourLogs.add(new TourLog(2, 1, LocalDate.of(2018, 9, 30), 30, 60));
    }

    @Override
    public boolean deleteTour(int id) {
        foundTours.remove(id); // index position
        return true;
    }

    @Override
    public boolean addTour(String[] parameters) {
        Tour newTour = TourMapper.GUIInputToTour(parameters);
        if (newTour != null) {
            foundTours.add(newTour);
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
}
