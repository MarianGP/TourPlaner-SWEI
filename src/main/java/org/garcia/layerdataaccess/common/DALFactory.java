package org.garcia.layerdataaccess.common;

import org.garcia.layerbusiness.ConfigurationManager;
import org.garcia.layerdataaccess.dbconnection.*;
import org.garcia.layerdataaccess.fileaccess.FileAccess;
import org.garcia.layerdataaccess.mapAPI.MapAPIConnection;
import org.garcia.layerdataaccess.repository.Repository;
import org.garcia.layerdataaccess.service.ServiceFactory;
import org.garcia.layerdataaccess.service.TourLogService;
import org.garcia.layerdataaccess.service.TourService;

import java.io.IOException;

public class DALFactory {

    private static String containerName;
    private static Repository repository;

    public static void init(String name) {
        containerName = name;
        repository = new Repository(createPostgresConnection());
    }

    private static DBConnection createPostgresConnection() {
        DBConnection dbConnection = new PostgresDBConnection(containerName);
        // -- visitor: connection fail -> change container (somewhere else)
        // DBVisitor visitor = new DBVisitorSetContainer();
        // visitor.visit(dbConnection,containerName);
        return DBConnectionFactory.getInstance(dbConnection);
    }

    public static MapAPIConnection createMapAPIConnection(String mapApiName) throws IOException {
        String mapApiKey = ConfigurationManager.getConfigProperty(mapApiName + ".key");
        return new MapAPIConnection(mapApiKey);
    }

    public static TourService createTourService() {
        return (TourService) ServiceFactory.getTourServiceInstance(repository);
    }

    public static TourLogService createTourLogService() {
        return (TourLogService) ServiceFactory.getTourLogServiceInstance(repository);
    }

    public static FileAccess createFileAccess() {
        return new FileAccess();
    }
}
