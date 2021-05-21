package org.garcia.layerView.controller;

import org.garcia.layerBusiness.appmanager.IAppManager;
import org.garcia.model.Tour;

public interface IController {
    void initViewModel(IAppManager appManager, Tour currentTour);
}
