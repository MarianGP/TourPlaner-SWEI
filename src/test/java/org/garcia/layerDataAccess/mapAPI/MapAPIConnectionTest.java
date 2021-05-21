package org.garcia.layerDataAccess.mapAPI;

import org.garcia.config.ConfigurationManager;
import org.garcia.layerDataAccess.mapAPI.ApiCollection.Response;
import org.garcia.model.Tour;
import org.junit.jupiter.api.*;
import org.mockito.Mock;

import java.io.IOException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MapAPIConnectionTest {
    private static Response response = new Response();

    @Mock
    MapAPIConnection api = new MapAPIConnection(ConfigurationManager.getConfigProperty("mapQuestApi.key"));

    MapAPIConnectionTest() throws IOException {
    }

    @Test
    @Order(1)
    void getRoute() throws IOException {
        Tour tour = new Tour(1, "first", "nice long tour1", "wien", "graz", "me", "org/garcia/img/dummy.png", 10, 60);
        response = api.getRoute(tour);
        Assertions.assertNotNull(response.getRoute().getSessionId());
    }

    @Test
    @Order(2)
    void getMapWithSessionId() throws IOException {
        Assertions.assertFalse(api.getMap(response).contains("dummy.png"));
    }
}
