package org.garcia.layerDataAccess.common;

import org.garcia.layerDataAccess.dbconnection.DBConnection;
import org.garcia.layerDataAccess.dbconnection.PostgresDBConnection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;

import java.io.IOException;
import java.sql.SQLException;

class PostgresDBConnectionTest {
    @Mock
    DBConnection db = new PostgresDBConnection("postgresDB1");;
    DBConnection db2 = new PostgresDBConnection("postgresDAA");;

    PostgresDBConnectionTest() throws IOException {
    }

    @Test
    @DisplayName("Connection established (fails if docker isn't running")
    void testConnectionEstablished() throws SQLException, IOException {
        Assertions.assertNotNull(db.connect());
    }

    @Test
    @DisplayName("Container doesn't exist. Connection can't be established. SQLException thrown")
    void containerNotFoundThrowsSQLException() {
        SQLException thrown = Assertions.assertThrows(SQLException.class,
                () -> db2.connect(),
                "Expected to throw exception cause the container doesn't exist");
        Assertions.assertEquals("Connection Failed", thrown.getMessage());
    }
}
