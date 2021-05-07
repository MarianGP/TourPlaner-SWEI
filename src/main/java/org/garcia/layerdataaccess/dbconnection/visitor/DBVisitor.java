package org.garcia.layerdataaccess.dbconnection.visitor;

import org.garcia.layerdataaccess.repository.Repository;

public interface DBVisitor {
    void visit(Repository repository);
}
