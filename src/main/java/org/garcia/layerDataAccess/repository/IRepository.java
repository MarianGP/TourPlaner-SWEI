package org.garcia.layerDataAccess.repository;

import org.garcia.layerDataAccess.entity.ResourceEntity;

import java.util.List;

public interface IRepository {
    String getContainerName();
    int modifyResources(String query, List<Object> parameters);
    List<ResourceEntity> findByTerm(String query, List<Object> parameters, String resourceType);
}
