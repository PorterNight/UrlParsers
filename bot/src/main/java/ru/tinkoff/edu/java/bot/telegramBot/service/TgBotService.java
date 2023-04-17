package ru.tinkoff.edu.java.bot.telegramBot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.tinkoff.edu.java.bot.webClients.BotWebClient;
import ru.tinkoff.edu.java.scrapper.dto.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.dto.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.service.ScrapperService;

import java.net.URISyntaxException;

@Service
public class TgBotService {

    private final BotWebClient client;
    private static final Logger log = LoggerFactory.getLogger(ScrapperService.class);

    public TgBotService(BotWebClient client) {
        this.client = client;
    }

    public Flux<String> sendChatByIDToRegister(long tgChatId){
        log.info("Bot: TgBotService executing sendChatByIDToRegister");
        return client.sendRegisterChatByID(tgChatId);

    }


    public Flux<AddLinkRequest> sendLinkToScrapperToTrack(long tgChatId, String url) throws URISyntaxException {
        return client.sendTrackedLink(tgChatId, url);
    }

    public  Flux<RemoveLinkRequest> sendLinkToScrapperToUntrack(long tgChatId, String url) throws URISyntaxException {
        return client.sendUnTrackedLink(tgChatId, url);
    }

    public ListLinksResponse sendReqToScrapperToGetListOfLinks(long tgChatId) {
        return client.getTrackedLink(tgChatId).blockLast();
    }

}
