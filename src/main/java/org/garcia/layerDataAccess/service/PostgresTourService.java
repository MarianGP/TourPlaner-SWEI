package org.garcia.layerDataAccess.service;

import org.garcia.layerBusiness.mapper.TourMapper;
import org.garcia.layerDataAccess.entity.ResourceEntity;
import org.garcia.layerDataAccess.entity.TourEntity;
import org.garcia.layerDataAccess.repository.Repository;
import org.garcia.model.Tour;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostgresTourService implements ITourService {

    private static final String SQL_FIND_TOUR_IMAGE_URL = "SELECT * FROM public.tour WHERE tour_id=?;";
    private static final String SQL_FIND_TOUR_BY_TERM = "SELECT * FROM public.tour WHERE title LIKE ? or description LIKE ? or destination LIKE ? or origin LIKE ?;";
    private static final String SQL_FIND_ALL_TOURS = "SELECT * FROM public.tour;";
    private static final String SQL_ADD_NEW_TOUR = "INSERT INTO public.tour (title, description, origin, destination, user_id, distance, duration, img)\n" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SQL_ADD_NEW_TOUR_WITH_ID = "INSERT INTO public.tour (tour_id, title, description, origin, destination, user_id, distance, duration, img)\n" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SQL_DELETE_TOUR = "DELETE FROM public.tour WHERE tour_id = ?;";
    private final Repository repository;

    public PostgresTourService(Repository repo) {
        repository = repo;
    }

    @Override
    public int editTour(Tour tour) {
        return 0;
    }

    @Override
    public List<Tour> findBySearchInput(String searchInput) {
        List<Object> tourParameters = new ArrayList<>();
        tourParameters.add(searchInput);
        tourParameters.add(searchInput);
        tourParameters.add(searchInput);
        tourParameters.add(searchInput);

        List<ResourceEntity> list;
        List<Tour> mappedTours = new ArrayList<>();

        try {
            list = repository.findByTerm(SQL_FIND_TOUR_BY_TERM, tourParameters, "tour");
            if (list != null) {
                for (ResourceEntity tour : list) {
                    mappedTours.add(TourMapper.tourEntityToTour(tour));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mappedTours;
    }

    public List<Tour> findAll() {
        List<Tour> mappedTours = new ArrayList<>();
        List<Object> tourParameters = new ArrayList<>();
        try {
            List<ResourceEntity> list = repository.findByTerm(SQL_FIND_ALL_TOURS, tourParameters, "tour");
            if (list != null) {
                for (ResourceEntity tour : list) {
                    mappedTours.add(TourMapper.tourEntityToTour(tour));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mappedTours;
    }

    public String getImageUrl(int id) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);
        try {
            List<ResourceEntity> tours = repository.findByTerm(SQL_FIND_TOUR_IMAGE_URL, parameters, "tour");
            return ((TourEntity) tours.get(0)).getImg();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";

    }

    @Override
    public int addTour(Tour tour) {
        List<Object> tourParameters = new ArrayList<>();
        String query;

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
        tourParameters.add(1);
        tourParameters.add(tour.getDistance());
        tourParameters.add(tour.getDuration());
        tourParameters.add(tour.getImg());

        try {
            return repository.modifyResources(query, tourParameters);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteTour(int id) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);
        try {
            return repository.modifyResources(SQL_DELETE_TOUR, parameters);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
