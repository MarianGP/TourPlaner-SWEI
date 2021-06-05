package org.garcia.layerView.viewModel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;
import org.garcia.layerBusiness.appmanager.IAppManager;
import org.garcia.model.Tour;
import org.garcia.model.TourLog;

import java.io.IOException;

@Data
public class AddTourViewModel implements IViewModel {

    private static AddTourViewModel viewModel;
    private ToursViewModel tourViewModel;
    private IAppManager appManager;
    private Tour currentTour;
    private TourLog currentTourLog;

    private StringProperty title = new SimpleStringProperty("");
    private StringProperty origin = new SimpleStringProperty("");
    private StringProperty destination = new SimpleStringProperty("");
    private StringProperty description = new SimpleStringProperty("");
    public BooleanProperty btnDisable = new SimpleBooleanProperty(true);

    public static AddTourViewModel getInstance() {
        if (viewModel == null) {
            viewModel = new AddTourViewModel();
        }
        return viewModel;
    }

    @Override
    public void init(IViewModel previousViewModel) {
        tourViewModel = (ToursViewModel) previousViewModel;
        appManager = tourViewModel.getAppManager();
        currentTour = tourViewModel.getCurrentTour();

        if (tourViewModel.getEditMode().getValue()) {
            origin.setValue(currentTour.getOrigin());
            destination.setValue(currentTour.getDestination());
            description.setValue(currentTour.getDescription());
            title.setValue(currentTour.getTitle());
        }

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

                int id = appManager.addTour(tour);
                if (id > 0) {
                    tourViewModel.searchTours("");
                }
                return id;
            } catch (RuntimeException ex) {
                throw ex;
            }
        } else {
            return -1;
        }
    }

    public int editNewTour() {
        if (title != null && description != null) {
            try {
                currentTour.setTitle(title.getValue());
                currentTour.setDescription(description.getValue());
                int id = appManager.editTour(currentTour);
                if (id > 0) {
                    tourViewModel.searchTours("");
                }
                return id;
            } catch (RuntimeException ex) {
                throw ex;
            }
        } else {
            return -1;
        }
    }
}
