package org.garcia;

import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.garcia.config.ConfigurationManager;
import org.garcia.layerBusiness.AppManagerDB;
import org.garcia.layerBusiness.AppManagerFactory;
import org.garcia.layerBusiness.AppManagerMock;
import org.garcia.layerBusiness.IAppManager;
import org.garcia.layerDataAccess.common.DALOracleFactory;
import org.garcia.layerDataAccess.common.DALPostgresFactory;
import org.garcia.layerDataAccess.common.IDALFactory;

@Data
public class AppConfiguration {

    private static IDALFactory factory;
    private static final Logger logger = LogManager.getLogger(AppConfiguration.class);
    private static final String DB_POSTGRES = "postgres";
    private static final String DB_ORACLE = "oracle";
    private static final String MANAGER = "mockedManager";
    private static final String DB_TYPE = "dbType";

    public static IAppManager initAppConfiguration() {
        boolean mockedDB;
        String dbType = "";

        try {
            mockedDB = Boolean.parseBoolean(ConfigurationManager.getConfigProperty(MANAGER));
            dbType = ConfigurationManager.getConfigProperty(DB_TYPE);
        } catch (Exception e) {
            logger.fatal("Configuration property: " + dbType + " doesn't exist.\n" + e);
            throw new IllegalStateException(e);
        }

        logger.info("Mocked Application: " + mockedDB);
        if (mockedDB)
            return AppManagerFactory.getInstance(new AppManagerMock(factory));

        switch (dbType) {
            case DB_POSTGRES:
                factory = new DALPostgresFactory();
                logger.info("Chosen DB: " + DB_POSTGRES);
                break;
            case DB_ORACLE:
                factory = new DALOracleFactory(); // not implemented only as example
                logger.info("Chosen DB: " + DB_ORACLE);
                break;
            default:
                logger.fatal("Chosen configuration option doesn't exist in config file. Unexpected value: " + dbType);
        }

        return AppManagerFactory.getInstance(new AppManagerDB(factory));
    }
}
