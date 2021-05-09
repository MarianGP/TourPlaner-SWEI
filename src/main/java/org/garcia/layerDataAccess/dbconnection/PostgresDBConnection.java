package org.garcia.layerDataAccess.dbconnection;

import lombok.Data;
import org.garcia.layerBusiness.ConfigurationManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Data
public class PostgresDBConnection implements DBConnection {

    private String containerName;

    public PostgresDBConnection(String aContainerName) {
        this.containerName = aContainerName;
        installDriver();
    }

    @Override
    public Connection connect() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(
                    ConfigurationManager.getConfigProperty(this.containerName + ".url"),
                    ConfigurationManager.getConfigProperty(this.containerName + ".username"),
                    ConfigurationManager.getConfigProperty(this.containerName + ".password"));
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

    @Override
    public void changeContainer(String containerName) {
        this.containerName = containerName;
    }

}
