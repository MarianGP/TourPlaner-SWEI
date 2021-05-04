package org.garcia.layerdataaccess.mapAPI;

import org.garcia.layerbusiness.util.NameGenerator;
import org.garcia.model.Tour;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MapAPIConnection {

    private final static String API_KEY = "7iPw8LaPBNon9nRTgKkwuL9zL6KfnQPD"; // TODO: ConfigManager Class

    public String getMap(Tour tour) throws IOException {
        String fileName = NameGenerator.getRandomName() + ".jpg";
        String origin = escapeEmptySpace(tour.getOrigin());
        String destination = escapeEmptySpace(tour.getDestination());
        String url = "https://www.mapquestapi.com/staticmap/v5/map?key=" + API_KEY +
                "&start=" + origin + "&end=" + destination +
                "&size=300,200@2x&type=light&defaultMarker=circle-7B0099-sm";

        return "org/garcia/img/" + ( (sendRequest(url, fileName, "img")) ? fileName : "kupo.jpg" );
    }

    public String getDirections(Tour tour) throws IOException {
        String fileName = NameGenerator.getRandomName() + ".txt";
        String url = "http://www.mapquestapi.com/directions/v2/route?key=" + API_KEY +
                "&from=" + tour.getOrigin() + "&to=" + tour.getDestination();

        return "org/garcia/dir/" + ( (sendRequest(url, fileName, "dir")) ? fileName : "default.txt" );
    }

    private boolean sendRequest(String aURL, String fileName, String directory) throws IOException {
        HttpURLConnection connection = connect(aURL);

        if (connection.getResponseCode() == 200) {
            InputStream is = connection.getInputStream();
            if (is != null)
                saveFile(is, fileName, directory);
            connection.disconnect();
            return true;
        }

        connection.disconnect();
        return false;
    }

    private HttpURLConnection connect(String aURL) {
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

    private void saveFile(InputStream is, String name, String location) throws IOException {
        Path configDirectory = Paths.get("src", "main", "resources", "org", "garcia", location);
        String absolutePath = configDirectory.toFile().getAbsolutePath();

        FileOutputStream fos = new FileOutputStream(new File(absolutePath, name));
        int read = 0;
        byte[] buffer = new byte[32768];
        while ((read = is.read(buffer)) > 0) {
            fos.write(buffer, 0, read);
        }

        fos.close();
        is.close();
    }

    private String escapeEmptySpace(String location) {
        return location.replace(" ", "+");
    }

    public void deleteFile(String location, String fileName){
        Path configDirectory = Paths.get("src", "main", "resources");
        String absolutePath = configDirectory.toFile().getAbsolutePath();

        try {
            File f= new File(absolutePath + "\\" + fileName);       //file to be delete
            if(f.delete()) {
                System.out.println(f.getName() + " deleted");   //getting and printing the file name
            } else {
                System.out.println("failed");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }


}
