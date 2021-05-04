package org.garcia.layerdataaccess.common.dbconnection;
import java.io.IOException;
import java.sql.*;

public interface DBConnection {

    Connection connect() throws SQLException, IOException;
    void installDriver();
}
