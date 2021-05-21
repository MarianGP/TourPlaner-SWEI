package org.garcia.layerDataAccess.service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ServiceFactory implements IServiceFactory {

    private static IService tourService = null;
    private static IService tourLogService = null;
//
//    public static IService getTourServiceInstance(Repository repository) {
//        if(tourService == null) {
//            tourService = new PostgresTourService(repository);
//        }
//        return tourService;
//    }
//
//    public static IService getTourLogServiceInstance(Repository repository) {
//        if(tourLogService == null) {
//            tourLogService = new PostgresTourLogService(repository);
//        }
//        return tourLogService;
//    }
}
