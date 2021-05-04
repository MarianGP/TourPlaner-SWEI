package org.garcia.layerdataaccess.repository;

import org.garcia.layerdataaccess.common.dbconnection.PostgresDBConnection;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

class RepositoryTest {

    @Mock
    Repository repository = new Repository(new PostgresDBConnection());

    RepositoryTest() throws IOException {
    }

    @Test
    void testInsertTour() throws SQLException {
        String query = "INSERT INTO public.tour (tour_id, title, description, origin, destination, user_id)\n" +
                "VALUES (?,?,?,?,?,?);";
        List<Object> tourParameters = new LinkedList<>();
        tourParameters.add(40);
        tourParameters.add("title");
        tourParameters.add("description");
        tourParameters.add("origin");
        tourParameters.add("destination");
        tourParameters.add(1);
        System.out.println(repository.modifyResources(query, tourParameters));
    }

    @Test
    void testInsertLog() {
    }

    @Test
    void findByTerm() {
    }

    @Test
    void deleteById() {
    }
}
