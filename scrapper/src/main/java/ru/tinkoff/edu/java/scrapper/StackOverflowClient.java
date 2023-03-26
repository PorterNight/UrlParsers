package ru.tinkoff.edu.java.scrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.dto.QuestionResponse;

import java.net.URI;

@Component
public class StackOverflowClient {

    @Autowired
    private WebClient stackOverflowWebClient;

    public QuestionResponse fetchQuestionInfo(int questionId) {
        return stackOverflowWebClient.get()
                //.uri("/questions/{questionId}?site=stackoverflow", questionId)
                .uri(uri -> {
                    URI build = uri.path("https://api.github.com/questions/{questionId}")

                            .queryParam("site", "stackoverflow")
                            .build(questionId);
                    return build;})

                .retrieve()
                .bodyToMono(QuestionResponse.class)
                .block();
    }
}