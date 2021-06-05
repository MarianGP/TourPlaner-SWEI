package org.garcia.layerDataAccess.fileaccess;

import org.garcia.model.Tour;
import org.garcia.model.TourData;
import org.garcia.model.TourLog;

import java.util.List;
import java.util.Map;

public interface IFileAccess {
    void exportData(String name, String url, Map<Tour, List<TourLog>> tourListMap);
    void exportTours(String name, String url, List<TourData> tourPairList);
    List<Tour> getToursFromFile(String fileName, String location);
    List<TourLog> getTourLogsFromFile(String fileName, String location);
    List<TourData> getTourDataFromFile(String test2, String dir);
}
