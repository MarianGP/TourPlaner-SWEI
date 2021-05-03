package org.garcia.layerdataaccess.persistance.service;

import lombok.AllArgsConstructor;
import org.garcia.layerbusiness.mapper.TourLogMapper;
import org.garcia.layerdataaccess.persistance.entity.ResourceEntity;
import org.garcia.layerdataaccess.persistance.repository.Repository;
import org.garcia.model.TourLog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class TourLogService implements IService {
    private final Repository repository;

    public int addTourLog(TourLog tourLog) throws SQLException {
        String query =
                "INSERT INTO public.tour_log (date, distance, duration, rating, sport, avg_speed, start, \n" +
                "arrival, special, tour_id, user_id) \n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        List<Object> logParameters = new ArrayList<>();

        logParameters.add(tourLog.getDate());
        logParameters.add(tourLog.getDistance());
        logParameters.add(tourLog.getDuration());
        logParameters.add(tourLog.getRating());
        logParameters.add(tourLog.getSport().toString());
        logParameters.add(tourLog.getAvg_speed());
        logParameters.add(tourLog.getStart());
        logParameters.add(tourLog.getEnd());
        logParameters.add(tourLog.getSpecial());
        logParameters.add(tourLog.getTourId());
        logParameters.add(tourLog.getUserId());

        return repository.modifyResources(query, logParameters);
    }

    public int deleteById(int id) throws SQLException {
        String query = "DELETE FROM public.tour_log WHERE log_id = ?;";
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);
        return repository.modifyResources(query, parameters);
    }

    public int deleteByTourId(int id) throws SQLException {
        String query = "DELETE FROM public.tour_log WHERE tour_id = ?;";
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);
        return repository.modifyResources(query, parameters);
    }

    public List<TourLog> findByTourId(int tourId) throws SQLException {
        List<ResourceEntity> tourLogs = new ArrayList<>();
        String query = "SELECT * FROM public.tour_log WHERE tour_id = ?;";
        List<Object> logParameters = new ArrayList<>();
        logParameters.add(tourId);
        tourLogs = repository.findByTerm(query, logParameters, "tour_log");

        if(tourLogs != null) {
            return TourLogMapper.getMappedTourLogs(tourLogs);
        }
        return null;
    }
}
