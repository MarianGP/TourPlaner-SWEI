package org.garcia.layerBusiness;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.garcia.layerDataAccess.common.IDALFactory;
import org.garcia.layerDataAccess.fileaccess.FileAccess;
import org.garcia.layerDataAccess.fileaccess.PDFBuilder;
import org.garcia.layerDataAccess.mapAPI.ApiCollection.LegsItem;
import org.garcia.layerDataAccess.mapAPI.ApiCollection.ManeuversItem;
import org.garcia.layerDataAccess.mapAPI.ApiCollection.Response;
import org.garcia.layerDataAccess.mapAPI.MapAPIConnection;
import org.garcia.layerDataAccess.service.ITourDirectionsService;
import org.garcia.layerDataAccess.service.ITourLogService;
import org.garcia.layerDataAccess.service.ITourService;
import org.garcia.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsable for the interaction between GUI and data access layer.
 */
@Getter
public class AppManagerDB implements IAppManager {

    private static final Logger logger = LogManager.getLogger(AppManagerDB.class);
    private static final String CONTAINER_NAME = "postgresDB1";
    private static final String API_NAME = "mapQuestApi";
    private final ITourService tourService;
    private final ITourLogService tourLogService;
    private final ITourDirectionsService tourDirectionsService;
    private final MapAPIConnection mapAPI;
    private final FileAccess fileAccess;

    /**
     * DAL properties instantiated with a DALFactory
     * All returned factory objects are singletons.
     */
    public AppManagerDB(IDALFactory factory) { // TODO: IFactory choose factory
        factory.init(CONTAINER_NAME);
        tourService = factory.createTourService();
        tourLogService = factory.createTourLogService();
        mapAPI = factory.createMapAPIConnection(API_NAME);
        fileAccess = factory.createFileAccess();
        tourDirectionsService = factory.createDirectionService();
    }

    /**
     * @param inputSearch String is the term used for filtering tours. if empty the whole db tour list in returned
     * @return a tour list of term filtered tours
     */
    @Override
    public List<Tour> searchTours(String inputSearch) {
        if (inputSearch.equals(""))
            return tourService.findAll();
        else
            return tourService.findBySearchInput(inputSearch);
    }


    @Override
    public List<TourDirection> searchDirections(int tourId) {
        return tourDirectionsService.findByTourId(tourId);
    }

    /**
     * Search in the db for tourLogs
     *
     * @param id from current/selected tour is used for filtering tourLogs
     * @return tourLogs list of tourLogs with tourId = id. if non found list is null
     */
    @Override
    public List<TourLog> searchLogsByTourId(int id) {
        return tourLogService.findByTourId(id);
    }


    /**
     * Create a new tour in db.
     * The input parameters are validated and then a new tour is added to the db.
     *
     * @param newTour is a Tour object with only 4 properties coming from addTourDialog view
     * @return id number of the new added tour
     */
    @Override
    public int addTour(Tour tour) {
        if (tour != null) {
            Response resp = mapAPI.getRoute(tour);
            if (resp == null)
                return -1;
            String imgPath = mapAPI.getMap(resp);
            if (imgPath.contains("dummy"))
                return -1;
            tour.setImg(imgPath);
            tour.setDistance((int) resp.getRoute().getDistance());
            tour.setDuration(resp.getRoute().getTime());
            int newTourId = tourService.addTour(tour);
            addTourDirections(newTourId, resp);
            return newTourId;
        }
        return 0;
    }

    private void addTourDirections(int newTourId, Response resp) {
        List<LegsItem> leg = resp.getRoute().getLegs();
        if (leg.size() > 0) {
            for (ManeuversItem maneuver: leg.get(0).getManeuvers()) {
                tourDirectionsService.addTourDirection(newTourId, maneuver.getNarrative(), maneuver.getIconUrl());
            }
        }
    }

    @Override
    public int editTour(Tour tour) {
        return tourService.editTour(tour);
    }

    /**
     * Add new tourLog to existing tour
     * Input terms are fist validated and mapped to a new tourLog if valid
     *
     * @param tourLog@return tourLog id when tourLog successfully added to db
     */
    @Override
    public int addLog(TourLog tourLog) {
        int logId = 0;
        if (tourLog != null)
            logId = tourLogService.addTourLog(tourLog);
        return logId;
    }

    @Override
    public int editLog(TourLog tourLog) {
        int logId = 0;
        if (tourLog != null)
            logId = tourLogService.editTourLog(tourLog);
        return logId;
    }

    @Override
    public boolean deleteTour(Tour tour) {
        if (tourService.deleteTour(tour.getId()) != 0) {
            return fileAccess.deleteFile("img", tour.getImg());
        }
        return false;
    }
    @Override
    public boolean deleteLogById(int id) {
        if (id != 0) {
            return tourLogService.deleteById(id) != 0;
        }
        return false;
    }

    @Override
    public boolean importTourNLogs(String fileName, String location) {
        List<TourData> tourDataList = fileAccess.getTourDataFromFile(fileName, location);
        if (tourDataList.size() == 0) return false;
        for (TourData tourData : tourDataList) {
            Tour tour = Tour.builder()
                    .title(tourData.getTour().getTitle())
                    .origin(tourData.getTour().getOrigin())
                    .destination(tourData.getTour().getDestination())
                    .description(tourData.getTour().getDescription())
                    .id(tourData.getTour().getId())
                    .build();
            addTour(tour);
            for (TourLog tourLog : tourData.getTourLogList()) {
                tourLogService.addTourLog(tourLog);
            }
        }
        return true;
    }

    @Override
    public boolean exportTourNLogs(String fileName, String location) {
        List<TourData> tourDataList = new ArrayList<>();
        List<Tour> allTours = tourService.findBySearchInput("");

        for (Tour tour : allTours) {
            List<TourLog> tourLogList = tourLogService.findByTourId(tour.getId());
            TourData tourPair = TourData.builder()
                    .tourLogList(tourLogList)
                    .tour(tour)
                    .build();
            tourDataList.add(tourPair);
        }

        fileAccess.exportTours(fileName, location, tourDataList);
        return true;
    }

    @Override
    public void createSummaryReport(String url, List<Tour> allTours) {

        TourStats stats = calculateMetrics(allTours);
        PDFBuilder.createSummaryPdf(url, allTours, stats);
    }

    private TourStats calculateMetrics(List<Tour> tourList) {
        TourStats stats = new TourStats();
        stats.setTotalTour(tourList.size());

        for (Tour tour : tourList) {
            List<TourLog> logs = searchLogsByTourId(tour.getId());
            stats.setTotalDistance(tour.getDistance() + stats.getTotalDistance());
            stats.setTotalTime(tour.getDuration() + stats.getTotalTime());

            for (TourLog log : logs) {

                stats.setTotalLogs(stats.getTotalLogs() + 1);

                switch (log.getSport()) {
                    case BIKE:
                        stats.setBikedTime(log.getDuration() + stats.getBikedTime());
                        stats.setBikedKm(log.getDistance() + stats.getBikedKm());
                        break;
                    case RUN:
                        stats.setRunTime(log.getDuration() + stats.getRunTime());
                        stats.setHikedKm(log.getDistance() + stats.getRunKm());
                        break;
                    case WALK:
                        stats.setWalkedTime(log.getDuration() + stats.getWalkedTime());
                        stats.setWalkedKm(log.getDistance() + stats.getWalkedKm());
                        break;
                    case HIKE:
                        stats.setHikedTime(log.getDuration() + stats.getHikedTime());
                        stats.setHikedKm(log.getDistance() + stats.getHikedKm());
                        break;
                    default:
                        break;
                }
            }

        }
        return stats;
    }

    @Override
    public void createTourReport(Tour currentTour, String url) {
        List<TourLog> tourLogList = new ArrayList<>(tourLogService.findByTourId(currentTour.getId()));
        PDFBuilder.createTourPdf(currentTour, url, tourLogList);
    }

}

