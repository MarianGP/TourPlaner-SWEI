package org.garcia.layerdataaccess.httpConnection;

import org.garcia.layerdataaccess.mapAPI.MapAPIConnection;
import org.garcia.model.Tour;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;

class HttpConnectionTest {
    @Mock
    // TODO: sankt pölten
    Tour tour = new Tour(1, "first", "nice long tour1", "wien", "graz", "me", "org/garcia/img/kupo.jpg", 10, 60);
    MapAPIConnection mapAPI = new MapAPIConnection();

    @Test
    void testGetMap() throws IOException {
        Assertions.assertNotNull(mapAPI.getMap(tour));
    }

    @Test
    void testGetDirections() throws IOException {
        Assertions.assertNotNull(mapAPI.getDirections(tour));
    }

}
