import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.tinkoff.edu.java.scrapper.configuration.JdbcAccessConfiguration;
import ru.tinkoff.edu.java.scrapper.domain.LinkBaseService;
import ru.tinkoff.edu.java.scrapper.domain.TgChatBaseService;
import ru.tinkoff.edu.java.scrapper.domain.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.ListLinkRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(classes = {IntegrationEnvironment.IntegrationEnvironmentConfig.class, JdbcAccessConfiguration.class})
@ExtendWith(SpringExtension.class)
public class JdbcLinkBaseServiceTest extends IntegrationEnvironment {

    @Autowired
    private LinkBaseService linkBaseService;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @BeforeEach
    void setUp() {
        // erase all data from db
        jdbcTemplate.update("DELETE FROM link");
        jdbcTemplate.update("DELETE FROM link_chat");
    }

    @Test
    @Transactional
    @Rollback
    void addTest() throws URISyntaxException {

        long testChatId = 2345;
        URI testUrl = new URI("https://overclockers.ru/");

        // register chat
        jdbcTemplate.update("INSERT INTO chat(chat_id) VALUES (?)", testChatId);

        // add link
        linkBaseService.add(testChatId, testUrl);

        String sql = "SELECT url FROM link WHERE url = ?";
        String actualUrl = jdbcTemplate.queryForObject(sql, String.class, testUrl.toString());

        assertEquals(testUrl.toString(), actualUrl);
    }


    @Test
    @Transactional
    @Rollback
    void removeTest() throws URISyntaxException {

        // Add a chat and link to be removed
        long testChatId = 1234;
        jdbcTemplate.update("INSERT INTO chat(chat_id) VALUES (?)", testChatId);
        URI testUrl = new URI("https://overclockers.ru/");
        linkBaseService.add(testChatId, testUrl);

        // remove the link
        linkBaseService.remove(testChatId, testUrl);

        String sql = "SELECT url FROM link WHERE url = ?";
        Exception e = assertThrows(EmptyResultDataAccessException.class, () -> {
            jdbcTemplate.queryForObject(sql, String.class, testUrl.toString());
        });

        String expectedMessage = "Incorrect result size: expected 1, actual 0";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));    }


    @Test
    @Transactional
    @Rollback
    void findAllTest() throws URISyntaxException {

        // add chat record
        long testChatId = 1234;
        jdbcTemplate.update("INSERT INTO chat(chat_id) VALUES (?)", testChatId);

        // add link records
        URI[] testUrls = {
                new URI("https://overclockers.ru/"),
                new URI("https://www.youtube.com/"),
                new URI("https://forum.ixbt.com/")
        };
        Arrays.stream(testUrls).forEach(url -> linkBaseService.add(testChatId, url));

        // get all links for the chat
        ListLinkRepository allLinks = linkBaseService.findAll(testChatId);

        assertEquals(testUrls.length, allLinks.size());

        List<URI> actualUrls = Arrays.asList(allLinks.links()).stream()
                .map(LinkRepository::url)
                .toList();

        assertEquals(Arrays.asList(testUrls), actualUrls);
    }

}

