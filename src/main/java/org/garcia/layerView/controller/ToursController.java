package org.garcia.layerView.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.garcia.App;
import org.garcia.layerView.enums.ViewName;
import org.garcia.layerView.viewModel.IViewModel;
import org.garcia.layerView.viewModel.ToursViewModel;
import org.garcia.model.Tour;
import org.garcia.model.TourLog;
import org.garcia.visitor.IVisitor;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class ToursController implements Initializable, IController {

    //tours
    public ListView<Tour> toursListView = new ListView<>();
    public TextField inputSearch = new TextField("");
    public Button deleteTourBtn = new Button();
    public Button editTourBtn = new Button();

    //selected tour
    public ImageView tourImageView = new ImageView();
    public AnchorPane imageAnchorPane = new AnchorPane();
    public Text tourDescription = new Text();

    //log
    public Button addLogBtn = new Button();
    public Button editLogBtn = new Button();
    public Button deleteLogBtn = new Button();

    // menu
    public MenuItem createTourReportMenuItem = new MenuItem();
    public MenuItem exportTourMenuItem = new MenuItem();

    // report
//    public ChoiceBox<String> reportTypeChoice = new ChoiceBox<>();
    private ToursViewModel viewModel;

    // logs table
    public TableView<TourLog> tourLogTableView = new TableView<>();
    public TableColumn<TourLog, LocalDate> dateColumn = new TableColumn<>();
    public TableColumn<TourLog, Integer> distanceColumn = new TableColumn<>();
    public TableColumn<TourLog, Integer> durationColumn = new TableColumn<>();
    public TableColumn<TourLog, Integer> ratingColumn = new TableColumn<>();
    public TableColumn<TourLog, String> sportColumn = new TableColumn<>();
    public TableColumn<TourLog, Integer> avgColumn = new TableColumn<>();
    public TableColumn<TourLog, LocalTime> startColumn = new TableColumn<>();
    public TableColumn<TourLog, LocalTime> arrivalColumn = new TableColumn<>();
    public TableColumn<TourLog, Integer> specialColumn = new TableColumn<>();
    public TableColumn<TourLog, Integer> reportColumn = new TableColumn<>();

    @FXML
    public void searchTours() {
        viewModel.searchTours(inputSearch.textProperty().getValue());
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
    public void openAddTourDialog() throws IOException {
        App.openDialog(ViewName.ADD_TOUR.getViewName(), ViewName.ADD_TOUR.getViewTitle(), viewModel);
    }

    @FXML
    public void openAddLogDialog() throws IOException {
        if (viewModel.getCurrentTour().getOrigin() != null) {
            App.openDialog(ViewName.ADD_LOG.getViewName(), ViewName.ADD_LOG.getViewTitle(), viewModel);
        }
    }

    @FXML
    public void openEditTour() throws IOException {
        if (viewModel.getCurrentTour().getOrigin() != null)
            viewModel.getEditMode().set(true);
            App.openDialog(ViewName.EDIT_TOUR.getViewName(), ViewName.EDIT_TOUR.getViewTitle(), viewModel);
    }

    @FXML
    public void openEditLog() throws IOException {
        if (viewModel.getCurrentTourLog() != null)
            viewModel.getEditMode().set(true);
            App.openDialog(ViewName.EDIT_LOG.getViewName(), ViewName.EDIT_LOG.getViewTitle(), viewModel);
    }

    @FXML
    public void openSaveReportDialog() throws IOException {
        App.openDialog(ViewName.SAVE_TOUR_REPORT.getViewName(), ViewName.SAVE_TOUR_REPORT.getViewTitle(), viewModel);
    }

    public void closeApp() {
        Platform.exit();
        System.exit(0);
    }

    public void exportTour() throws IOException {
        viewModel.exportTour();
    }

    public void importTour() throws IOException {
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
        addTourListener();

        viewModel.searchTours("");
        toursListView.setItems(viewModel.getTourObservableList());
        tourDescription.textProperty().bind(viewModel.getCurrentTourDescription());
        tourImageView.fitWidthProperty().bind(imageAnchorPane.widthProperty());
        tourImageView.imageProperty().bind(viewModel.getTourImageProperty());
//        Bindings.bindBidirectional(tourImageView.imageProperty(), toursViewModel.getTourImageProperty());
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

    @Override
    public void accept(IVisitor visitor, IViewModel viewModel) {
        visitor.visit(this, viewModel);
    }

    public void initViewModel(IViewModel previousViewModel) {
        viewModel.init(previousViewModel);
    }
}
