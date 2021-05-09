package org.garcia.layerDataAccess.fileaccess;

import org.garcia.model.Tour;
import org.garcia.model.TourLog;
import org.garcia.model.TourData;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IFileAccess {
    void exportData(String name, String url, Map<Tour, List<TourLog>> tourListMap) throws IOException;
    void exportTours(String name, String url, List<TourData> tourPairList) throws IOException;
    List<Tour> getToursFromFile(String fileName, String location);
    List<TourLog> getTourLogsFromFile(String fileName, String location);
    List<TourData> getTourDataFromFile(String test2, String dir) throws IOException;
}
