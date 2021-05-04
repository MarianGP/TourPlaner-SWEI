package org.garcia.layerdataaccess.common.dbconnection;

public class DBConnectionFactory {
    private static DBConnection connSingleton;

    public static DBConnection getInstance(DBConnection connection) {
        if(connSingleton == null)
            connSingleton = connection;
        return connSingleton;
    }


}
