package org.garcia.layerdataaccess.persistance.repository;

import org.garcia.layerbusiness.mapper.TourLogMapper;
import org.garcia.layerbusiness.mapper.TourMapper;
import org.garcia.layerdataaccess.persistance.dbconnection.DBConnection;
import org.garcia.layerdataaccess.persistance.entity.ResourceEntity;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {

    //    @Inject
    private final DBConnection dbConnection;

    public Repository(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public int modifyResources(String query, List<Object> parameters) throws SQLException {
        try (Connection conn = dbConnection.connect()) {
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < parameters.size(); i++) {
                if (parameters.get(i) instanceof Number)
                    stmt.setInt(i + 1, (Integer) parameters.get(i));
                else if (parameters.get(i) instanceof String)
                    stmt.setString(i + 1, (String) parameters.get(i));
                else if (parameters.get(i) instanceof LocalDate)
                    stmt.setDate(i + 1, Date.valueOf((LocalDate) parameters.get(i)));
                else if (parameters.get(i) instanceof LocalTime)
                    stmt.setTime(i + 1, Time.valueOf((LocalTime) parameters.get(i)));
            }

            int total_rows = stmt.executeUpdate();

            if (total_rows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        conn.commit();
                        conn.close(); // TODO: ?
                        return generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new SQLException("Adding resource to DB failed.");
    }

    @Override
    public List<ResourceEntity> findByTerm(String query, List<Object> parameters, String resourceType) throws SQLException {
        try (Connection conn = dbConnection.connect()) {
            PreparedStatement stmt = conn.prepareStatement(query);

            for (int i = 0; i < parameters.size(); i++) {
                if (parameters.get(i) instanceof Number)
                    stmt.setInt(i + 1, (Integer) parameters.get(i));
                else if (parameters.get(i) instanceof String)
                    stmt.setString(i + 1, "%" + parameters.get(i) + "%");
            }

            ResultSet rs = stmt.executeQuery();

            if (resourceType.equals("tour"))
                return tourEntityList(rs, conn);
            else if ((resourceType.equals("tour_log"))) {
                return tourLogEntityList(rs, conn);
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new SQLException("Couldn't retrieve data");
    }

    private List<ResourceEntity> tourEntityList(ResultSet rs, Connection conn) throws SQLException {
        List<ResourceEntity> list = new ArrayList<>();
        try {
            if (rs.next()) {
                do {
                    list.add(TourMapper.dbRowToTourEntity(rs)); // TODO: ask dependency?
                } while (rs.next());
            }
            conn.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new SQLException("Couldn't create TourEntity");
    }

    private List<ResourceEntity> tourLogEntityList(ResultSet rs, Connection conn) throws SQLException {
        List<ResourceEntity> list = new ArrayList<>();
        try {
            if (rs.next()) {
                do {
                    list.add(TourLogMapper.dbRowToLogEntity(rs));
                } while (rs.next());
            }
            conn.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new SQLException("Couldn't create LogEntity");
    }

    @Override
    public ResourceEntity getById(String query, int id) {
        return null;
    }

    @Override
    public int edit(String query, List<Object> parameters) {
        return 0;
    }

    @Override
    public int deleteById(String query, int id) throws SQLException {
        return 0;
    }
}
