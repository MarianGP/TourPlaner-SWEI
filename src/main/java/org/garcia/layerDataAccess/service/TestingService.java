package org.garcia.layerDataAccess.service;

import org.garcia.layerDataAccess.repository.Repository;

import java.util.List;

public class TestingService {

    private final Repository repository;

    public TestingService(Repository repo) {
        repository = repo;
    }

    public void addTestingTours() {
        String query = "INSERT INTO public.tour (tour_id, title, description, origin, destination, user_id, distance, duration, img) " +
        "VALUES (1, 'first', 'nice long-deleteInTest', 'wien', 'linz', 1, 200, 3, 'org/garcia/img/dummy.jpg');" +
        "INSERT INTO public.tour (tour_id, title, description, origin, destination, user_id, distance, duration, img) " +
        "VALUES (2, 'second', 'nice short tour-deleteInTest', 'lienz', 'salzburg', 1, 200, 3, 'org/garcia/img/dummy.jpg')";

        List<Object> parameters = null;
        repository.modifyResources(query, parameters);
    }
}
