package org.garcia.layerdataaccess.persistance.dbconnection;
import java.sql.*;

public interface DBConnection {

    Connection connect() throws SQLException;
    void installDriver();
}
