package org.garcia.layerDataAccess.service;

import org.garcia.layerDataAccess.dbconnection.PostgresDBConnection;
import org.garcia.layerDataAccess.repository.Repository;
import org.garcia.model.Tour;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

class TourServiceTest {

    @Mock
    PostgresTourService tourService = new PostgresTourService(new Repository(new PostgresDBConnection("postgresDB1")));

    TourServiceTest() throws IOException {
    }

    @Test
    void TestFindBySearchInput() throws SQLException {
        List<Tour> tourList = tourService.findBySearchInput("s");
        System.out.println(tourList);
    }

    @Test
    void TestAddTour() throws SQLException {
        System.out.println(tourService.addTour(null));
    }

    @Test
    void TestDeleteTour()  throws  SQLException {
        //System.out.println(tourService.deleteTour(50));
    }
}
