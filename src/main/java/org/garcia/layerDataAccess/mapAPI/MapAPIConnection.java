package org.garcia.layerDataAccess.mapAPI;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.garcia.layerBusiness.util.NameGenerator;
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

    public String getMap(Tour tour) throws IOException {
        String fileName = NameGenerator.getRandomName() + ".jpg";
        String origin = escapeEmptySpace(tour.getOrigin());
        String destination = escapeEmptySpace(tour.getDestination());
        String url = "https://www.mapquestapi.com/staticmap/v5/map?key=" + apiKey +
                "&start=" + origin + "&end=" + destination +
                "&size=300,200@2x&type=light&defaultMarker=circle-7B0099-sm";

        return "org/garcia/img/" + ((sendRequest(url, fileName, "img")) ? fileName : "dummy.png");
    }

    public String getMap(Response response) throws IOException {
        String coordinates =
                        response.getRoute().getBoundingBox().getUl().getLat() + "," +
                        response.getRoute().getBoundingBox().getUl().getLng() + "," +
                        response.getRoute().getBoundingBox().getLr().getLat() + "," +
                        response.getRoute().getBoundingBox().getLr().getLng();
        String sessionId = response.getRoute().getSessionId();

        String url = "https://www.mapquestapi.com/staticmap/v5/map?key=" + apiKey +
                "&size=640,480@2x&type=light&defaultMarker=circle-7B0099-sm&session=" + sessionId +
                "&boundingBox=" + coordinates + "&zoom=11";

        String fileName = NameGenerator.getRandomName() + ".jpg";

        return "org/garcia/img/" + ((sendRequest(url, fileName, "img")) ? fileName : "dummy.png");
    }

    public String getDirections(Tour tour) throws IOException {
        String fileName = NameGenerator.getRandomName() + ".txt";
        String origin = escapeEmptySpace(tour.getOrigin());
        String destination = escapeEmptySpace(tour.getDestination());

        String url = "http://www.mapquestapi.com/directions/v2/route?key=" + apiKey +
                "&from=" + origin +
                "&to=" + destination;

        String location = ((sendRequest(url, fileName, "dir")) ? fileName : "default.txt");
        return "org/garcia/dir/" + location;
    }

    public Response getRoute(Tour tour) throws IOException {
        String origin = escapeEmptySpace(tour.getOrigin());
        String destination = escapeEmptySpace(tour.getDestination());

        String url = "http://www.mapquestapi.com/directions/v2/route?key=" + apiKey +
                "&from=" + origin +
                "&to=" + destination;
        Response routeResponse = null;
        HttpURLConnection connection = httpConnection(url);
        Gson gson = new Gson();
        BufferedReader br;
        String json;




        if (connection.getResponseCode() == 200) {
            try {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                json = br.readLine();
                routeResponse = gson.fromJson(json, Response.class);
            } catch (IOException | JsonSyntaxException e) {
                e.printStackTrace();
            }

            connection.disconnect();
        }
        return routeResponse;
    }

    private boolean sendRequest(String aURL, String fileName, String directory) throws IOException {
        HttpURLConnection connection = httpConnection(aURL);

        if (connection.getResponseCode() == 200) {
            InputStream is = connection.getInputStream();
            if (is != null)
                fileAccess.saveFile(is, fileName, directory);
            connection.disconnect();
            return true;
        }

        connection.disconnect();
        return false;
    }

    private String escapeEmptySpace(String location) {
        return location.replace(" ", "+");
    }


}
