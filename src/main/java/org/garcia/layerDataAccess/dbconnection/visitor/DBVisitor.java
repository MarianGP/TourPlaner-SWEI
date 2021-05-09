package org.garcia.layerDataAccess.dbconnection.visitor;

import org.garcia.layerDataAccess.repository.Repository;

public interface DBVisitor {
    void visit(Repository repository);
}
