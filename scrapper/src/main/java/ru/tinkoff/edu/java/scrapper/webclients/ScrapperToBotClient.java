package ru.tinkoff.edu.java.scrapper.webclients;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ru.tinkoff.edu.java.scrapper.service.dto.LinkUpdateNotifyRequestDto;

@Component
public class ScrapperToBotClient {

    private final WebClient scrapperToBotWebClient;

    public ScrapperToBotClient(@Qualifier("scrapperToBotWebClient") WebClient scrapperToBotWebClient) {
        this.scrapperToBotWebClient = scrapperToBotWebClient;
    }

    public Flux<String> sendTrackedLinkNotify(LinkUpdateNotifyRequestDto info) {

        System.out.println("Scrapper sendTrackedLinkNotify sending request");

        return scrapperToBotWebClient.post()
                .uri("/updates")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(info))
                .retrieve()
                .bodyToFlux(String.class);
    }
}

