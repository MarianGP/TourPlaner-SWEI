package org.garcia.layerView.viewModel;

import org.garcia.layerBusiness.appmanager.IAppManager;
import org.garcia.model.Tour;

import java.io.IOException;

public class AddTourViewModel {

    private static AddTourViewModel viewModel;
    private IAppManager appManager;
    private Tour currentTour;

    public int addNewTour(String[] inputFields) throws IOException {
        int tourId = appManager.addTour(inputFields);
//        if (tourId > 0) {
//            searchTours("");
//        } else {
//            System.out.println("Wrong input");
//        }
        return tourId;
    }

    public static AddTourViewModel getInstance() {
        if (viewModel == null) {
            viewModel = new AddTourViewModel();
        }
        return viewModel;
    }

    public void init(IAppManager anAppManager, Tour tour) {
        appManager = anAppManager;
        if (currentTour == null) {
            currentTour = tour;
        }
    }
}
