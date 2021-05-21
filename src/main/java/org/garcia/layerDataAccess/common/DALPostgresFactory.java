package org.garcia.layerDataAccess.common;

import org.garcia.config.ConfigurationManager;
import org.garcia.layerDataAccess.dbconnection.DBConnection;
import org.garcia.layerDataAccess.dbconnection.DBConnectionFactory;
import org.garcia.layerDataAccess.dbconnection.PostgresDBConnection;
import org.garcia.layerDataAccess.fileaccess.FileAccess;
import org.garcia.layerDataAccess.mapAPI.MapAPIConnection;
import org.garcia.layerDataAccess.repository.Repository;
import org.garcia.layerDataAccess.service.ITourLogService;
import org.garcia.layerDataAccess.service.ITourService;
import org.garcia.layerDataAccess.service.PostgresTourLogService;
import org.garcia.layerDataAccess.service.PostgresTourService;

public class DALPostgresFactory implements IDALFactory {

    private static String containerName;
    private static Repository repository;
    private ITourService tourService;
    private ITourLogService tourLogService;

    public void init(String name) {
        containerName = name;
        repository = new Repository(createDBConnection());
    }

    private DBConnection createDBConnection() {
        DBConnection dbConnection = new PostgresDBConnection(containerName);
        // DBVisitor visitor = new DBVisitorSetContainer(); -- connection fail -> change container (somewhere else)
        // visitor.visit(dbConnection,containerName);
        return DBConnectionFactory.getInstance(dbConnection);
    }

    public MapAPIConnection createMapAPIConnection(String mapApiName) {
        String mapApiKey = ConfigurationManager.getConfigProperty(mapApiName + ".key");
        return new MapAPIConnection(mapApiKey);
    }

    public ITourService createTourService() {
        if(this.tourService == null) {
            this.tourService = new PostgresTourService(repository);
        }
        return this.tourService;
    }

    public ITourLogService createTourLogService() {
        if(this.tourLogService == null) {
            this.tourLogService = new PostgresTourLogService(repository);
        }
        return this.tourLogService;
    }

    public FileAccess createFileAccess() {
        return new FileAccess();
    }
}
