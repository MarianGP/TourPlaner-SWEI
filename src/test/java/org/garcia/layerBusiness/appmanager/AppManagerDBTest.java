package org.garcia.layerBusiness.appmanager;

import org.garcia.layerDataAccess.common.DALPostgresFactory;
import org.garcia.layerDataAccess.dbconnection.PostgresDBConnection;
import org.garcia.layerDataAccess.repository.Repository;
import org.garcia.layerDataAccess.service.TestingService;
import org.garcia.model.Tour;
import org.garcia.model.enums.Sport;
import org.junit.Before;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AppManagerDBTest {
    private static int newTourId;
    private static int newLogId;

    TestingService testingService = new TestingService(new Repository(new PostgresDBConnection("postgresDB1")));
    AppManagerDB appManager = new AppManagerDB(new DALPostgresFactory());
    String NEW_TOUR_TITLE = "Nice Day";
    String NEW_TOUR_ORIGIN = "Santiago";
    String NEW_TOUR_DESTINATION = "Punta Arenas";
    String NEW_TOUR_DESCRIPTION = "ALhhhhFNIUre-deleteInTest";

    AppManagerDBTest() {

    }

    @Before
    public void init() throws SQLException, InterruptedException {
        deleteNewlyCreatedTourTest();
        testingService.addTestingTours();
    }

    @Test
    @DisplayName("add new tour")
    @Order(1)
    void testTryAdd2TourFirstWrongInputSecondWorksTest() throws SQLException, IOException {
        String[] parameters = {NEW_TOUR_TITLE, NEW_TOUR_ORIGIN, NEW_TOUR_DESTINATION, "@%!ñKLHFOHS"};
        Assertions.assertEquals(0, appManager.addTour(parameters));
        parameters = new String[]{NEW_TOUR_TITLE, NEW_TOUR_ORIGIN, NEW_TOUR_DESTINATION, NEW_TOUR_DESCRIPTION};
        newTourId = appManager.addTour(parameters);
        Assertions.assertNotEquals(0, newTourId);
    }

    @Test
    @Order(2)
    void searchToursTest() {
        Tour createdTour = appManager.searchTours(NEW_TOUR_DESCRIPTION).get(0);
        Assertions.assertNotNull(createdTour);
    }

    @Test
    @Order(3)
    @DisplayName("add log to new tour")
    void addLogTest() {
        Object[] parameters = {LocalDate.of(2012, 6, 30), 25, 60, 1, Sport.BIKE, 20, LocalTime.now(), LocalTime.now(), 120, 1};
        newLogId = appManager.addLog(parameters, newTourId);
        Assertions.assertNotEquals(0, newLogId);
    }

    @Test
    @Order(4)
    void importTourNLogsTest() {
        Assertions.assertTrue(appManager.importTourNLogs("testing-import", "dir"));
        Assertions.assertTrue(appManager.searchTours("").size() >= 3);
    }

    @Test
    @Order(5)
    void exportTourNLogsTest() {
        Assertions.assertTrue(appManager.exportTourNLogs("data-export", "dir"));
    }

    @Test
    @Order(6)
    @DisplayName("Search logs (imported and added)")
    void searchLogsTest() {
        Assertions.assertEquals(1, appManager.searchLogsByTourId(newTourId).size());
        Assertions.assertEquals(2, appManager.searchLogsByTourId(1).size());
    }

    @Test
    @Order(7)
    @DisplayName("Create Summary Report from created tours")
    void createSummaryReportTest() throws IOException {
        List<Tour> tourList = appManager.searchTours("");
        appManager.createSummaryReport("C:\\\\Users\\\\mgarc\\\\IdeaProjects\\\\Projects\\\\TourPlaner_SWE\\\\src\\\\testing-summary-report.pdf", tourList);
    }

    @Test
    @Order(8)
    void deleteLogTest() {
        Object[] parameters = {LocalDate.of(2012, 6, 30), 25, 60, 1, Sport.BIKE, 20, LocalTime.now(), LocalTime.now(), 120, 1};
        newLogId = appManager.addLog(parameters, newTourId);
        Assertions.assertTrue(appManager.deleteLogById(newLogId));
    }

    @Test
    @Order(9)
    void deleteNewlyCreatedTourTest() throws InterruptedException {
        Thread.sleep(2000);
        List<Tour> cleanUpTours = appManager.searchTours("-deleteInTest");
        for (Tour tour : cleanUpTours) {
            Assertions.assertTrue(appManager.deleteTour(tour));
        }
    }
}
