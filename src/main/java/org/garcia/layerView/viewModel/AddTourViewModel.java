package org.garcia.layerView.viewModel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;
import org.garcia.layerBusiness.appmanager.IAppManager;
import org.garcia.model.Tour;

import java.io.IOException;

@Data
public class AddTourViewModel implements IViewModel {

    private static AddTourViewModel viewModel;
    private IAppManager appManager;
    private Tour currentTour;

    private StringProperty title = new SimpleStringProperty();
    private StringProperty origin = new SimpleStringProperty();
    private StringProperty destination = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    public BooleanProperty btnDisable = new SimpleBooleanProperty();

    public static AddTourViewModel getInstance() {
        if (viewModel == null) {
            viewModel = new AddTourViewModel();
        }
        return viewModel;
    }

    @Override
    public IAppManager getAppManager() {
        return appManager;
    }

    @Override
    public Tour getCurrentTour() {
        return currentTour;
    }

    @Override
    public void init(IViewModel aPreviousViewModel) {
        currentTour = aPreviousViewModel.getCurrentTour();
        System.out.println("init view model");
    }

    public int addNewTour() throws IOException {
        if (title != null && origin != null && description != null && destination != null) {
            try {
                Tour tour = Tour.builder()
                        .title(title.getValue())
                        .origin(origin.getValue())
                        .destination(destination.getValue())
                        .description(description.getValue())
                        .build();
                return appManager.addTour(tour);
            } catch (RuntimeException ex) {
                throw ex;
            }
        } else {
            return -1;
        }
    }
}
