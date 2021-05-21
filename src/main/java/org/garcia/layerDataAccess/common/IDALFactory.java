package org.garcia.layerDataAccess.common;

import org.garcia.layerDataAccess.fileaccess.FileAccess;
import org.garcia.layerDataAccess.mapAPI.MapAPIConnection;
import org.garcia.layerDataAccess.service.ITourLogService;
import org.garcia.layerDataAccess.service.ITourService;

public interface IDALFactory {
    void init(String containerName);

    ITourService createTourService();

    ITourLogService createTourLogService();

    FileAccess createFileAccess();

    MapAPIConnection createMapAPIConnection(String apiName);
}
