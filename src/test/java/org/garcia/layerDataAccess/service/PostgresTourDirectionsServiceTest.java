package org.garcia.layerDataAccess.service;

import org.garcia.layerBusiness.AppManagerDB;
import org.garcia.layerDataAccess.common.DALPostgresFactory;
import org.garcia.model.Tour;
import org.junit.jupiter.api.*;
import org.mockito.Mock;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostgresTourDirectionsServiceTest {

    private static int newTourId;

    @Mock
    AppManagerDB appManager = new AppManagerDB(new DALPostgresFactory());

    Tour tour = Tour.builder()
            .title("title")
            .origin("wien")
            .destination("graz")
            .description("")
            .build();
    @Test
    @Order(1)
    void addTourDirection() {
        newTourId = appManager.addTour(tour);
        int newDirectionId = appManager.getTourDirectionsService().addTourDirection(newTourId, "Move Left", "org.garcia");
        Assertions.assertNotEquals(0, newDirectionId);
    }

    @Test
    @Order(2)
    void findByTourId() {
        Assertions.assertNotEquals(0, appManager.getTourDirectionsService().findByTourId(newTourId).size());
    }

    @Test
    @Order(3)
    void deleteByTourId() {
        tour.setId(newTourId);
        appManager.deleteTour(tour);
        Assertions.assertEquals(0, appManager.getTourDirectionsService().findByTourId(newTourId).size());
    }
}
