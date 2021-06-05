package org.garcia.layerView.viewModel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.garcia.layerBusiness.IAppManager;
import org.garcia.model.Tour;
import org.garcia.model.TourLog;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

@Data
public class ReportFormViewModel implements IViewModel {

    private static final Logger logger = LogManager.getLogger(ReportFormViewModel.class);
    private static final String TOUR_REPORT_NAME = "tour";
    private static final String SUMMARY_REPORT_NAME = "summary";
    private static final String WRONG_INPUT = "Wrong input";

    private static ReportFormViewModel viewModel;

    private IAppManager appManager;
    private Tour currentTour;
    private TourLog currentTourLog = null;
    private BooleanProperty isTourSelected = new SimpleBooleanProperty();
    private StringProperty reportTypeName = new SimpleStringProperty();
    private StringProperty reportUrl = new SimpleStringProperty();
    private StringProperty reportName = new SimpleStringProperty();

    public static ReportFormViewModel getInstance() {
        if (viewModel == null) {
            viewModel = new ReportFormViewModel();
        }
        return viewModel;
    }

    public boolean createReport() throws IOException {
        String reportType = reportTypeName.getValue().toLowerCase(Locale.ROOT);
        if (reportUrl.getValue() != null && reportName.getValue() != null) {
            String url = reportUrl.getValue() + "\\"
                    + reportName.getValue() + "-" + reportType + ".pdf";
            createSelectedReport(url, reportType);
            return true;
        } else {
            reportUrl.setValue("* Wrong input");
            logger.warn(WRONG_INPUT);
            return false;
        }
    }

    public void createSelectedReport(String url, String report) throws IOException {
        if (SUMMARY_REPORT_NAME.equals(report)) {
            List<Tour> allTours = appManager.searchTours("");
            if (allTours != null)
                appManager.createSummaryReport(url, allTours);
        } else if (TOUR_REPORT_NAME.equals(report)) {
            if (currentTour != null)
                appManager.createTourReport(currentTour, url);
        }
    }

    public void setReportUrlValue(String absolutePath) {
        reportUrl.setValue(absolutePath);
    }

    @Override
    public void init(IViewModel previousViewModel) {
        appManager = previousViewModel.getAppManager();
        if (currentTour == null)
            currentTour = previousViewModel.getCurrentTour();
    }

}
