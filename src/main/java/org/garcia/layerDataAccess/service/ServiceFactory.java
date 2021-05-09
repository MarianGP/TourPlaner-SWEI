package org.garcia.layerDataAccess.service;

import lombok.AllArgsConstructor;
import org.garcia.layerDataAccess.repository.Repository;

@AllArgsConstructor
public class ServiceFactory {

    private static IService tourService = null;
    private static IService tourLogService = null;

    public static IService getTourServiceInstance(Repository repository) {
        if(tourService == null) {
            tourService = new TourService(repository);
        }
        return tourService;
    }

    public static IService getTourLogServiceInstance(Repository repository) {
        if(tourLogService == null) {
            tourLogService = new TourLogService(repository);
        }
        return tourLogService;
    }
}
