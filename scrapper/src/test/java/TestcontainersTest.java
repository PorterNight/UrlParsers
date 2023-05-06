import configuration.IntegrationEnvironment;
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

            // write 3 rows
            statement.execute("CREATE TABLE IF NOT EXISTS test (url TEXT)");
            statement.executeUpdate("INSERT INTO test (url) VALUES ('https://github.com')");
            statement.executeUpdate("INSERT INTO test (url) VALUES ('https://youtube.com')");
            statement.executeUpdate("INSERT INTO test (url) VALUES ('https://habr.com')");
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(url) FROM test");

            resultSet.next();
            int count = resultSet.getInt(1);

            assertEquals(3, count);
        }
    }
}