package org.garcia.layerDataAccess.service;

import org.garcia.layerDataAccess.entity.ResourceEntity;
import org.garcia.layerDataAccess.entity.TourEntity;
import org.garcia.layerDataAccess.mapper.TourMapper;
import org.garcia.layerDataAccess.repository.Repository;
import org.garcia.model.Tour;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PostgresTourService implements ITourService {

    private static final String SQL_FIND_TOUR_IMAGE_URL =
            "SELECT * FROM public.tour WHERE tour_id=?;";
    private static final String SQL_FIND_TOUR_BY_TERM =
            "SELECT * FROM public.tour WHERE title LIKE ? or description LIKE ? or destination LIKE ? or origin LIKE ?;";
    private static final String SQL_FIND_TOUR_BY_TERM_FULL =
            "SELECT DISTINCT t.* FROM public.tour t LEFT JOIN public.tour_log tl USING (tour_id) " +
            "WHERE t.title LIKE ? or t.description LIKE ? or " +
            "t.destination LIKE ? or t.origin LIKE ? or " +
            "tl.sport LIKE ? or tl.report LIKE ?;";
    private static final String SQL_FIND_ALL_TOURS =
            "SELECT * FROM public.tour;";
    private static final String SQL_ADD_NEW_TOUR =
            "INSERT INTO public.tour (title, description, origin, destination, user_id, distance, duration, img)\n" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SQL_ADD_NEW_TOUR_WITH_ID =
            "INSERT INTO public.tour (tour_id, title, description, origin, destination, user_id, distance, duration, img)\n" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SQL_DELETE_TOUR =
            "DELETE FROM public.tour WHERE tour_id = ?;";
    private static final String SQL_EDIT_TOUR =
            "UPDATE public.tour SET title = ?, description = ? WHERE tour_id = ?;";

    private final Repository repository;

    public PostgresTourService(Repository repo) {
        repository = repo;
    }

    @Override
    public int editTour(Tour tour) {
        List<Object> tourParameters = new ArrayList<>();
        tourParameters.add(tour.getTitle());
        tourParameters.add(tour.getDescription());
        tourParameters.add(tour.getId());

        return repository.modifyResources(SQL_EDIT_TOUR, tourParameters);
    }

    @Override
    public List<Tour> findBySearchInput(String searchInput) {
        List<Object> tourParameters = new ArrayList<>();

        List<ResourceEntity> list;
        List<Tour> mappedTours = new ArrayList<>();

        tourParameters.add(searchInput); // title
        tourParameters.add(searchInput); // description
        tourParameters.add(searchInput); // destination
        tourParameters.add(searchInput); // origin
        tourParameters.add(searchInput.toUpperCase(Locale.ROOT)); // sport
        tourParameters.add(searchInput); // report

        list = repository.findByTerm(SQL_FIND_TOUR_BY_TERM_FULL, tourParameters, "tour");
        if (list != null) {
            for (ResourceEntity tour : list) {
                mappedTours.add(TourMapper.tourEntityToTour(tour));
            }
        }

        return mappedTours;
    }

    @Override
    public List<Tour> findAll() {
        List<Tour> mappedTours = new ArrayList<>();
        List<Object> tourParameters = new ArrayList<>();
        List<ResourceEntity> list = repository.findByTerm(SQL_FIND_ALL_TOURS, tourParameters, "tour");
        if (list != null) {
            for (ResourceEntity tour : list) {
                mappedTours.add(TourMapper.tourEntityToTour(tour));
            }
        }
        return mappedTours;
    }

    @Override
    public int addTour(Tour tour) {
        List<Object> tourParameters = new ArrayList<>();
        String query;
        int userId = 1;

        if (tour.getId() == 0) {
            query = SQL_ADD_NEW_TOUR;
        } else {
            tourParameters.add(tour.getId());
            query = SQL_ADD_NEW_TOUR_WITH_ID;
        }

        tourParameters.add(tour.getTitle());
        tourParameters.add(tour.getDescription());
        tourParameters.add(tour.getOrigin());
        tourParameters.add(tour.getDestination());
        tourParameters.add(userId);
        tourParameters.add(tour.getDistance());
        tourParameters.add(tour.getDuration());
        tourParameters.add(tour.getImg());

        return repository.modifyResources(query, tourParameters);
    }

    @Override
    public int deleteTour(int id) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);
        return repository.modifyResources(SQL_DELETE_TOUR, parameters);
    }


    public String getImageUrl(int id) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);
        List<ResourceEntity> tours = repository.findByTerm(SQL_FIND_TOUR_IMAGE_URL, parameters, "tour");
        return ((TourEntity) tours.get(0)).getImg();
    }

    public List<Tour> findBySearchInput2(String searchInput) {
        List<Object> tourParameters = new ArrayList<>();
        tourParameters.add(searchInput); // title
        tourParameters.add(searchInput); // description
        tourParameters.add(searchInput); // destination
        tourParameters.add(searchInput); // origin

        List<ResourceEntity> list;
        List<Tour> mappedTours = new ArrayList<>();

        list = repository.findByTerm(SQL_FIND_TOUR_BY_TERM, tourParameters, "tour");
        if (list != null) {
            for (ResourceEntity tour : list) {
                mappedTours.add(TourMapper.tourEntityToTour(tour));
            }
        }

        return mappedTours;
    }

}
