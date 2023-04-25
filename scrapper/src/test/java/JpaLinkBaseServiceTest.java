import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.tinkoff.edu.java.scrapper.configuration.JpaAccessConfiguration;
import ru.tinkoff.edu.java.scrapper.domain.LinkBaseService;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entity.Chat;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entity.Link;
import ru.tinkoff.edu.java.scrapper.domain.jpa.repository.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.repository.JpaLinkChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.repository.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.service.JpaLinkBaseService;
import ru.tinkoff.edu.java.scrapper.domain.repository.LinkWithTimeRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.ListLinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.ListLinkWithTimeRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest(classes = {IntegrationEnvironment.IntegrationEnvironmentConfig.class, JpaAccessConfiguration.class, JpaChatRepository.class, JpaLinkRepository.class, JpaLinkChatRepository.class})
@ExtendWith(SpringExtension.class)
@EnableJpaRepositories("ru.tinkoff.edu.java.scrapper.domain.jpa.repository")
public class JpaLinkBaseServiceTest extends IntegrationEnvironment {

    @Autowired
    private LinkBaseService jpaLinkBaseService;

    @Autowired
    private JpaLinkRepository jpaLinkRepository;

    @Autowired
    private JpaChatRepository jpaChatRepository;

    @Test
    @Transactional
    @Rollback
    void addTest() throws URISyntaxException {

        long testChatId = 2345;
        URI testUrl = new URI("https://overclockers.ru/");

        //register chat
        Chat chat = new Chat();
        chat.setChatId(testChatId);
        jpaChatRepository.save(chat);

        // add link
        jpaLinkBaseService.add(testChatId, testUrl);

        String actualUrl = jpaLinkRepository.findByUrl(String.valueOf(testUrl)).orElseThrow(() -> new RuntimeException("Link not found")).getUrl();

        assertEquals(testUrl.toString(), actualUrl);
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() throws URISyntaxException {

        // Add a chat and link to be removed
        long testChatId = 1234;
        Chat chat = new Chat();
        chat.setChatId(testChatId);
        jpaChatRepository.save(chat);
        URI testUrl = new URI("https://overclockers.ru/");
        jpaLinkBaseService.add(testChatId, testUrl);

        // remove the link
        jpaLinkBaseService.remove(testChatId, testUrl);

        // Check if the link was removed
        assertTrue(jpaLinkRepository.findByUrl(String.valueOf(testUrl)).isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    void findAllTest() throws URISyntaxException {

        // add chat record
        long testChatId = 1234;
        Chat chat = new Chat();
        chat.setChatId(testChatId);
        jpaChatRepository.save(chat);

        // add link records
        URI[] testUrls = {
                new URI("https://overclockers.ru/"),
                new URI("https://www.youtube.com/"),
                new URI("https://forum.ixbt.com/")
        };
        Arrays.stream(testUrls).forEach(url -> jpaLinkBaseService.add(testChatId, url));

        // get all links
        ListLinkRepository allLinks = jpaLinkBaseService.findAll(testChatId);

        assertEquals(testUrls.length, allLinks.size());

        List<URI> actualUrls = Arrays.asList(allLinks.links()).stream()
                .map(LinkRepository::url)
                .toList();

        assertEquals(Arrays.asList(testUrls), actualUrls);
    }

    @Test
    @Transactional
    @Rollback
    void findAllFilteredByTimeoutTest() throws URISyntaxException {
        // add chat
        long testChatId = 1234;
        Chat chat = new Chat();
        chat.setChatId(testChatId);
        jpaChatRepository.save(chat);

        // add link records
        URI[] testUrls = {
                new URI("https://overclockers.ru/"),
                new URI("https://www.youtube.com/"),
                new URI("https://forum.ixbt.com/")
        };

        // add links and update updatedAt field
        for (URI testUrl : testUrls) {
            jpaLinkBaseService.add(testChatId, testUrl);
        }

        // update updatedAt field for each link
        Link link1 = jpaLinkRepository.findByUrl(testUrls[0].toString()).get();
        link1.setUpdatedAt(OffsetDateTime.now().minusMinutes(50));
        jpaLinkRepository.save(link1);

        Link link2 = jpaLinkRepository.findByUrl(testUrls[1].toString()).get();
        link2.setUpdatedAt(OffsetDateTime.now().minusMinutes(30));
        jpaLinkRepository.save(link2);

        Link link3 = jpaLinkRepository.findByUrl(testUrls[2].toString()).get();
        link3.setUpdatedAt(OffsetDateTime.now().minusMinutes(10));
        jpaLinkRepository.save(link3);

        // set timeout to 20 minutes
        long timeout = 20;

        ListLinkWithTimeRepository filteredLinks = jpaLinkBaseService.findAllFilteredByTimeout(timeout);

        assertEquals(2, filteredLinks.size());

        List<URI> actualUrls = Arrays.asList(filteredLinks.linksWithTime()).stream()
                .map(LinkWithTimeRepository::url)
                .toList();

        List<URI> expectedUrls = List.of(testUrls[0], testUrls[1]);

        assertEquals(expectedUrls, actualUrls);
    }
}
