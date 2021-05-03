package org.garcia.layerdataaccess.persistance.repository;

import org.garcia.layerdataaccess.persistance.dbconnection.DBConnection;

public class RepositoryFactory {
    private static IRepository repo;

    public static IRepository getInstance(DBConnection dbConnection) {
        if(repo == null)
            repo = new Repository(dbConnection);
        return repo;
    }
}
