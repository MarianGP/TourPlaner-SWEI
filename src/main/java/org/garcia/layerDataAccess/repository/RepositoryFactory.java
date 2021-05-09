package org.garcia.layerDataAccess.repository;

import org.garcia.layerDataAccess.dbconnection.DBConnection;

public class RepositoryFactory {
    private static IRepository repo;

    public static IRepository getInstance(DBConnection dbConnection) {
        if(repo == null)
            repo = new Repository(dbConnection);
        return repo;
    }
}
