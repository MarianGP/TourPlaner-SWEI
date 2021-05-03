package org.garcia.layerdataaccess.persistance.repository;

import org.garcia.layerdataaccess.persistance.entity.ResourceEntity;

import java.sql.SQLException;
import java.util.List;

public interface IRepository {
    int modifyResources(String query, List<Object> parameters) throws SQLException;
    List<ResourceEntity> findByTerm(String query, List<Object> parameters, String resourceType) throws SQLException;
    ResourceEntity getById(String query, int id);
    int edit(String query, List<Object> parameters);
    int deleteById(String query, int id) throws SQLException;
}
