package org.garcia.layerDataAccess.fileaccess;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.garcia.model.Tour;
import org.garcia.model.TourData;
import org.garcia.model.TourLog;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FileAccess implements IFileAccess {
    private static final Logger logger = LogManager.getLogger(FileAccess.class);
    private static final String FILE_SUFIX_TOURLOG = "-log";
    private static final String FILE_SUFIX_TOUR = "-tour";
    private static final String FILE_SUFIX_EXPORT = "-export";

    @Override
    public void exportData(String name, String url, Map<Tour, List<TourLog>> tourListMap) {
        Gson gson = new Gson();

        StringBuilder exportJson = new StringBuilder();
        for(Map.Entry<Tour, List<TourLog>> entry: tourListMap.entrySet()) {
            exportJson.append(gson.toJson(entry.getKey()));
            exportJson.append("\n");
        }
        InputStream inputStream = new ByteArrayInputStream(exportJson.toString().getBytes());
        saveFile(inputStream, name + FILE_SUFIX_TOUR, url);

        exportJson = new StringBuilder();
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

    public void exportTours(String name, String url, List<TourData> tourPairList) {
        Gson gson = new Gson();
        StringBuilder exportJson = new StringBuilder();

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
            logger.error(e);
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
            logger.error(e);
        }
        return tourLogList;
    }

    @Override
    public List<TourData> getTourDataFromFile(String fileName, String location) {
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
            logger.error(e);
        }

        return tourPairList;
    }

    public Tour jsonToTour(String json) {
        Gson gson = new Gson();
        Tour tour = null;
        try {
            tour = gson.fromJson(json, Tour.class);
        } catch (JsonSyntaxException e) {
            logger.error(e);
        }
        return tour;
    }

    public TourLog[] jsonToTourLog(String json) {
        Gson gson = new GsonBuilder().setLenient().create();
        TourLog[] log = null;
        try {
            log = gson.fromJson(json, TourLog[].class);
        } catch (JsonSyntaxException e) {
            logger.error(e);
        }
        return log;
    }

    public TourData jsonToTourPair(String json) {
        Gson gson = new Gson();
        TourData tourPairs = null;
        try {
            tourPairs = gson.fromJson(json, TourData.class);
        } catch (JsonSyntaxException e) {
            logger.error(e);
        }
        return tourPairs;
    }

    public void saveFile(InputStream is, String name, String location) {
        Path configDirectory = Paths.get("src", "main", "resources", "org", "garcia", location);
        String absolutePath = configDirectory.toFile().getAbsolutePath();

        try {
            FileOutputStream fos = new FileOutputStream(new File(absolutePath, name));
            int read = 0;
            byte[] buffer = new byte[32768];
            while ((read = is.read(buffer)) > 0) {
                fos.write(buffer, 0, read);
            }
            fos.close();
            is.close();
            logger.info("File saved successfully");
        } catch (IOException e) {
            logger.error("Failed to save file");
            e.printStackTrace();
        }

    }

    public boolean deleteFile(String location, String fileName){
        Path configDirectory = Paths.get("src", "main", "resources");
        String absolutePath = configDirectory.toFile().getAbsolutePath();

        try {
            File f= new File(absolutePath + "\\" + fileName);       //file to be delete
            if(f.delete()) {
                logger.info("File " + f.getName() + " was successfully deleted");
            } else {
                logger.error("Failed to delete file");
            }
        }
        catch(Exception e) {
            logger.debug("Unexpected exception deleting file");
            return false;
        }
        return true;
    }
}
