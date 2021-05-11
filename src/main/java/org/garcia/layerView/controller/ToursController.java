package org.garcia.layerView.controller;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.garcia.App;
import org.garcia.layerBusiness.appmanager.AppManagerDB;
import org.garcia.layerBusiness.appmanager.AppManagerFactory;
import org.garcia.layerBusiness.appmanager.IAppManager;
import org.garcia.layerView.viewModel.ToursViewModel;
import org.garcia.model.Tour;
import org.garcia.model.TourLog;
import org.garcia.model.enums.ViewName;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
    public String tourStringChoiceBox;
    ToursViewModel toursViewModel;
    IAppManager tourManager;

    //! tours - methods
    public void searchTours() throws SQLException {
        toursViewModel.clearObservableList();
        toursViewModel.clearLogsObservableList();
        List<Tour> foundTours = tourManager.searchTours(inputSearch.textProperty().getValue());
        toursViewModel.addAllToursObsList(foundTours);
        inputSearch.textProperty().setValue("");
    }

    public void addTour(ActionEvent actionEvent) throws SQLException, IOException {
        String[] inputFields = {title.getText(), origin.getText(), destination.getText(), description.getText()};
        int tourId = tourManager.addTour(inputFields);
        if (tourId > 0) {
            searchTours();
//            toursViewModel.setCurrentTour(toursViewModel.getTourObservableList().get(toursViewModel.getTourObservableList().size()-1));
        } else {
            System.out.println("Wrong input");
        }
        closeDialog(actionEvent);
    }

    public void deleteTour() throws SQLException {
        if (tourManager.deleteTour(toursViewModel.getCurrentTour())) {
            toursViewModel.setCurrentTour(null);
//            toursViewModel.getTourImageProperty(); // TODO: disable again when currentTour is deleted
            searchTours();
        }
    }

    public void clearTours() {
        toursViewModel.clearObservableList();
        toursViewModel.clearLogsObservableList();
        inputSearch.textProperty().setValue("");
        tourDescription.textProperty().setValue("");
//        tourImageView.setImage(null);
    }


    //! logs - methods
    public void searchTourLogs(int tourId) throws SQLException {
        toursViewModel.clearLogsObservableList();
        List<TourLog> foundTourLogs = tourManager.searchLogsByTourId(tourId);
        if (foundTourLogs != null) {
            toursViewModel.addTourLogs(foundTourLogs);
        }
    }

    public void addLog(ActionEvent actionEvent) throws SQLException {
        int tourId = toursViewModel.getCurrentTour().getId();
        Object[] inputFields = {
                toursViewModel.getLocalDate(),
                Integer.parseInt(logDuration.getText()),
                Integer.parseInt(logDistance.getText())};
        if (tourManager.addLog(inputFields, tourId) != 0) {
            closeDialog(actionEvent);
            searchTourLogs(tourId);
        } else {
            System.out.println("Wrong input");
        }
    }

    public void deleteLogTour() throws SQLException {
        int logId = toursViewModel.getCurrentLog().getId();
        if (!tourManager.deleteLogById(logId)) {
            System.out.println("Todo. Show alerts"); // TODO: alerts
        }
        searchTourLogs(toursViewModel.getCurrentLog().getTourId());
        System.out.println("delete: " + logId);
    }


    //! buttons - methods
    @FXML
    public void openAddTourDialog() throws IOException {
        DatePicker dp = new DatePicker();
        datePicker.getChildren().add(dp);
        App.openDialog(ViewName.ADD_TOUR.getViewName(), "Add Tour");
    }

    @FXML
    public void openAddLogDialog() throws IOException {
        if (toursViewModel.getCurrentTour().getOrigin() != null) {
            App.openDialog(ViewName.ADD_LOG.getViewName(), "Add Tour");
        }
    }

    @FXML
    public void openSaveReportDialog() throws IOException {
        App.openDialog(ViewName.SAVE_TOUR_REPORT.getViewName(), "Save a report");
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
    public void exportTour() throws SQLException, IOException {
        tourManager.exportTourNLogs("data", "dir");
    }

    public void importTour() throws SQLException, IOException {
        tourManager.importTourNLogs("testing-import", "dir");
        searchTours();
    }

    public void createReport(ActionEvent actionEvent) throws IOException, SQLException {
        String url;
        String reportType = toursViewModel.getReportTypeName().getValue().toLowerCase(Locale.ROOT);
        if (toursViewModel.getReportUrl().getValue() != null && toursViewModel.getReportName().getValue() != null) {
            closeDialog(actionEvent);
            url = toursViewModel.getReportUrl().getValue() + "\\"
                    + toursViewModel.getReportName().getValue() + "-" + reportType + ".pdf";
            createSelectedReport(url, reportType);
            filePath.setStyle("-fx-font-size: 11; -fx-text-fill: green;");
        } else {
            toursViewModel.getReportUrl().setValue("* Wrong input");
            filePath.setStyle("-fx-font-size: 11; -fx-text-fill: red;");
        }
        chosenReportType.setText("");
    }

    private void createSelectedReport(String url, String report) throws IOException, SQLException {
        if (toursViewModel.getSummaryReportName().equals(report))
            tourManager.createSummaryReport(url, new ArrayList<>(toursViewModel.getTourObservableList())); //TODO: url!

        else if (toursViewModel.getTourReportName().equals(report))
            if (toursViewModel.getCurrentTour() != null)
                tourManager.createTourReport(toursViewModel.getCurrentTour(), url);
    }

    public void openDirectoryChooser() {
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("src"));

        Stage stage = (Stage) paneView.getScene().getWindow();
        File file = directoryChooser.showDialog(stage);

        if (file != null) {
            toursViewModel.getReportUrl().setValue(file.getAbsolutePath());
        }
    }


    //! initialize - bindings
    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        toursViewModel = ToursViewModel.getInstance();
        tourManager = AppManagerFactory.getInstance(new AppManagerDB());
        initializeTours();
        initializeLogs();
        initializeReports();
        makeElementsDisableable();
    }

    private void initializeReports() {
        // report type
        Bindings.bindBidirectional(toursViewModel.getReportTypeName(), reportTypeChoice.valueProperty());
        Bindings.bindBidirectional(chosenReportType.textProperty(), toursViewModel.getReportTypeName());

        // report name
        Bindings.bindBidirectional(reportName.textProperty(), toursViewModel.getReportName());

        // report path - base url
        Bindings.bindBidirectional(filePath.textProperty(), toursViewModel.getReportUrl());
    }

    private void makeElementsDisableable() {
        // enable/disable log btn
        addLogBtn.disableProperty().bind(toursListView.getSelectionModel().selectedItemProperty().isNull());
        deleteLogBtn.disableProperty().bind(tourLogTableView.getSelectionModel().selectedItemProperty().isNull());

        // enable/disable add tour btn
        deleteTourBtn.disableProperty().bind(toursListView.getSelectionModel().selectedItemProperty().isNull());

        // disable choice-box if no current tour
        reportTypeChoice.disableProperty().bind(toursViewModel.getTourImageProperty().isNull());

        // will be disabled when toursList is empty
        createTourReportMenuItem.disableProperty().bind(toursViewModel.getMenuItemDisabled());
        createSummaryMenuItem.disableProperty().bind(toursViewModel.getMenuItemDisabled());
        exportTourMenuItem.disableProperty().bind(toursViewModel.getMenuItemDisabled());
    }

    private void initializeLogs() {
        setDatePicker();
        addTourLogListener();

        //set logs table
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
        tourLogTableView.setItems(toursViewModel.getTourLogObservableList());
    }

    private void initializeTours() throws SQLException {
        toursListView.setItems(toursViewModel.getTourObservableList());
        setTourListViewFormatCells();
        addTourListener();

        toursViewModel.clearObservableList();
        toursViewModel.addAllToursObsList(tourManager.searchTours(""));

        Bindings.bindBidirectional(tourDescription.textProperty(), toursViewModel.getCurrentTourDescription());

        tourImageView.fitWidthProperty().bind(imageAnchorPane.widthProperty());
        tourImageView.imageProperty().bindBidirectional(toursViewModel.getTourImageProperty());
//        Bindings.bindBidirectional(tourImageView.imageProperty(), toursViewModel.getTourImageProperty());
    }

    public void addTourListener() {
        toursListView.getSelectionModel().selectedItemProperty().addListener(
                ((observableValue, oldTour, newTour) -> {
                    if ((newTour != null) && (oldTour != newTour) && (newTour.getId() != 0)) {
                        toursViewModel.setCurrentTour(newTour);

                        try {
                            searchTourLogs(toursViewModel.getCurrentTour().getId());
                            String tourUrl = toursViewModel.getCurrentTour().getImg();
                            System.out.println("Current tour: " + tourUrl);
                            toursViewModel.getTourImageProperty().setValue(new Image(tourUrl));
                            // tourImageView.setImage(new Image(toursViewModel.getCurrentTour().getImg()));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
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
                        toursViewModel.setCurrentLog(newLog);
                    }
                    System.out.println("current log: " + toursViewModel.getCurrentLog());
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
            toursViewModel.setLocalDate(ld);
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
