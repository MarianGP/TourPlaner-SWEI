package org.garcia.layerdataaccess.service;

import lombok.AllArgsConstructor;
import org.garcia.layerdataaccess.repository.Repository;

@AllArgsConstructor
public class ServiceFactory {
    private final Repository repository;
    private IService tourService = null;
    private IService tourLogService = null;

    public ServiceFactory(Repository repository) {
        this.repository = repository;
    }

    public IService getTourServiceInstance() {
        if(tourService == null) {
            tourService = new TourService(repository);
        }
        return tourService;
    }

    public IService getTourLogServiceInstance() {
        if(tourLogService == null) {
            tourLogService = new TourLogService(repository);
        }
        return tourLogService;
    }
}
