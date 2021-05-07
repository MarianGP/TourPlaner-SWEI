package org.garcia.layerdataaccess.service;

import org.garcia.layerbusiness.mapper.TourMapper;
import org.garcia.layerdataaccess.entity.ResourceEntity;
import org.garcia.layerdataaccess.entity.TourEntity;
import org.garcia.layerdataaccess.repository.Repository;
import org.garcia.model.Tour;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TourService implements ITourService {

    private final Repository repository;

    private final static String SQL_FIND_TOUR_IMAGE_URL = "SELECT * FROM public.tour WHERE tour_id=?;";
    private final static String SQL_FIND_TOUR_BY_TERM = "SELECT * FROM public.tour WHERE title LIKE ? or description LIKE ? or destination LIKE ? or origin LIKE ?;";
    private final static String SQL_FIND_ALL_TOURS = "SELECT * FROM public.tour;";
    private final static String SQL_ADD_NEW_TOUR = "INSERT INTO public.tour (title, description, origin, destination, user_id, distance, duration, img)\n" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
    private final static String SQL_ADD_NEW_TOUR_WITH_ID = "INSERT INTO public.tour (tour_id, title, description, origin, destination, user_id, distance, duration, img)\n" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private final static String SQL_DELETE_TOUR = "DELETE FROM public.tour WHERE tour_id = ?;";

    public TourService(Repository repo) {
        repository = repo;
    }

    @Override
    public TourEntity findById(Integer id) {
        return null;
    }

    @Override
    public List<TourEntity> findByOrigin(String origin) {
        return null;
    }

    @Override
    public List<TourEntity> findByDestination(String destination) {
        return null;
    }

    @Override
    public int duplicateTour(Tour tour) {
        return 0;
    }

    @Override
    public int editTour(Tour tour) {
        return 0;
    }

    @Override
    public List<Tour> findBySearchInput(String searchInput) throws SQLException {
        List<Object> tourParameters = new ArrayList<>();
        tourParameters.add(searchInput);
        tourParameters.add(searchInput);
        tourParameters.add(searchInput);
        tourParameters.add(searchInput);

        List<ResourceEntity> list;
        List<Tour> mappedTours = new ArrayList<>();
        list = repository.findByTerm(SQL_FIND_TOUR_BY_TERM, tourParameters, "tour");

        if(list != null) {
            for(ResourceEntity tour: list) {
                mappedTours.add(TourMapper.tourEntityToTour(tour));
            }
        }
        return mappedTours;
    }

    public List<Tour> findAll() throws SQLException {
        List<Tour> mappedTours = new ArrayList<>();
        List<Object> tourParameters = new ArrayList<>();
        List<ResourceEntity> list = repository.findByTerm(SQL_FIND_ALL_TOURS, tourParameters, "tour");

        if(list != null) {
            for(ResourceEntity tour: list) {
                mappedTours.add(TourMapper.tourEntityToTour(tour));
            }
        }
        return mappedTours;
    }

    public String getImageUrl(int id) throws SQLException {
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);
        List<ResourceEntity> tours = repository.findByTerm(SQL_FIND_TOUR_IMAGE_URL, parameters, "tour");
        return ((TourEntity) tours.get(0)).getImg();
    }

    @Override
    public int addTour(Tour tour) throws SQLException {
        List<Object> tourParameters = new ArrayList<>();
        String query;

        if(tour.getId() == 0) {
            query = SQL_ADD_NEW_TOUR ;
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

        return repository.modifyResources(query, tourParameters);
    }

    @Override
    public int deleteTour(int id) throws SQLException {
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);
        return repository.modifyResources(SQL_DELETE_TOUR, parameters);
    }


}
