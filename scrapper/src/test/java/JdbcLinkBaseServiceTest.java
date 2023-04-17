import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.service.JdbcLinkService;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.service.JdbcTgChatBaseService;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;

@Testcontainers
@SpringBootTest(classes = IntegrationEnvironment.IntegrationEnvironmentConfig.class)
@ExtendWith(SpringExtension.class)
public class JdbcLinkBaseServiceTest extends IntegrationEnvironment {

    private final JdbcLinkService jdbcLinkService;
    private final JdbcTgChatBaseService jdbcTgChatBaseService;

    @Autowired
    public JdbcLinkBaseServiceTest(JdbcLinkService jdbcLinkService, JdbcTgChatBaseService jdbcTgChatBaseService) {
        this.jdbcLinkService = jdbcLinkService;
        this.jdbcTgChatBaseService = jdbcTgChatBaseService;
    }

    @Test
    @Transactional
    @Rollback
    void addTest() throws URISyntaxException {
        jdbcTgChatBaseService.add(3424234);
        jdbcLinkService.add(3424234, new URI("https://overclockers.ru/"));
    }

    @Test
    @Transactional
    @Rollback
    void removeWrongUrlTest() {

        Exception e = assertThrows(RuntimeException.class, () -> {
            jdbcLinkService.remove(3424234, new URI("https://overclockers"));
        });

        String expectedMessage = "Url отсутсвует для удаления: https://overclockers";
        String actualMessage = e.getMessage();

//        System.out.println(expectedMessage + ":" + actualMessage);

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    @Transactional
    @Rollback
    void removeTest() throws URISyntaxException {
        jdbcTgChatBaseService.add(2233);
        jdbcLinkService.add(2233, new URI("https://overclockers.ru/"));
        jdbcLinkService.remove(2233, new URI("https://overclockers.ru/"));
    }

    @Test
    @Transactional
    @Rollback
    void findAllTest() {
        jdbcLinkService.listAll(3424234);
    }

}

