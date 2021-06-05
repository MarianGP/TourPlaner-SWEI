package org.garcia.layerView.viewModel;

import org.garcia.layerBusiness.appmanager.IAppManager;
import org.garcia.model.Tour;
import org.garcia.model.TourLog;

public interface IViewModel {
    IAppManager getAppManager();
    Tour getCurrentTour();
    TourLog getCurrentTourLog();
    void init(IViewModel viewModel);
}
