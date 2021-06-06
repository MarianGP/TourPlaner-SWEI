package org.garcia.layerView.controller;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.garcia.App;
import org.garcia.appVisitor.IVisitor;
import org.garcia.layerView.enums.ViewName;
import org.garcia.layerView.viewModel.IViewModel;
import org.garcia.layerView.viewModel.ToursViewModel;
import org.garcia.model.Tour;
import org.garcia.model.TourDirection;
import org.garcia.model.TourLog;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class ToursController implements Initializable, IController {
    private static final Logger logger = LogManager.getLogger(ToursController.class);
    private ToursViewModel viewModel;

    //tours
    @FXML
    public ListView<Tour> toursListView;
    @FXML
    public TextField inputSearch;
    @FXML
    public Button deleteTourBtn;
    @FXML
    public Button editTourBtn;
    @FXML
    public ListView<TourDirection> stepsListView;
    @FXML
    public ImageView tourImageView;
    @FXML
    public AnchorPane imageAnchorPane;
    @FXML
    public Text tourDescription;

    // menu
    @FXML
    public MenuItem createTourReportMenuItem;
    @FXML
    public MenuItem exportTourMenuItem;

    //log
    @FXML
    public Button addLogBtn;
    @FXML
    public Button editLogBtn;
    @FXML
    public Button deleteLogBtn;

    // table
    @FXML
    public TableView<TourLog> tourLogTableView;
    public TableColumn<TourLog, LocalDate> dateColumn;
    public TableColumn<TourLog, Integer> distanceColumn;
    public TableColumn<TourLog, Integer> durationColumn;
    public TableColumn<TourLog, Integer> ratingColumn;
    public TableColumn<TourLog, String> sportColumn;
    public TableColumn<TourLog, Integer> avgColumn;
    public TableColumn<TourLog, LocalTime> startColumn;
    public TableColumn<TourLog, LocalTime> arrivalColumn;
    public TableColumn<TourLog, Integer> specialColumn;
    public TableColumn<TourLog, Integer> reportColumn;

    @FXML
    public void searchTours() {
        viewModel.searchTours();
        inputSearch.textProperty().setValue("");
    }

    @FXML
    public void deleteTour() {
        viewModel.deleteTour();
        searchTours();
    }

    @FXML
    public void clearTours() {
        viewModel.clearObservableLists();
        inputSearch.textProperty().setValue("");
    }

    @FXML
    public void deleteLogTour() {
        viewModel.deleteTourLog();
    }

    @FXML
    public void openAddTourDialog() {
        viewModel.getEditMode().set(false);
        App.openDialog(ViewName.ADD_TOUR.getViewName(), ViewName.ADD_TOUR.getViewTitle(), viewModel);
    }

    @FXML
    public void openAddLogDialog() {
        viewModel.getEditMode().set(false);
        App.openDialog(ViewName.ADD_LOG.getViewName(), ViewName.ADD_LOG.getViewTitle(), viewModel);
    }

    @FXML
    public void openEditTour() {
        if (viewModel.getCurrentTour().getOrigin() != null)
            viewModel.getEditMode().set(true);
            App.openDialog(ViewName.EDIT_TOUR.getViewName(), ViewName.EDIT_TOUR.getViewTitle(), viewModel);
    }

    @FXML
    public void openEditLog() {
        if (viewModel.getCurrentTourLog() != null)
            viewModel.getEditMode().set(true);
            App.openDialog(ViewName.EDIT_LOG.getViewName(), ViewName.EDIT_LOG.getViewTitle(), viewModel);
    }

    @FXML
    public void openSaveReportDialog() {
        App.openDialog(ViewName.SAVE_TOUR_REPORT.getViewName(), ViewName.SAVE_TOUR_REPORT.getViewTitle(), viewModel);
    }

    public void closeApp() {
        Platform.exit();
        System.exit(0);
    }

    public void exportTour() {
        viewModel.exportTour();
    }

    public void importTour() {
        viewModel.importTour();
        searchTours();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewModel = ToursViewModel.getInstance();
        viewModel.defineAppManager();
        initializeTours();
        initializeLogs();
        makeElementsDisableable();
        viewModel.tourSelected(viewModel.getCurrentTour());
    }

    private void makeElementsDisableable() {
        addLogBtn.disableProperty().bind(toursListView.getSelectionModel().selectedItemProperty().isNull());
        editLogBtn.disableProperty().bind(tourLogTableView.getSelectionModel().selectedItemProperty().isNull());
        deleteLogBtn.disableProperty().bind(tourLogTableView.getSelectionModel().selectedItemProperty().isNull());
        deleteTourBtn.disableProperty().bind(toursListView.getSelectionModel().selectedItemProperty().isNull());
        editTourBtn.disableProperty().bind(toursListView.getSelectionModel().selectedItemProperty().isNull());
//        reportTypeChoice.disableProperty().bind(viewModel.getTourImageProperty().isNull());
        createTourReportMenuItem.disableProperty().bind(viewModel.getMenuItemDisabled());
        exportTourMenuItem.disableProperty().bind(viewModel.getMenuItemDisabled());

    }

    private void initializeLogs() {
        addTourLogListener();

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        sportColumn.setCellValueFactory(new PropertyValueFactory<>("sport"));
        avgColumn.setCellValueFactory(new PropertyValueFactory<>("avgSpeed"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        arrivalColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        specialColumn.setCellValueFactory(new PropertyValueFactory<>("special"));
        reportColumn.setCellValueFactory(new PropertyValueFactory<>("report"));

        tourLogTableView.setItems(viewModel.getTourLogObservableList());
    }

    private void initializeTours() {
        setTourListViewFormatCells();
        setDirectionsViewFormatCells();
        addTourListener();

        inputSearch.textProperty().bindBidirectional(viewModel.getInputSearch());
        viewModel.searchTours();
        tourDescription.textProperty().bind(viewModel.getCurrentTourDescription());
        tourImageView.fitWidthProperty().bind(imageAnchorPane.widthProperty());
        tourImageView.fitHeightProperty().bind(imageAnchorPane.heightProperty());
        tourImageView.imageProperty().bindBidirectional(viewModel.getTourImageProperty());
        toursListView.setItems(viewModel.getTourObservableList());
        stepsListView.setItems(viewModel.getTourDirections());
    }

    public void addTourListener() {
        toursListView.getSelectionModel().selectedItemProperty().addListener(
                ((observableValue, oldTour, newTour) -> {
                    if ((newTour != null) && (oldTour != newTour) && (newTour.getId() != 0)) {
                        viewModel.tourSelected(newTour);
                    } else {
                        clearTours();
                    }
                })
        );
    }

    public void addTourLogListener() {
        tourLogTableView.getSelectionModel().selectedItemProperty().addListener(
                (((observableValue, oldLog, newLog) -> {
                    if (newLog != null && (oldLog != newLog)) {
                        viewModel.setCurrentTourLog(newLog);
                    }
                }))
        );
    }

    public void setTourListViewFormatCells() {
        toursListView.setCellFactory((param -> new ListCell<>() {
            @Override
            protected void updateItem(Tour tour, boolean empty) {
                super.updateItem(tour, empty);
                if (empty || (tour == null) || tour.getTitle() == null) { // TODO: validation in business layer
                    setText(null);
                } else {
                    setText(tour.getTitle() + ": " + tour.getOrigin() + " -> " + tour.getDestination());
                    setId(String.valueOf(tour.getId()));
                }
            }
        }));
    }

    public void setDirectionsViewFormatCells() {
        stepsListView.setCellFactory((param -> new ListCell<>() {

            @Override
            protected void updateItem(TourDirection tourDirection, boolean empty) {
                super.updateItem(tourDirection, empty);
                if (!empty && (tourDirection != null) && !tourDirection.getDirection().equals("")) {
                    String local = tourDirection.getIconUrl().replace("http://content.mqcdn.com/mqsite/turnsigns/","org/garcia/img/icons/");
                    String online = tourDirection.getIconUrl();
                    ImageView imageView;
                    try {
                        imageView = new ImageView(local);
                    } catch (IllegalArgumentException e) {
                        logger.warn("Image not found locally (" + online + "). " + e);
                        imageView = new ImageView(online);
                    }
                    setGraphic(imageView);
                    setText(tourDirection.getDirection());
                }
            }
        }));
    }

    @Override
    public void accept(IVisitor visitor, IViewModel viewModel) {
        visitor.visit(this, viewModel);
    }

    public void initViewModel(IViewModel previousViewModel) {
        viewModel.init(previousViewModel);
    }

    public void showDirections(Event event) {

    }
}
