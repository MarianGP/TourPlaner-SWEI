package org.garcia.layerDataAccess.mapAPI;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.garcia.layerDataAccess.fileaccess.FileAccess;
import org.garcia.layerDataAccess.mapAPI.ApiCollection.Response;
import org.garcia.model.Tour;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MapAPIConnection {
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(MapAPIConnection.class);
    private static final String DUMMY_IMG_NAME = "dummy.png";
    private static String apiKey;
    private final FileAccess fileAccess = new FileAccess();

    public MapAPIConnection(String key) {
        apiKey = key;
    }

    private HttpURLConnection httpConnection(String aURL) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(aURL).openConnection();
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.disconnect();
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return connection;
    }

    public String getMap(Tour tour) {
        String fileName = NameGenerator.getRandomName() + ".jpg";
        String origin = escapeEmptySpace(tour.getOrigin());
        String destination = escapeEmptySpace(tour.getDestination());
        String url = "https://www.mapquestapi.com/staticmap/v5/map?key=" + apiKey +
                "&start=" + origin + "&end=" + destination +
                "&size=300,200@2x&type=light&defaultMarker=circle-7B0099-sm";

        return "org/garcia/img/" + ((sendRequest(url, fileName, "img")) ? fileName : "dummy.png");
    }

    public String getMap(Response response) {

        if (response == null) {
            logger.error("Empty response from MapQuest");
            return "org/garcia/img/" + DUMMY_IMG_NAME;
        }

        String fileName = NameGenerator.getRandomName() + ".jpg";
        String sessionId = response.getRoute().getSessionId();
        String coordinates =
                response.getRoute().getBoundingBox().getUl().getLat() + "," +
                response.getRoute().getBoundingBox().getUl().getLng() + "," +
                response.getRoute().getBoundingBox().getLr().getLat() + "," +
                response.getRoute().getBoundingBox().getLr().getLng();

        String url = "https://www.mapquestapi.com/staticmap/v5/map?key=" + apiKey +
                "&size=800,400@2x&type=light&defaultMarker=circle-7B0099-sm&session=" + sessionId +
                "&boundingBox=" + coordinates + "&zoom=11";

        boolean request = sendRequest(url, fileName, "img");

        if (request)
            logger.info("Map image was saved");
        else
            logger.error("Map image couldn't be saved");

        return "org/garcia/img/" + ((request) ? fileName : DUMMY_IMG_NAME);
    }

    public String getDirections(Tour tour) {
        String fileName = NameGenerator.getRandomName() + ".txt";
        String origin = escapeEmptySpace(tour.getOrigin());
        String destination = escapeEmptySpace(tour.getDestination());
        String url = "https://www.mapquestapi.com/directions/v2/route?key=" + apiKey +
                "&from=" + origin +
                "&to=" + destination;

        boolean request = sendRequest(url, fileName, "dir");
        String location = ((request) ? fileName : "default.txt");

        return "org/garcia/dir/" + location;
    }

    public Response getRoute(Tour tour) {
        String origin = escapeEmptySpace(tour.getOrigin());
        String destination = escapeEmptySpace(tour.getDestination());

        String url = "https://www.mapquestapi.com/directions/v2/route?key=" + apiKey +
                "&from=" + origin +
                "&to=" + destination;
        Response routeResponse = null;
        HttpURLConnection connection = httpConnection(url);
        Gson gson = new Gson();
        BufferedReader br;
        String json;

        try {
            if (connection.getResponseCode() == 200) {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                json = br.readLine();
                routeResponse = gson.fromJson(json, Response.class);
                connection.disconnect();
            }
        } catch (Exception e) {
            logger.error(e);
        }

        return routeResponse;
    }

    private boolean sendRequest(String aURL, String fileName, String directory) {
        HttpURLConnection connection = httpConnection(aURL);

        try {
            if (connection.getResponseCode() == 200) {
                InputStream is = connection.getInputStream();
                if (is != null)
                    fileAccess.saveFile(is, fileName, directory);
                connection.disconnect();
                return true;
            }
        } catch (IOException e) {
            logger.error(e);
        }
        connection.disconnect();
        return false;
    }

    private String escapeEmptySpace(String location) {
        return location.replace(" ", "+");
    }

}
