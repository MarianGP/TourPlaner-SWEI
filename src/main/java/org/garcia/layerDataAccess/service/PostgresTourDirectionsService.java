package org.garcia.layerDataAccess.service;

import lombok.AllArgsConstructor;
import org.garcia.layerDataAccess.entity.ResourceEntity;
import org.garcia.layerDataAccess.entity.TourDirectionEntity;
import org.garcia.layerDataAccess.mapper.TourDirectionsMapper;
import org.garcia.layerDataAccess.repository.Repository;
import org.garcia.model.TourDirection;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class PostgresTourDirectionsService implements ITourDirectionsService {

    private final Repository repository;

    private static final String SQL_FIND_DIRECCIONS =
            "SELECT * FROM public.tour_directions WHERE tour_id = ?;";

    private static final String SQL_ADD_DIRECCION =
            "INSERT INTO public.tour_directions (tour_id, step, icon_url)\n" +
                    "VALUES (?, ?, ?);";

    private static final String SQL_DELETE_DIRECCION =
            "DELETE FROM public.tour_directions WHERE tour_id = ?;";

    @Override
    public List<TourDirection> findByTourId(int tourId) {
        List<Object> tourParameters = new ArrayList<>();
        List<TourDirection> tourDirectionList = new ArrayList<>();
        List<ResourceEntity> list;
        tourParameters.add(tourId);

        list = repository.findByTerm(SQL_FIND_DIRECCIONS, tourParameters, "direction"); //edit query
        if (list != null) {
            for (ResourceEntity direction : list) {
                tourDirectionList.add(TourDirectionsMapper.entityToTourDirections((TourDirectionEntity) direction));
            }
        }

        return tourDirectionList;
    }

    @Override
    public int addTourDirection(int tourId, String step, String iconUrl) {
        List<Object> tourParameters = new ArrayList<>();
        tourParameters.add(tourId);
        tourParameters.add(step);
        tourParameters.add(iconUrl);
        return repository.modifyResources(SQL_ADD_DIRECCION, tourParameters);
    }

    @Override
    public int deleteByTourId(int tourId) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(tourId);
        return repository.modifyResources(SQL_DELETE_DIRECCION, parameters);
    }

}
