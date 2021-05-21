package org.garcia.layerView.viewModel;

import org.garcia.layerBusiness.appmanager.IAppManager;
import org.garcia.model.Tour;

public class AddTourLogViewModel {

    private static IAppManager appManager;
    private static AddTourLogViewModel viewModel;
    private Tour currentTour;

    public static AddTourLogViewModel getInstance() {
        if (viewModel == null) {
            viewModel = new AddTourLogViewModel();
        }
        return viewModel;
    }
}
