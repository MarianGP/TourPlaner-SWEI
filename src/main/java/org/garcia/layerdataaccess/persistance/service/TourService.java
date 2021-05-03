package org.garcia.layerdataaccess.persistance.service;

import org.garcia.layerbusiness.mapper.TourMapper;
import org.garcia.layerdataaccess.persistance.entity.ResourceEntity;
import org.garcia.layerdataaccess.persistance.entity.TourEntity;
import org.garcia.layerdataaccess.persistance.repository.Repository;
import org.garcia.model.Tour;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TourService implements ITourService {

    private final Repository repository;

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
        String query = "select * from public.tour where title LIKE ? or description LIKE ? or destination LIKE ? or origin LIKE ?;";
        List<Object> tourParameters = new ArrayList<>();
        tourParameters.add(searchInput);
        tourParameters.add(searchInput);
        tourParameters.add(searchInput);
        tourParameters.add(searchInput);

        List<ResourceEntity> list;
        List<Tour> mappedTours = new ArrayList<>();
        list = repository.findByTerm(query, tourParameters, "tour");

        if(list != null) {
            for(ResourceEntity tour: list) {
                mappedTours.add(TourMapper.tourEntityToTour(tour));
            }
        }
        return mappedTours;
    }

    public List<Tour> findAll() throws SQLException {
        String query = "select * from public.tour;";
        List<Tour> mappedTours = new ArrayList<>();
        List<Object> tourParameters = new ArrayList<>();
        List<ResourceEntity> list = repository.findByTerm(query, tourParameters, "tour");

        if(list != null) {
            for(ResourceEntity tour: list) {
                mappedTours.add(TourMapper.tourEntityToTour(tour));
            }
        }
        return mappedTours;
    }

    @Override
    public int addTour(Tour tour) throws SQLException {
        String query = "INSERT INTO public.tour (title, description, origin, destination, user_id)\n" +
                "VALUES (?,?,?,?,?);";
        List<Object> tourParameters = new ArrayList<>();
        tourParameters.add(tour.getTitle());
        tourParameters.add(tour.getDescription());
        tourParameters.add(tour.getOrigin());
        tourParameters.add(tour.getDestination());
        tourParameters.add(1);

        return repository.modifyResources(query, tourParameters);
    }

    @Override
    public int deleteTour(int id) throws SQLException {
        String query = "DELETE FROM public.tour WHERE tour_id = ?;";
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);
        return repository.modifyResources(query, parameters);
    }


}
