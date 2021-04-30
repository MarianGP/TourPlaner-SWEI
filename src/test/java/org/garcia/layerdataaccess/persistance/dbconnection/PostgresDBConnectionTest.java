package org.garcia.layerdataaccess.persistance.dbconnection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.sql.Connection;

class PostgresDBConnectionTest {
    @Mock
    DBConnection connection = new PostgresDBConnection();

    @Test
    @DisplayName("Connection established")
    void testConnectionEstablished() {
        Connection conn = connection.connect();
        Assertions.assertNotNull(conn);
    }
}
