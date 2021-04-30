package org.garcia.layerdataaccess.persistance.dbconnection;

import lombok.AllArgsConstructor;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

@AllArgsConstructor
public class PostgresDBConnection implements DBConnection {

    private static Connection conn;
    private String dbFullPath;
    private String dbUsername;
    private String dbPassword;
    private String dbName;

    public PostgresDBConnection() {
        Path configDirectory = Paths.get("src", "main", "java", "org", "garcia", "config");
        String absolutePath = configDirectory.toFile().getAbsolutePath();
        String filePath = absolutePath + "\\app.properties";
        Properties properties = new Properties();

        installDriver();

        try {
            properties.load(new FileInputStream(filePath));
            setDBProperties(properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setDBProperties(Properties prop) {
        dbName = prop.getProperty("db.name");
        dbUsername = prop.getProperty("db.username");
        dbPassword = prop.getProperty("db.password");
        dbFullPath = prop.getProperty("db.url") + ":" + prop.getProperty("db.port") + "/" + dbName;
    }

    @Override
    public Connection connect() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(dbFullPath, dbUsername, dbPassword);
                conn.setAutoCommit(false);
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
                return null;
            }
        }
        return conn;
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
