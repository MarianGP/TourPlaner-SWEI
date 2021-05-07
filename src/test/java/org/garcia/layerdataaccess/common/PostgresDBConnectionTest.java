package org.garcia.layerdataaccess.common;

import org.garcia.layerdataaccess.dbconnection.DBConnection;
import org.garcia.layerdataaccess.dbconnection.PostgresDBConnection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

class PostgresDBConnectionTest {
    @Mock
    DBConnection db = new PostgresDBConnection("postgresDB1");;

    PostgresDBConnectionTest() throws IOException {
    }

    @Test
    @DisplayName("Connection established")
    void testConnectionEstablished() throws SQLException, IOException {
        Connection conn = db.connect();
        Assertions.assertNotNull(conn);
    }
}
