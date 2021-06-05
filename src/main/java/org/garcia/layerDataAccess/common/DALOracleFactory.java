package org.garcia.layerDataAccess.common;

import org.garcia.layerDataAccess.fileaccess.FileAccess;
import org.garcia.layerDataAccess.mapAPI.MapAPIConnection;
import org.garcia.layerDataAccess.service.ITourDirectionsService;
import org.garcia.layerDataAccess.service.ITourLogService;
import org.garcia.layerDataAccess.service.ITourService;

public class DALOracleFactory implements IDALFactory {
    @Override
    public void init(String containerName) {

    }

    @Override
    public ITourService createTourService() {
        return null;
    }

    @Override
    public ITourLogService createTourLogService() {
        return null;
    }

    @Override
    public ITourDirectionsService createDirectionService() {
        return null;
    }

    @Override
    public FileAccess createFileAccess() {
        return null;
    }

    @Override
    public MapAPIConnection createMapAPIConnection(String apiName) {
        return null;
    }
}
