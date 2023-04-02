package ru.tinkoff.edu.java.bot.webClients;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ru.tinkoff.edu.java.scrapper.service.dto.GitHubClientResponseDto;

@Component
public class BotWebClient {

    private final WebClient botWebClient;

    public BotWebClient(@Qualifier("botWebConfigClient") WebClient botWebClient) {
        this.botWebClient = botWebClient;
    }

    public Flux<GitHubClientResponseDto> sendRegisterChatByID(long tgChatId) {
        return botWebClient.get()
                .uri("/tg-chat/{tgChatId}", tgChatId)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToFlux(GitHubClientResponseDto.class);
    }


    public Flux<GitHubClientResponseDto> setTrackedLink(long tgChatId, String link) {
        return botWebClient.get()
                .uri("/links")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToFlux(GitHubClientResponseDto.class);
    }

}




