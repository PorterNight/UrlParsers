import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest(classes = IntegrationEnvironment.IntegrationEnvironmentConfig.class)
@ExtendWith(SpringExtension.class)
public class TestcontainersTest2 extends IntegrationEnvironment {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void simpleTest() {
        // write 3 rows
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS test (url TEXT)");
        jdbcTemplate.update("INSERT INTO test (url) VALUES (?)", "https://github.com");
        jdbcTemplate.update("INSERT INTO test (url) VALUES (?)", "https://youtube.com");
        jdbcTemplate.update("INSERT INTO test (url) VALUES (?)", "https://habr.com");

        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(url) FROM test", Integer.class);
        assertEquals(3, count);
    }
}
