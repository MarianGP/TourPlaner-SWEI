package org.garcia.layerDataAccess.dbconnection;

import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.garcia.config.ConfigurationManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Data
public class PostgresDBConnection implements DBConnection {

    private static final Logger logger = LogManager.getLogger(PostgresDBConnection.class);

    private String containerName;

    public PostgresDBConnection(String aContainerName) {
        this.containerName = aContainerName;
        installDriver();
    }

    @Override
    public Connection connect() {
        try {
            Connection conn = DriverManager.getConnection(
                    ConfigurationManager.getConfigProperty(this.containerName + ".url"),
                    ConfigurationManager.getConfigProperty(this.containerName + ".username"),
                    ConfigurationManager.getConfigProperty(this.containerName + ".password"));
            conn.setAutoCommit(false);
            return conn;
        } catch (SQLException e) {
            logger.error(e.getClass().getName() + ": " + e.getMessage());
        }
        return null;
    }

    @Override
    public void installDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("PostgreSQL JDBC driver not found.");
            e.printStackTrace();
        }
    }

    @Override
    public void changeContainer(String containerName) {
        this.containerName = containerName;
    }

}
