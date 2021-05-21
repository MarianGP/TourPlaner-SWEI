package org.garcia.layerView.viewModel;

import org.garcia.layerBusiness.appmanager.IAppManager;
import org.garcia.model.Tour;

public interface IViewModel {
    IAppManager getAppManager();
    Tour getCurrentTour();
}
