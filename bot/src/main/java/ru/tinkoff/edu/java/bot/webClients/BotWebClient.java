
package ru.tinkoff.edu.java.bot.webClients;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.dto.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.dto.RemoveLinkRequest;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class BotWebClient {

    private final WebClient botWebClient;

    public BotWebClient(WebClient botWebClient) {
        this.botWebClient = botWebClient;
    }

    public Flux<String> sendRegisterChatByID(long tgChatId) {
        System.out.println("BotWebClient : sending sendRegisterChatByID " + tgChatId);

        return botWebClient.post()
                .uri("/tg-chat/{tgChatId}", tgChatId)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToFlux(String.class);
    }


    public Flux<AddLinkRequest> sendTrackedLink(long tgChatId, String link) throws URISyntaxException {

        AddLinkRequest addLinkRequest = new AddLinkRequest(new URI(link));

        return botWebClient.post()
                .uri("/links")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("tgChatId", String.valueOf(tgChatId))
                .body(BodyInserters.fromValue(addLinkRequest))
                .retrieve()
                .bodyToFlux(AddLinkRequest.class);
    }

    public Flux<RemoveLinkRequest> sendUnTrackedLink(long tgChatId, String link) throws URISyntaxException {

        AddLinkRequest addLinkRequest = new AddLinkRequest(new URI(link));

        return botWebClient.method(HttpMethod.DELETE)
                .uri("/links")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("tgChatId", String.valueOf(tgChatId))
                .body(BodyInserters.fromValue(addLinkRequest))
                .exchange()
                .flatMapMany(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToFlux(RemoveLinkRequest.class);
                    } else {
                        return response.createException().flatMapMany(Mono::error);
                    }
                });
    }

    public Flux<ListLinksResponse> getTrackedLink(long tgChatId) {
        System.out.println("BotWebClient : sending sendRegisterChatByID " + tgChatId);

        return botWebClient.get()
                .uri("/links")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("tgChatId", String.valueOf(tgChatId))
                .retrieve()
                .bodyToFlux(ListLinksResponse.class);
    }



}


