package org.garcia.layerdataaccess.service;

import org.garcia.layerdataaccess.repository.Repository;

import java.sql.SQLException;
import java.util.List;

public class TestingService {

    private final Repository repository;

    public TestingService(Repository repo) {
        repository = repo;
    }

    public void addTestingTours() throws SQLException {
        String query = "INSERT INTO public.tour (tour_id, title, description, origin, destination, user_id, distance, duration, img) " +
        "VALUES (1, 'first', 'nice long-deleteInTest', 'wien', 'linz', 1, 200, 3, 'org/garcia/img/tp-buhevugaqo.jpg');" +
        "INSERT INTO public.tour (tour_id, title, description, origin, destination, user_id, distance, duration, img) " +
        "VALUES (2, 'second', 'nice short tour-deleteInTest', 'lienz', 'salzburg', 1, 200, 3, 'org/garcia/img/tp-zivozapelo.jpg')";

        List<Object> parameters = null;
        repository.modifyResources(query, parameters);
    }
}
