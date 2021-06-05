package org.garcia.layerView.viewModel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.garcia.layerBusiness.IAppManager;
import org.garcia.layerView.util.InputValidator;
import org.garcia.model.Tour;
import org.garcia.model.TourLog;

@Data
public class TourFormViewModel implements IViewModel {

    private static final Logger logger = LogManager.getLogger(TourFormViewModel.class);

    private static final String NOT_FOUND = "Address not found";
    private static final String TOUR_ADDED = "Tour added";
    private static final String TOUR_EDITED = "Tour edited";
    private static final String TOUR_ADDED_FAIL = "Couldn't add tour";
    private static final String TOUR_EDIT_FAIL = "Couldn't edit tour";
    private static final String WRONG_INPUT = "Wrong input";

    private static TourFormViewModel viewModel;
    public BooleanProperty btnDisable = new SimpleBooleanProperty(true);
    private ToursViewModel tourViewModel;
    private IAppManager appManager;
    private Tour currentTour;
    private TourLog currentTourLog;
    private StringProperty title = new SimpleStringProperty("");
    private StringProperty origin = new SimpleStringProperty("");
    private StringProperty destination = new SimpleStringProperty("");
    private StringProperty description = new SimpleStringProperty("");
    private StringProperty alertMessage = new SimpleStringProperty();

    public static TourFormViewModel getInstance() {
        if (viewModel == null) {
            viewModel = new TourFormViewModel();
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
    }

    public int addNewTour() {
        if (!InputValidator.validString(title.getValue()) ||
                !InputValidator.validString(origin.getValue()) ||
                !InputValidator.validString(destination.getValue()) ||
                !InputValidator.validString(description.getValue())
        ) {
            logger.warn("Add Tour:" + WRONG_INPUT);
            alertMessage.setValue("Fill all fields up");
            return -1;
        } else {
            Tour tour = Tour.builder()
                    .title(title.getValue())
                    .origin(origin.getValue())
                    .destination(destination.getValue())
                    .description(description.getValue())
                    .build();
            int id = appManager.addTour(tour);
            if (id > 0) {
                tourViewModel.searchTours();
                alertMessage.setValue("");
                logger.info(TOUR_ADDED);
            } else if (id == -1) {
                alertMessage.setValue(NOT_FOUND);
                logger.error(NOT_FOUND);
            } else {
                logger.info(TOUR_ADDED_FAIL);
                alertMessage.setValue(TOUR_ADDED_FAIL);
            }
            return id;
        }
    }

    public int editNewTour() {
        if (!InputValidator.validString(title.getValue()) ||
                !InputValidator.validString(description.getValue())) {
            logger.warn("Edit Tour:" + WRONG_INPUT);
            alertMessage.setValue(WRONG_INPUT);
        } else {
            currentTour.setTitle(title.getValue());
            currentTour.setDescription(description.getValue());
            int id = appManager.editTour(currentTour);
            if (id > 0) {
                tourViewModel.getInputSearch().setValue("");
                tourViewModel.searchTours();
                alertMessage.setValue("");
                logger.info(TOUR_EDITED);
            } else {
                alertMessage.setValue(TOUR_EDIT_FAIL);
                logger.error(TOUR_EDIT_FAIL);
            }
            return id;
        }
        return 0;
    }
}
