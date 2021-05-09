package org.garcia.layerBusiness.appmanager;

public class AppManagerFactory {

    private static IAppManager managerSingleton;

    public static IAppManager getInstance(IAppManager manager) {
        if (managerSingleton == null)
            managerSingleton = manager;
        return managerSingleton;
    }
}
