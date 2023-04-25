import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

import ru.tinkoff.edu.java.scrapper.configuration.JdbcAccessConfiguration;
import ru.tinkoff.edu.java.scrapper.domain.TgChatBaseService;
import ru.tinkoff.edu.java.scrapper.domain.repository.TgChatRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest(classes = {IntegrationEnvironment.IntegrationEnvironmentConfig.class, JdbcAccessConfiguration.class})
@ExtendWith(SpringExtension.class)
public class JdbcTgChatBaseServiceTest extends IntegrationEnvironment {

    @Autowired
    private TgChatBaseService tgChatBaseService;

    @Autowired
    private JdbcTemplate jdbcTemplate;



    @BeforeEach
    void setUp() {
        // erase all data from db
        jdbcTemplate.update("DELETE FROM chat");
        jdbcTemplate.update("DELETE FROM link_chat");
    }

    @Test
    void addTest() {
        // Add a new chat

        long testChatId = 2345235;
        tgChatBaseService.add(testChatId);

        String sql = "SELECT chat_id FROM chat WHERE chat_id = ?";
        Long chatId = jdbcTemplate.queryForObject(sql, Long.class, testChatId);

        assertEquals(testChatId, chatId);
    }

    @Test
    void removeTest() {

        // add a chat to be removed
        long testChatId = 2233;
        tgChatBaseService.add(testChatId);

        // remove the chat
        tgChatBaseService.remove(testChatId);

        String sql = "SELECT chat_id FROM chat WHERE chat_id = ?";
        Exception e = assertThrows(EmptyResultDataAccessException.class, () -> {
            jdbcTemplate.queryForObject(sql, Long.class, testChatId);
        });

        String expectedMessage = "Incorrect result size: expected 1, actual 0";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void findAllTest() {
        // add chat records
        long[] testChatIds = {1001, 1002, 1003};
        for (long chatId : testChatIds) {
            tgChatBaseService.add(chatId);
        }

        // get all chats
        TgChatRepository allChats = tgChatBaseService.findAll();

        assertEquals(testChatIds.length, allChats.length());

        for (int i = 0; i < testChatIds.length; i++) {
            assertEquals(testChatIds[i], allChats.tgChatId()[i]);
        }
    }

}

