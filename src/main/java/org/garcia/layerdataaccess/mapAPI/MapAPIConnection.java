package org.garcia.layerdataaccess.mapAPI;

import org.garcia.layerbusiness.util.NameGenerator;
import org.garcia.layerdataaccess.fileaccess.FileAccess;
import org.garcia.model.Tour;

import java.io.IOException;
import java.io.InputStream;
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

    public String getMap(Tour tour) throws IOException {
        String fileName = NameGenerator.getRandomName() + ".jpg";
        String origin = escapeEmptySpace(tour.getOrigin());
        String destination = escapeEmptySpace(tour.getDestination());
        String url = "https://www.mapquestapi.com/staticmap/v5/map?key=" + apiKey +
                "&start=" + origin + "&end=" + destination +
                "&size=300,200@2x&type=light&defaultMarker=circle-7B0099-sm";

        return "org/garcia/img/" + ( (sendRequest(url, fileName, "img")) ? fileName : "kupo.jpg" );
    }

    public String getDirections(Tour tour) throws IOException {
        String fileName = NameGenerator.getRandomName() + ".txt";
        String url = "http://www.mapquestapi.com/directions/v2/route?key=" + apiKey +
                "&from=" + tour.getOrigin() + "&to=" + tour.getDestination();

        return "org/garcia/dir/" + ( (sendRequest(url, fileName, "dir")) ? fileName : "default.txt" );
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

    private String escapeEmptySpace(String location) {
        return location.replace(" ", "+");
    }


}
