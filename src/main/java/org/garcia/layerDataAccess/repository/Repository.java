package org.garcia.layerDataAccess.repository;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.garcia.layerDataAccess.dbconnection.DBConnection;
import org.garcia.layerDataAccess.dbconnection.visitor.DBVisitor;
import org.garcia.layerDataAccess.entity.ResourceEntity;
import org.garcia.layerDataAccess.mapper.TourDirectionsMapper;
import org.garcia.layerDataAccess.mapper.TourLogMapper;
import org.garcia.layerDataAccess.mapper.TourMapper;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Repository implements IRepository {

    private static final Logger logger = LogManager.getLogger(Repository.class);
    private final DBConnection dbConnection;
    private String containerName;

    public Repository(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public int modifyResources(String query, List<Object> parameters) {
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
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Modifying resource in DB failed.");
            return 0;
        }
    }

    @Override
    public List<ResourceEntity> findByTerm(String query, List<Object> parameters, String resourceType) {
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
            else if (resourceType.equals("tour_log")) {
                return tourLogEntityList(rs, conn);
            } else if (resourceType.equals("direction")) {
                return tourDirectionEntityList(rs, conn);
            } else {
                return null;
            }

        } catch (SQLException e) {
            logger.error("Couldn't retrieve data. DID YOU START THE CONTAINER? ***");
            e.printStackTrace();
            return null;
        }
    }

    private List<ResourceEntity> tourDirectionEntityList(ResultSet rs, Connection conn) {
        List<ResourceEntity> list = new ArrayList<>();
        try {
            if (rs.next()) {
                do {
                    list.add(TourDirectionsMapper.dbRowToTourDirectionEntity(rs));
                } while (rs.next());
            }
            conn.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Couldn't create TourEntity");
            return null;
        }
    }

    private List<ResourceEntity> tourEntityList(ResultSet rs, Connection conn) {
        List<ResourceEntity> list = new ArrayList<>();
        try {
            if (rs.next()) {
                do {
                    list.add(TourMapper.dbRowToTourEntity(rs));
                } while (rs.next());
            }
            conn.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Couldn't create TourEntity");
            return null;
        }
    }

    private List<ResourceEntity> tourLogEntityList(ResultSet rs, Connection conn) {
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
            logger.error("Couldn't create LogEntity");
            return null;
        }
    }

    public void accept(DBVisitor visitor, String newContainerName) {
        this.containerName = newContainerName;
        visitor.visit(this);
    }


}
