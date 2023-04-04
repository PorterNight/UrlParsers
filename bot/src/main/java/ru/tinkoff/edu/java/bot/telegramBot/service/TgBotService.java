package ru.tinkoff.edu.java.bot.telegramBot.service;

import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.webClients.BotWebClient;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;

import java.net.URISyntaxException;

@Service
public class TgBotService {

    private final BotWebClient client;

    public TgBotService(BotWebClient client) {
        this.client = client;
    }

    public void sendLinkToScrapperToTrack(long tgChatId, String url) throws URISyntaxException {
        client.sendTrackedLink(tgChatId, url).blockLast();
    }

    public void sendLinkToScrapperToUntrack(long tgChatId, String url) throws URISyntaxException {
        client.sendUnTrackedLink(tgChatId, url).blockLast();
    }

    public ListLinksResponse sendReqToScrapperToGetListOfLinks(long tgChatId) {
        return client.getTrackedLink(tgChatId).blockLast();
    }

}
