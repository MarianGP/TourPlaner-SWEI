package org.garcia.layerDataAccess.fileaccess;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.garcia.model.Tour;
import org.garcia.model.TourLog;
import org.garcia.model.TourData;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FileAccess implements IFileAccess {

    private final static String FILE_SUFIX_TOURLOG = "-log";
    private final static String FILE_SUFIX_TOUR = "-tour";
    private final static String FILE_SUFIX_EXPORT = "-export";

    @Override
    public void exportData(String name, String url, Map<Tour, List<TourLog>> tourListMap) throws IOException {
        Gson gson = new Gson();

        StringBuffer exportJson = new StringBuffer();
        for(Map.Entry<Tour, List<TourLog>> entry: tourListMap.entrySet()) {
            exportJson.append(gson.toJson(entry.getKey()));
            exportJson.append("\n");
        }
        InputStream inputStream = new ByteArrayInputStream(exportJson.toString().getBytes());
        saveFile(inputStream, name + FILE_SUFIX_TOUR, url);

        exportJson = new StringBuffer();
        for(Map.Entry<Tour, List<TourLog>> entry: tourListMap.entrySet()) {
            if(entry.getValue().size() != 0) {
                exportJson.append(gson.toJson(entry.getValue()));
                exportJson.append("\n");
            }
        }
        exportJson.replace(exportJson.length()-1,exportJson.length(),"");
        inputStream = new ByteArrayInputStream(exportJson.toString().getBytes());
        saveFile(inputStream, name + FILE_SUFIX_TOURLOG, url);
    }

    public void exportTours(String name, String url, List<TourData> tourPairList) throws IOException {
        Gson gson = new Gson();
        StringBuffer exportJson = new StringBuffer();

        for(TourData pair: tourPairList) {
            exportJson.append(gson.toJson(pair, TourData.class));
            exportJson.append("\n");
        }

        InputStream inputStream = new ByteArrayInputStream(exportJson.toString().getBytes());
        saveFile(inputStream, name + FILE_SUFIX_EXPORT, url);
    }

    public List<Tour> getToursFromFile(String fileName, String location) {
        Path configDirectory = Paths.get("src", "main", "resources", "org", "garcia", location);
        String absolutePath = configDirectory.toFile().getAbsolutePath();
        List<Tour> tourList = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(absolutePath + "\\" + fileName + FILE_SUFIX_TOUR));       //file to be delete
            String line;
            while( (line = reader.readLine()) != null) {
                tourList.add(jsonToTour(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tourList;
    }

    public List<TourLog> getTourLogsFromFile(String fileName, String location) {
        Path configDirectory = Paths.get("src", "main", "resources", "org", "garcia", location);
        String absolutePath = configDirectory.toFile().getAbsolutePath();
        List<TourLog> tourLogList = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(absolutePath + "\\" + fileName + FILE_SUFIX_TOURLOG));       //file to be delete
            String line;
            while( (line = reader.readLine()) != null) {
                List<TourLog> logList = Arrays.asList(jsonToTourLog(line));
                tourLogList.addAll(logList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tourLogList;
    }

    @Override
    public List<TourData> getTourDataFromFile(String fileName, String location) throws IOException {
        Path configDirectory = Paths.get("src", "main", "resources", "org", "garcia", location);
        String absolutePath = configDirectory.toFile().getAbsolutePath();
        List<TourData> tourPairList = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(absolutePath + "\\" + fileName));
            String line;

            while( (line = reader.readLine()) != null) {
                tourPairList.add(jsonToTourPair(line));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tourPairList;
    }

    public Tour jsonToTour(String json) {
        Gson gson = new Gson();
        Tour tour = null;
        try {
            tour = gson.fromJson(json, Tour.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return tour;
    }

    public TourLog[] jsonToTourLog(String json) {
        Gson gson = new GsonBuilder().setLenient().create();
        TourLog[] log = null;
        try {
            log = gson.fromJson(json, TourLog[].class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return log;
    }


    public TourData jsonToTourPair(String json) {
        Gson gson = new Gson();
        TourData tourPairs = null;
        try {
            tourPairs = gson.fromJson(json, TourData.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return tourPairs;
    }

    public void saveFile(InputStream is, String name, String location) throws IOException {
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
