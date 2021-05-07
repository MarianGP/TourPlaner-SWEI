package org.garcia.layerdataaccess.dbconnection;

public class DBConnectionFactory {
    private static DBConnection connSingleton;

    public static DBConnection getInstance(DBConnection connection) {
        if(connSingleton == null)
            connSingleton = connection;
        return connSingleton;
    }


}
