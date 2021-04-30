package org.garcia.layerdataaccess.persistance.repository;

import org.garcia.layerdataaccess.persistance.entity.ResourceEntity;

import java.util.List;

public interface IRepository {
    int insert(String query, List<Object> parameters);
    List<ResourceEntity> selectByInput(String query, String input);
    ResourceEntity getById(String query, int id);
    int edit(String query, List<Object> parameters);
    int deleteById(String query, int id);
}
