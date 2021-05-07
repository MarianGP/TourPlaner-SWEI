package org.garcia.layerdataaccess.dbconnection.visitor;

import org.garcia.layerdataaccess.dbconnection.DBConnection;
import org.garcia.layerdataaccess.repository.Repository;

public class DBVisitorSetContainer implements DBVisitor {

    @Override
    public void visit(Repository repository) {
        DBConnection dbConnection = repository.getDbConnection();
        dbConnection.changeContainer(repository.getContainerName());
    }
}
