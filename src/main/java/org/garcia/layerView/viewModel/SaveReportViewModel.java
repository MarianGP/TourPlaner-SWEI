package org.garcia.layerView.viewModel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;
import org.garcia.layerBusiness.appmanager.IAppManager;
import org.garcia.model.Tour;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

@Data
public class SaveReportViewModel implements IViewModel {

    private static final String TOUR_REPORT_NAME = "tour";
    private static final String SUMMARY_REPORT_NAME = "summary";
    private static SaveReportViewModel viewModel;

    private IAppManager appManager;
    private Tour currentTour;
    private BooleanProperty isTourSelected = new SimpleBooleanProperty();
    private StringProperty reportTypeName = new SimpleStringProperty();
    private StringProperty reportUrl = new SimpleStringProperty();
    private StringProperty reportName = new SimpleStringProperty();

    public static SaveReportViewModel getInstance() {
        if (viewModel == null) {
            viewModel = new SaveReportViewModel();
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
