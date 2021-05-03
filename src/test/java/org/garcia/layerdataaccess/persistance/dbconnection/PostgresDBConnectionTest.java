package org.garcia.layerdataaccess.persistance.dbconnection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.sql.Connection;
import java.sql.SQLException;

class PostgresDBConnectionTest {
    @Mock
    DBConnection db = new PostgresDBConnection();;

    @Test
    @DisplayName("Connection established")
    void testConnectionEstablished() throws SQLException {
        Connection conn = db.connect();
        Assertions.assertNotNull(conn);
    }
}
