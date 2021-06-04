package org.garcia.layerBusiness.appmanager;

import org.garcia.layerBusiness.util.InputValidator;
import org.garcia.layerDataAccess.common.IDALFactory;
import org.garcia.layerDataAccess.fileaccess.FileAccess;
import org.garcia.layerDataAccess.fileaccess.PDFBuilder;
import org.garcia.layerDataAccess.mapAPI.ApiCollection.Response;
import org.garcia.layerDataAccess.mapAPI.MapAPIConnection;
import org.garcia.layerDataAccess.service.ITourLogService;
import org.garcia.layerDataAccess.service.ITourService;
import org.garcia.model.Tour;
import org.garcia.model.TourData;
import org.garcia.model.TourLog;
import org.garcia.model.TourStats;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsable for the interaction between GUI and data access layer.
 */
public class AppManagerDB implements IAppManager {

    private static final String CONTAINER_NAME = "postgresDB1";
    private static final String API_NAME = "mapQuestApi";
    private final ITourService tourService;
    private final ITourLogService tourLogService;
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
    }

    /**
     * @param inputSearch String is the term used for filtering tours. if empty the whole db tour list in returned
     * @return a tour list of term filtered tours
     */
    @Override
    public List<Tour> searchTours(String inputSearch) {
        if (inputSearch.equals(""))
            return tourService.findAll();
        if (InputValidator.validString(inputSearch)) {
            return tourService.findBySearchInput(inputSearch);
        }
        return null;
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
     * @throws IOException wrong parameters type
     */
    @Override
    public int addTour(Tour newTour) throws IOException {
        if (newTour != null) {
            Response resp = mapAPI.getRoute(newTour);
            String imgPath = mapAPI.getMap(resp);
            newTour.setImg(imgPath);
            newTour.setDistance((int) resp.getRoute().getDistance());
            newTour.setDuration(resp.getRoute().getTime());
            return tourService.addTour(newTour);
        }
        return 0;
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
    public boolean deleteTour(Tour tour) {
        if (tourService.deleteTour(tour.getId()) != 0) {
            fileAccess.deleteFile("img", tour.getImg());
            return true;
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
    public boolean validTour(Tour tour) {
        return (InputValidator.validString(tour.getDescription()) &&
                InputValidator.validString(tour.getTitle()) &&
                InputValidator.validString(tour.getDestination()) &&
                InputValidator.validString(tour.getOrigin())
        );
    }

    @Override
    public boolean importTourNLogs(String fileName, String location) {
        try {
            List<TourData> tourDataList = fileAccess.getTourDataFromFile(fileName, location);
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
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
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

        try {
            fileAccess.exportTours(fileName, location, tourDataList);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void createSummaryReport(String url, List<Tour> allTours) throws IOException {

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
    public void createTourReport(Tour currentTour, String url) throws IOException {
        List<TourLog> tourLogList = new ArrayList<>(tourLogService.findByTourId(currentTour.getId()));
        PDFBuilder.createTourPdf(currentTour, url, tourLogList);
    }

    @Override
    public int editTour(Tour tour) {
        if (validTour(tour))
            return tourService.editTour(tour);
        return 0;
    }

}
