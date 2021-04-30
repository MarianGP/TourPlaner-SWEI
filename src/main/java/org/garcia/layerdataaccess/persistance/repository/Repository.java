package org.garcia.layerdataaccess.persistance.repository;

import org.garcia.layerdataaccess.persistance.entity.ResourceEntity;

import java.util.List;

public class Repository implements IRepository {
    @Override
    public int insert(String query, List<Object> parameters) {
        return 0;
    }

    @Override
    public List<ResourceEntity> selectByInput(String query, String input) {
        return null;
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
    public int deleteById(String query, int id) {
        return 0;
    }
}
