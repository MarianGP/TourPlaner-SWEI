package org.garcia.layerdataaccess.persistance.repository;

public class RepositoryFactory {
    private static IRepository repo;

    public static IRepository getInstance(IRepository repository) {
        if(repo == null)
            repo = repository;
        return repo;
    }
}
