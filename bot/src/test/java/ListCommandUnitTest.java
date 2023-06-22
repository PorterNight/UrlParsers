import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.val;
import mocks.ChatMock;
import mocks.MessageMock;
import mocks.UpdateMock;
import org.junit.Before;
import org.junit.Test;
import ru.tinkoff.edu.java.bot.telegramBot.commands.ListCommand;
import ru.tinkoff.edu.java.bot.telegramBot.service.TgBotService;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ListCommandUnitTest {

    private TgBotService tgBotService;
    private ListCommand listCommand;

    @Before
    public void init() {
        tgBotService = mock(TgBotService.class);

        listCommand = new ListCommand(tgBotService);
    }

    @Test
    public void checkSupportedCommandMethod() {
        val update = new UpdateMock()
                .setUpdate_id(111111)
                .setMessage(new MessageMock().setText("/list", new Chat()));

        boolean supports = listCommand.supports(update);

        assertTrue(supports);
    }

    @Test
    public void checkUnsupportedCommandMethod() {
        val update = new UpdateMock()
                .setUpdate_id(111111)
                .setMessage(new MessageMock().setText("/absent command", new Chat()));

        boolean supports = listCommand.supports(update);

        assertFalse(supports);
    }

    @Test
    public void checkHandleMethod() throws URISyntaxException {

        // create a mock update
        val update = new UpdateMock()
                .setUpdate_id(111111)
                .setMessage(new MessageMock().setText("/list", new ChatMock().setId(42442L)));

        // create a mock response from the scrapper service
        val link1 = new LinkResponse(1, new URI("https://www.youtube.com/"));
        val link2 = new LinkResponse(2, new URI("https://habr.com/ru/top/monthly/"));
        val links = new LinkResponse[]{link1, link2};
        val res = new ListLinksResponse(links, 2);

        when(tgBotService.sendReqToScrapperToGetListOfLinks(update.message().chat().id()))
                .thenReturn(res);

        SendMessage actualMessage = listCommand.handle(update);

        String expectedText = "tracked links: \n" + link1.url() + "\n" + link2.url();

        assertEquals(expectedText, actualMessage.getParameters().get("text"));
    }

    @Test
    public void checkHandleMethodWithNull() {

        // create a mock update
        val update = new UpdateMock()
                .setUpdate_id(111111)
                .setMessage(new MessageMock().setText("/list", new ChatMock().setId(42442L)));

        // create a mock response with null from the scrapper service
        val res = new ListLinksResponse(null, 0);

        when(tgBotService.sendReqToScrapperToGetListOfLinks(update.message().chat().id()))
                .thenReturn(res);

        SendMessage actualMessage = listCommand.handle(update);

        String expectedText = "no tracked links";

        assertEquals(expectedText, actualMessage.getParameters().get("text"));
    }
}
