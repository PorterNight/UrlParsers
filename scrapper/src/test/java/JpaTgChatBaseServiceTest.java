import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.configuration.JpaAccessConfiguration;
import ru.tinkoff.edu.java.scrapper.domain.TgChatBaseService;
import ru.tinkoff.edu.java.scrapper.domain.jpa.repository.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.repository.JpaLinkChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.repository.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.service.JpaTgChatBaseService;
import ru.tinkoff.edu.java.scrapper.domain.repository.TgChatRepository;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {IntegrationEnvironment.IntegrationEnvironmentConfig.class, JpaAccessConfiguration.class, JpaChatRepository.class, JpaLinkRepository.class, JpaLinkChatRepository.class})
@ExtendWith(SpringExtension.class)
@EnableJpaRepositories("ru.tinkoff.edu.java.scrapper.domain.jpa")
@EntityScan("ru.tinkoff.edu.java.scrapper.domain.jpa.entity")
public class JpaTgChatBaseServiceTest extends IntegrationEnvironment {

    @Autowired
    private TgChatBaseService jpaTgChatBaseService;

    @Autowired
    private JpaLinkRepository jpaLinkRepository;

    @Autowired
    private JpaChatRepository jpaChatRepository;

    @Autowired
    private JpaLinkChatRepository jpaLinkChatRepository;

    @Test
    @Transactional
    @Rollback
    void addTest() {
        long testChatId = 2233;

        jpaTgChatBaseService.add(testChatId);

        Long chatId = jpaChatRepository.findById(testChatId).get().getChatId();

        assertEquals(testChatId, chatId);
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {

        // add a chat to be removed
        long testChatId = 2233;
        jpaTgChatBaseService.add(testChatId);

        // remove the chat
        jpaTgChatBaseService.remove(testChatId);

        Exception e = assertThrows(NoSuchElementException.class, () -> {
            jpaChatRepository.findById(testChatId).orElseThrow();
        });

        String expectedMessage = "No value present";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @Transactional
    @Rollback
    void findAllTest() {
        // add chat records
        long[] testChatIds = {1001, 1002, 1003};
        for (long chatId : testChatIds) {
            jpaTgChatBaseService.add(chatId);
        }

        // get all chats
        TgChatRepository allChats = jpaTgChatBaseService.findAll();

        assertEquals(testChatIds.length, allChats.length());

        for (int i = 0; i < testChatIds.length; i++) {
            assertEquals(testChatIds[i], allChats.tgChatId()[i]);
        }
    }
}

