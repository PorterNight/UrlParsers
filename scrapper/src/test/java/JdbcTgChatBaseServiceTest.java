import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.tinkoff.edu.java.scrapper.jdbc.service.JdbcTgChatBaseService;

@Testcontainers
@SpringBootTest(classes = IntegrationEnvironment.IntegrationEnvironmentConfig.class)
@ExtendWith(SpringExtension.class)
public class JdbcTgChatBaseServiceTest extends IntegrationEnvironment {


    private final JdbcTgChatBaseService jdbcTgChatBaseService;

//    @Autowired
//    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTgChatBaseServiceTest(JdbcTgChatBaseService jdbcTgChatBaseService) {
        this.jdbcTgChatBaseService = jdbcTgChatBaseService;
    }


    @Test
    void addTest() {
        jdbcTgChatBaseService.add(2345235);
    }

    @Test
    void removeTest() {
        jdbcTgChatBaseService.remove(23452353);
    }

    @Test
    void findAllTest() {
        jdbcTgChatBaseService.add(234523555);
    }

}

