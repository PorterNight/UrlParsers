import configuration.IntegrationEnvironment;
import configuration.TestsConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.tinkoff.edu.java.scrapper.domain.LinkService;
import ru.tinkoff.edu.java.scrapper.domain.TgChatService;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entity.LinkChat;
import ru.tinkoff.edu.java.scrapper.domain.jpa.repository.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.repository.JpaLinkChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.repository.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.ListLinkRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(classes = {TestsConfiguration.class})
@ExtendWith(SpringExtension.class)
public class JpaIntegrationServiceTest extends IntegrationEnvironment {

    @Autowired
    private LinkService jpaLinkService;

    @Autowired
    private TgChatService jpaTgChatService;

    @Autowired
    private JpaLinkRepository jpaLinkRepository;

    @Autowired
    private JpaChatRepository jpaChatRepository;

    @Autowired
    private JpaLinkChatRepository jpaLinkChatRepository;

    @Test
    @Transactional
    @Rollback
    void addTgChatUsersAndLinksTest() throws URISyntaxException {

        long testChatId1 = 2345;
        long testChatId2 = 1345;
        URI testUrl1 = new URI("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c-c");
        URI testUrl2 = new URI("https://github.com/AykutSarac/jsoncrack.com");

        // register chat
        jpaTgChatService.register(testChatId1);
        jpaTgChatService.register(testChatId2);

        // add link
        jpaLinkService.add(testChatId1, testUrl1);
        jpaLinkService.add(testChatId1, testUrl2);
        jpaLinkService.add(testChatId2, testUrl1);
        jpaLinkService.add(testChatId2, testUrl2);

        String actualUrl = jpaLinkRepository.findByUrl(testUrl1.toString()).get().getUrl();
        assertEquals(testUrl1.toString(), actualUrl);

        String actualUrl2 = jpaLinkRepository.findByUrl(testUrl2.toString()).get().getUrl();
        assertEquals(testUrl2.toString(), actualUrl2);

        Long actualChatId1 = jpaChatRepository.findById(testChatId1).get().getChatId();
        assertEquals(testChatId1, actualChatId1);

        Long actualChatId2 = jpaChatRepository.findById(testChatId2).get().getChatId();
        assertEquals(testChatId2, actualChatId2);

        // check number of urls
        List<LinkChat> linkChatsForChat1 = jpaLinkChatRepository.findByChat_ChatId(testChatId1);
        assertEquals(2, linkChatsForChat1.size());

        assertEquals(testChatId1, linkChatsForChat1.get(0).getChat().getChatId());
        assertEquals(testUrl1.toString(), linkChatsForChat1.get(0).getLink().getUrl());
        assertEquals(testUrl2.toString(), linkChatsForChat1.get(1).getLink().getUrl());

        // check number of urls
        List<LinkChat> linkChatsForChat2 = jpaLinkChatRepository.findByChat_ChatId(testChatId2);
        assertEquals(2, linkChatsForChat2.size());

        assertEquals(testChatId2, linkChatsForChat2.get(0).getChat().getChatId());
        assertEquals(testUrl1.toString(), linkChatsForChat2.get(0).getLink().getUrl());
        assertEquals(testUrl2.toString(), linkChatsForChat2.get(1).getLink().getUrl());

        // check link_chat data for the first link in chat 2
        assertEquals(testChatId2, linkChatsForChat2.get(0).getChat().getChatId());
        assertEquals(testUrl1.toString(), linkChatsForChat2.get(0).getLink().getUrl());

        // check link_chat data for the second link in chat 2
        assertEquals(testChatId2, linkChatsForChat2.get(1).getChat().getChatId());
        assertEquals(testUrl2.toString(), linkChatsForChat2.get(1).getLink().getUrl());
    }

    @Test
    @Transactional
    @Rollback
    void removeLinksTest() throws URISyntaxException {

        long testChatId1 = 2345;
        long testChatId2 = 1345;
        URI testUrl1 = new URI("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c-c");
        URI testUrl2 = new URI("https://github.com/AykutSarac/jsoncrack.com");

        // register chat
        jpaTgChatService.register(testChatId1);
        jpaTgChatService.register(testChatId2);

        // add link
        jpaLinkService.add(testChatId1, testUrl1);
        jpaLinkService.add(testChatId1, testUrl2);
        jpaLinkService.add(testChatId2, testUrl1);
        jpaLinkService.add(testChatId2, testUrl2);

        // remove one url
        jpaLinkService.remove(testChatId2, testUrl2);

        Long actualChatId1 = jpaChatRepository.findById(testChatId1).get().getChatId();
        assertEquals(testChatId1, actualChatId1);

        Long actualChatId2 = jpaChatRepository.findById(testChatId2).get().getChatId();
        assertEquals(testChatId2, actualChatId2);

        List<LinkChat> linkChatsForChat1 = jpaLinkChatRepository.findByChat_ChatId(testChatId1);
        assertEquals(2, linkChatsForChat1.size());

        assertEquals(testChatId1, linkChatsForChat1.get(0).getChat().getChatId());
        assertEquals(testUrl1.toString(), linkChatsForChat1.get(0).getLink().getUrl());

        List<LinkChat> linkChatsForChat2 = jpaLinkChatRepository.findByChat_ChatId(testChatId2);
        assertEquals(1, linkChatsForChat2.size());

        assertEquals(testChatId2, linkChatsForChat2.get(0).getChat().getChatId());
        assertEquals(testUrl1.toString(), linkChatsForChat2.get(0).getLink().getUrl());

        // Check link_chat data for the first link in chat 2
        assertEquals(testChatId2, linkChatsForChat2.get(0).getChat().getChatId());
        assertEquals(testUrl1.toString(), linkChatsForChat2.get(0).getLink().getUrl());
    }

    @Test
    @Transactional
    @Rollback
    void getLinksTest() throws URISyntaxException {

        long testChatId = 1234;
        jpaTgChatService.register(testChatId);

        URI[] testUrls = {
                new URI("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c-c"),
                new URI("https://github.com/AykutSarac/jsoncrack.com")
        };
        Arrays.stream(testUrls).forEach(url -> jpaLinkService.add(testChatId, url));

        // get all links for the chat
        ListLinkRepository allLinks = jpaLinkService.listAll(testChatId);

        assertEquals(testUrls.length, allLinks.size());

        List<URI> actualUrls = Arrays.asList(allLinks.links()).stream()
                .map(LinkRepository::url)
                .toList();

        assertEquals(Arrays.asList(testUrls), actualUrls);
    }


}
