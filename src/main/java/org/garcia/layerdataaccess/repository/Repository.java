package org.garcia.layerdataaccess.repository;

import lombok.Getter;
import org.garcia.layerbusiness.mapper.TourLogMapper;
import org.garcia.layerbusiness.mapper.TourMapper;
import org.garcia.layerdataaccess.dbconnection.DBConnection;
import org.garcia.layerdataaccess.dbconnection.visitor.DBVisitor;
import org.garcia.layerdataaccess.entity.ResourceEntity;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Repository implements IRepository {

    private final DBConnection dbConnection;
    private String containerName;

    public Repository(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public int modifyResources(String query, List<Object> parameters) throws SQLException  {
        try (Connection conn = this.dbConnection.connect()) {
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
            } else {
                conn.commit();
                conn.close();
                return 0;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        throw new SQLException("Modifying resource in DB failed.");
    }

    @Override
    public List<ResourceEntity> findByTerm(String query, List<Object> parameters, String resourceType) throws SQLException {
        try (Connection conn = this.dbConnection.connect()) {
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
        throw new SQLException("Couldn't retrieve data. Is your container running?");
    }

    private List<ResourceEntity> tourEntityList(ResultSet rs, Connection conn) throws SQLException {
        List<ResourceEntity> list = new ArrayList<>();
        try {
            if (rs.next()) {
                do {
                    list.add(TourMapper.dbRowToTourEntity(rs)); // TODO: dependency?
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

    public void accept(DBVisitor visitor, String newContainerName) {
        this.containerName = newContainerName;
        visitor.visit(this);
    }


}
