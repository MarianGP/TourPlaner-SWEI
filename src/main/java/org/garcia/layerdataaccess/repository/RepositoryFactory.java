package org.garcia.layerdataaccess.repository;

import org.garcia.layerdataaccess.dbconnection.DBConnection;

public class RepositoryFactory {
    private static IRepository repo;

    public static IRepository getInstance(DBConnection dbConnection) {
        if(repo == null)
            repo = new Repository(dbConnection);
        return repo;
    }
}
