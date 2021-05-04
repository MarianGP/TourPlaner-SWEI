package org.garcia.layerdataaccess.common.dbconnection;

import org.garcia.config.ConfigurationManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDBConnection implements DBConnection {

    public PostgresDBConnection() {
        installDriver();
    }

    @Override
    public Connection connect() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(
                    ConfigurationManager.getConfigProperty("db.url"),
                    ConfigurationManager.getConfigProperty("db.username"),
                    ConfigurationManager.getConfigProperty("db.password"));
            conn.setAutoCommit(false);
            return conn;
        } catch (SQLException | IOException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        throw new SQLException("Connection Failed");
    }

    @Override
    public void installDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC driver not found");
            e.printStackTrace();
        }
    }

}
