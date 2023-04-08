import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
class TestcontainersTest extends IntegrationEnvironment {

    @Test
    void simpleTest() throws Exception {
        try (Connection connection = DriverManager.getConnection(
                POSTGRESQL_CONTAINER.getJdbcUrl(),
                POSTGRESQL_CONTAINER.getUsername(),
                POSTGRESQL_CONTAINER.getPassword());
             Statement statement = connection.createStatement()) {

            // simple access to db
            ResultSet resultSet = statement.executeQuery("SELECT 1");
            resultSet.next();
            int result = resultSet.getInt(1);

            assertEquals(1, result);
        }
    }
}