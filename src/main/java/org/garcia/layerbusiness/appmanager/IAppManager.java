package org.garcia.layerbusiness.appmanager;

import org.garcia.model.Tour;

import java.util.List;

public interface IAppManager {

    List<Tour> searchTours(String inputSearch, boolean b);
    boolean deleteTour(int id);
    boolean addTour(Tour tour);
    boolean editTour(Tour tour);
    boolean validTour(Tour tour);

    static IAppManager getInstance() {
        return null;
    }
}
