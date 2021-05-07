package org.garcia.layerdataaccess.repository;

import org.garcia.layerdataaccess.entity.ResourceEntity;

import java.sql.SQLException;
import java.util.List;

public interface IRepository {
    String getContainerName();
    int modifyResources(String query, List<Object> parameters) throws SQLException;
    List<ResourceEntity> findByTerm(String query, List<Object> parameters, String resourceType) throws SQLException;
}
