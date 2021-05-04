package org.garcia.layerdataaccess.service;

import org.garcia.layerdataaccess.common.dbconnection.PostgresDBConnection;
import org.garcia.layerdataaccess.repository.Repository;
import org.garcia.model.Tour;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

class TourServiceTest {

    @Mock
    TourService tourService = new TourService(new Repository(new PostgresDBConnection()));

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
