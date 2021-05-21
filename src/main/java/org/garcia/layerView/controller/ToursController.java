package org.garcia.layerView.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.garcia.App;
import org.garcia.layerView.viewModel.ToursViewModel;
import org.garcia.model.Tour;
import org.garcia.model.TourLog;
import org.garcia.model.enums.ViewName;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ToursController implements Initializable {

    //new tour
    public TextField title;
    public TextField origin;
    public TextField destination;
    public TextField description;

    //tours
    public ListView<Tour> toursListView = new ListView<>();
    public TextField inputSearch = new TextField("");
    public Button deleteTourBtn = new Button();

    //selected tour
    public ImageView tourImageView = new ImageView();
    public AnchorPane imageAnchorPane = new AnchorPane();
    public Text tourDescription = new Text();

    //new log
    public TilePane datePicker = new TilePane();
    public TextField logDuration;
    public TextField logDistance;
    public Button addLogBtn = new Button();
    public Button deleteLogBtn = new Button();
    public ComboBox<String> sportComboBox;

    //logs
    public TableView<TourLog> tourLogTableView = new TableView<>();
    public TableColumn<TourLog, LocalDate> dateColumn = new TableColumn<>();
    public TableColumn<TourLog, Integer> durationColumn = new TableColumn<>();
    public TableColumn<TourLog, Integer> distanceColumn = new TableColumn<>();
    // menu
    public MenuItem createSummaryMenuItem = new MenuItem();
    public MenuItem createTourReportMenuItem = new MenuItem();
    public MenuItem exportTourMenuItem = new MenuItem();
    // report
    public AnchorPane paneView = new AnchorPane();
    public TextField reportName = new TextField();
    public ChoiceBox<String> reportTypeChoice = new ChoiceBox<>();
    public Label filePath = new Label();
    public Label chosenReportType = new Label();
    private ToursViewModel viewModel;

    //! tours - methods
    public void searchTours() {
        viewModel.searchTours(inputSearch.textProperty().getValue());
        inputSearch.textProperty().setValue("");
    }

    public void addTour(ActionEvent actionEvent) throws IOException {
        String[] inputFields = {title.getText(), origin.getText(), destination.getText(), description.getText()};
        if (viewModel.addNewTour(inputFields) > 0) {
            closeDialog(actionEvent);
        } else {
            System.out.println("Couldn't add new tour");
        }
    }

    public void deleteTour() {
        viewModel.deleteTour();
        searchTours();
    }

    public void clearTours() {
        viewModel.clearObservableLists();
        inputSearch.textProperty().setValue("");
    }

    public void addTourLog(ActionEvent actionEvent) {
        Object[] inputFields = {
                viewModel.getLocalDate(),
                Integer.parseInt(logDuration.getText()),
                Integer.parseInt(logDistance.getText())};

        if (viewModel.addTourLog(inputFields) > 0)
            closeDialog(actionEvent);
    }

    public void deleteLogTour() {
        viewModel.deleteTourLog();
    }


    //! buttons - methods
    @FXML
    public void openAddTourDialog() throws IOException {
        DatePicker dp = new DatePicker();
        datePicker.getChildren().add(dp);
        App.openDialog(ViewName.ADD_TOUR.getViewName(), "Add Tour", viewModel);
    }

    @FXML
    public void openAddLogDialog() throws IOException {
        if (viewModel.getCurrentTour().getOrigin() != null) {
            App.openDialog(ViewName.ADD_LOG.getViewName(), "Add Log", viewModel);
        }
    }

    @FXML
    public void openSaveReportDialog() throws IOException {
        App.openDialog(ViewName.SAVE_TOUR_REPORT.getViewName(), "Save a report", viewModel);
    }

    public void closeDialog(@NotNull ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void closeApp() {
        Platform.exit();
        System.exit(0);
    }

    //! menu actions - methods
    public void exportTour() throws IOException {
        viewModel.exportTour();
    }

    public void importTour() throws IOException {
        viewModel.importTour();
        searchTours();
    }


    //! initialize - bindings
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewModel = ToursViewModel.getInstance();
        viewModel.defineAppManager();
        initializeTours();
        initializeLogs();
        makeElementsDisableable();
    }

    private void makeElementsDisableable() {
        // enable/disable log btn
        addLogBtn.disableProperty().bind(toursListView.getSelectionModel().selectedItemProperty().isNull());
        deleteLogBtn.disableProperty().bind(tourLogTableView.getSelectionModel().selectedItemProperty().isNull());

        // enable/disable add tour btn
        deleteTourBtn.disableProperty().bind(toursListView.getSelectionModel().selectedItemProperty().isNull());

        // disable choice-box if no current tour
        reportTypeChoice.disableProperty().bind(viewModel.getTourImageProperty().isNull());

        // will be disabled when toursList is empty
        createTourReportMenuItem.disableProperty().bind(viewModel.getMenuItemDisabled());
        createSummaryMenuItem.disableProperty().bind(viewModel.getMenuItemDisabled());
        exportTourMenuItem.disableProperty().bind(viewModel.getMenuItemDisabled());
    }

    private void initializeLogs() {
        setDatePicker();
        addTourLogListener(); // TODO:

        //set logs table
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
        tourLogTableView.setItems(viewModel.getTourLogObservableList());
    }

    private void initializeTours() {
        setTourListViewFormatCells();
        addTourListener(); // TODO

        viewModel.searchTours("");
        toursListView.setItems(viewModel.getTourObservableList());
        tourDescription.textProperty().bind(viewModel.getCurrentTourDescription());
        tourImageView.fitWidthProperty().bind(imageAnchorPane.widthProperty());
        tourImageView.imageProperty().bind(viewModel.getTourImageProperty());
//        Bindings.bindBidirectional(tourImageView.imageProperty(), toursViewModel.getTourImageProperty());
    }

    //TODO: refactor
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

    //TODO: refactor
    public void addTourLogListener() {
        tourLogTableView.getSelectionModel().selectedItemProperty().addListener(
                (((observableValue, oldLog, newLog) -> {
                    if (newLog != null && (oldLog != newLog)) {
                        viewModel.setCurrentLog(newLog);
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

    private void setDatePicker() {
        Label l = new Label("no date selected");
        DatePicker dp = new DatePicker();

        EventHandler<ActionEvent> event = e -> {
            LocalDate ld = dp.getValue();
            viewModel.setLocalDate(ld);
            l.setText(ld.toString());
        };

        // show week numbers
        dp.setShowWeekNumbers(true);

        // when datePicker is pressed
        dp.setOnAction(event);

        // add button and label
        datePicker.getChildren().add(dp);
        datePicker.getChildren().add(l);
    }
}
