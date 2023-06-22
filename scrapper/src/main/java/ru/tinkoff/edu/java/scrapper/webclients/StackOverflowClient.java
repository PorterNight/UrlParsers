package ru.tinkoff.edu.java.scrapper.webclients;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ru.tinkoff.edu.java.scrapper.service.dto.StackOverflowInfoDto;
import ru.tinkoff.edu.java.scrapper.service.dto.StackOverflowListAnswersDto;

@Component
public class StackOverflowClient {

    private final WebClient stackOverflowClient;

    public StackOverflowClient(@Qualifier("stackOverflowWebClient") WebClient stackOverflowClient) {
        this.stackOverflowClient = stackOverflowClient;
    }

    public Flux<StackOverflowInfoDto> getFaqByTag(String tags) {

        return stackOverflowClient.get()
                .uri(uriBuilder -> uriBuilder.path("/tags/{tags}/faq")
                        .queryParam("site", "stackoverflow")
                        .build(tags))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToFlux(StackOverflowInfoDto.class);
    }


    public Flux<StackOverflowListAnswersDto> getAnswersByID(int id) {

        return stackOverflowClient.get()
                .uri(uriBuilder -> uriBuilder.path("/questions/{id}/answers")
                        .queryParam("site", "stackoverflow")
                        .build(id))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToFlux(StackOverflowListAnswersDto.class)
                .take(1);
    }
}

