package org.garcia.layerDataAccess.service;

import org.garcia.layerDataAccess.dbconnection.PostgresDBConnection;
import org.garcia.layerDataAccess.repository.Repository;
import org.garcia.model.Tour;
import org.junit.jupiter.api.*;
import org.mockito.Mock;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TourServiceTest {
    private static int newTourId;
    private static final String EXISTING_TERM_IN_DB = "nice long";
    private static final String NON_EXISTING_TERM_IN_DB = "something_that_doesnt_exist_in_the_db";

    @Mock
    Tour tour = new Tour(0, "first", "nice long tour1", "wien", "graz", "me", "org/garcia/img/dummy.png", 10, 60);
    PostgresTourService tourService = new PostgresTourService(new Repository(new PostgresDBConnection("postgresDB1")));

    TourServiceTest()  {
    }

    @Test
    @Order(1)
    @DisplayName("Add new tour")
    void TestAddTour()  {
        newTourId = tourService.addTour(tour);
        Assertions.assertNotEquals(0, newTourId);
    }

    @Test
    @Order(2)
    @DisplayName("Search Tours by existing and non existing term")
    void TestFindBySearchInput() {
        List<Tour> tourList = tourService.findBySearchInput(EXISTING_TERM_IN_DB);
        Assertions.assertNotEquals(0, tourList.size());
        List<Tour> tourList2 = tourService.findBySearchInput(NON_EXISTING_TERM_IN_DB);
        Assertions.assertEquals(0, tourList2.size());
    }

    @Test
    @Order(3)
    @DisplayName("Edit tour title and description")
    void TestEditTour() {
        Tour editedTour = new Tour(newTourId, "new_title_keyword_45789%&", "new description", "wien", "graz", "me", "org/garcia/img/dummy.png", 10, 60);
        tourService.editTour(editedTour);
        List<Tour> modified = tourService.findBySearchInput("new_title_keyword_45789%&");
        Assertions.assertEquals(1, modified.size());
    }

    @Test
    @Order(4)
    @DisplayName("Delete newly created and edited tour")
    void TestDeleteTour()  {
        Assertions.assertEquals(newTourId, tourService.deleteTour(newTourId));
    }
}
