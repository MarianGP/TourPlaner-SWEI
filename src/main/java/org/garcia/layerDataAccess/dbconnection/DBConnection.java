package org.garcia.layerDataAccess.dbconnection;
import java.sql.Connection;

public interface DBConnection {
    Connection connect();
    void installDriver();
    void changeContainer(String containerName);
}
