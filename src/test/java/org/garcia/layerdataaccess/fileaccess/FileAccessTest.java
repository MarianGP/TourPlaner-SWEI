package org.garcia.layerdataaccess.fileaccess;

import org.garcia.model.Tour;
import org.garcia.model.TourLog;
import org.garcia.model.TourData;
import org.garcia.model.enums.Sport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class FileAccessTest {
    String TESTING_FILE_NAME = "testing-import";
    @Mock
    IFileAccess fileAccess = new FileAccess();
    List<Tour> foundTours = new ArrayList<>();
    List<TourLog> foundTourLogs = new ArrayList<>();
    Map<Tour, List<TourLog>> tourListMap = new HashMap<>();
    List<TourData> tourPairList = new ArrayList<>();

    @BeforeEach
    void init() {
        foundTours.add(new Tour(1, "first", "nice long tour1", "wien", "graz", "me", "org/garcia/img/dummy.png", 10, 60));
        foundTours.add(new Tour(2, "second", "nice long tour2", "wien", "graz", "me", "org/garcia/img/dummy.png", 10, 60));
        foundTours.add(new Tour(3, "third", "nice long tour3", "wien", "graz", "me", "org/garcia/img/dummy.png", 10, 60));
        foundTourLogs.add(new TourLog(0, 1, LocalDate.of(2012, 6, 30), 25, 60, 1, Sport.BIKE, 20, LocalTime.now(), LocalTime.now(), 120, 1));
        foundTourLogs.add(new TourLog(2, 1, LocalDate.of(2018, 9, 30), 30, 60, 1, Sport.WALK, 20, LocalTime.now(), LocalTime.now(), 120, 1));
        foundTourLogs.add(new TourLog(0, 2, LocalDate.of(2012, 6, 30), 25, 60, 1, Sport.BIKE, 20, LocalTime.now(), LocalTime.now(), 120, 1));

        for (Tour tour : foundTours) {
            TourData tourPair = TourData.builder().tour(tour).build();
            List<TourLog> tourLogsList = foundTourLogs.stream()
                    .filter(x -> x.getTourId() == tour.getId())
                    .collect(Collectors.toList());
            tourListMap.put(tour, tourLogsList);
            tourPair.setLogs(tourLogsList);
            tourPairList.add(tourPair);
        }
    }

    @Test
    void testExportData() throws IOException {
        fileAccess.exportData("test", "dir", tourListMap);
    }

    @Test
    void testImportToursFromJson() {
        List<Tour> tours = fileAccess.getToursFromFile("test", "dir");
        System.out.println(tours + "\n");
        Assertions.assertNotEquals(0, tours.size());
    }

    @Test
    void testImportTourLogsFromJson() {
        List<TourLog> logs = fileAccess.getTourLogsFromFile("test", "dir");
        System.out.println(logs + "\n");
        Assertions.assertNotEquals(0, logs.size());
    }

    @Test
    void testExportTourAsOneClass() throws IOException {
        fileAccess.exportTours("test2", "dir", tourPairList);
    }

    @Test
    void testImportTourFromPairTourClassJson() throws IOException {
        List<TourData> tourPairs = fileAccess.getTourDataFromFile(TESTING_FILE_NAME, "dir");
        System.out.println(tourPairs);
        Assertions.assertNotEquals(0, tourPairs.size());
        Assertions.assertNotEquals(0, tourPairs.get(0).getTourLogList().size());
    }


}
