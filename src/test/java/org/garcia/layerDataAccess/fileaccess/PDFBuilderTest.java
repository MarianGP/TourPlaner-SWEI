package org.garcia.layerDataAccess.fileaccess;

import org.garcia.model.Tour;
import org.garcia.model.TourLog;
import org.garcia.model.enums.Sport;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

class PDFBuilderTest {
    @Mock
    Tour currentTour = new Tour(1, "first", "nice long tour1", "wien", "graz", "me", "org/garcia/img/dummy.png", 10, 60);
    @Mock
    List<TourLog> foundTourLogs = new ArrayList<>();


    @Test
    void createTourPdf() throws IOException {
        foundTourLogs.add(new TourLog(0, 1, LocalDate.of(2012, 6, 30), 25, 60, 1, Sport.BIKE, 20, LocalTime.now(), LocalTime.now(), 120, 1));
        foundTourLogs.add(new TourLog(2, 1, LocalDate.of(2018, 9, 30), 30, 60, 1, Sport.WALK, 20, LocalTime.now(), LocalTime.now(), 120, 1));
        foundTourLogs.add(new TourLog(0, 2, LocalDate.of(2012, 6, 30), 25, 60, 1, Sport.BIKE, 20, LocalTime.now(), LocalTime.now(), 120, 1));
        PDFBuilder.createTourPdf(currentTour, "C:\\Users\\mgarc\\IdeaProjects\\Projects\\TourPlaner_SWE\\src\\tour-with-logs-summary.pdf", foundTourLogs);
    }

    @Test
    void createTourWithOutLogsPdf() throws IOException {
        PDFBuilder.createTourPdf(currentTour, "C:\\Users\\mgarc\\IdeaProjects\\Projects\\TourPlaner_SWE\\src\\no-logs-summary.pdf", foundTourLogs);
    }
}
