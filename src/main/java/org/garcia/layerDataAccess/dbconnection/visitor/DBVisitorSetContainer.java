package org.garcia.layerDataAccess.dbconnection.visitor;

import org.garcia.layerDataAccess.dbconnection.DBConnection;
import org.garcia.layerDataAccess.repository.Repository;

public class DBVisitorSetContainer implements DBVisitor {

    @Override
    public void visit(Repository repository) {
        DBConnection dbConnection = repository.getDbConnection();
        dbConnection.changeContainer(repository.getContainerName());
    }
}
