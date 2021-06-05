package org.garcia.layerDataAccess.service;

import lombok.AllArgsConstructor;
import org.garcia.layerDataAccess.mapper.TourLogMapper;
import org.garcia.layerDataAccess.entity.ResourceEntity;
import org.garcia.layerDataAccess.repository.Repository;
import org.garcia.model.TourLog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class PostgresTourLogService implements ITourLogService {

    private final Repository repository;

    private static final String SQL_EDIT_TOUR_LOG =
            "UPDATE public.tour_log SET distance  = ?, duration = ?, rating = ?, " +
            "sport = ?, avg_speed = ?, special = ? WHERE log_id = ?;";
    private static final String SQL_ADD_TOUR_LOG =
            "INSERT INTO public.tour_log (date, distance, duration, rating, sport, avg_speed, " +
            "start, arrival, special, tour_id, user_id, report) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SQL_DELETE_LOG =
            "DELETE FROM public.tour_log WHERE log_id = ?;";
    private static final String SQL_FIND_BY_LOG_ID =
            "SELECT * FROM public.tour_log WHERE tour_id = ?;";

    public int addTourLog(TourLog tourLog) {

        List<Object> logParameters = new ArrayList<>();

        logParameters.add(tourLog.getDate());
        logParameters.add(tourLog.getDistance());
        logParameters.add(tourLog.getDuration());
        logParameters.add(tourLog.getRating());
        logParameters.add(tourLog.getSport().toString());
        logParameters.add(tourLog.getAvgSpeed());
        logParameters.add(tourLog.getStart());
        logParameters.add(tourLog.getEnd());
        logParameters.add(tourLog.getSpecial());
        logParameters.add(tourLog.getTourId());
        logParameters.add(tourLog.getUserId());
        logParameters.add(tourLog.getReport());

        try {
            return repository.modifyResources(SQL_ADD_TOUR_LOG, logParameters);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int editTourLog(TourLog tourLog) {

        List<Object> logParameters = new ArrayList<>();

        logParameters.add(tourLog.getDistance());
        logParameters.add(tourLog.getDuration());
        logParameters.add(tourLog.getRating());
        logParameters.add(tourLog.getSport().toString());
        logParameters.add(tourLog.getAvgSpeed());
        logParameters.add(tourLog.getSpecial());
        logParameters.add(tourLog.getId());

        try {
            return repository.modifyResources(SQL_EDIT_TOUR_LOG, logParameters);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int deleteById(int id) {

        List<Object> parameters = new ArrayList<>();
        parameters.add(id);
        try {
            return repository.modifyResources(SQL_DELETE_LOG, parameters);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<TourLog> findByTourId(int tourId) {
        List<ResourceEntity> tourLogs;

        List<Object> logParameters = new ArrayList<>();
        logParameters.add(tourId);

        try {
            tourLogs = repository.findByTerm(SQL_FIND_BY_LOG_ID, logParameters, "tour_log");
            return TourLogMapper.getMappedTourLogs(tourLogs);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
