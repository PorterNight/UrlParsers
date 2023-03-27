package ru.tinkoff.edu.java.scrapper.webclients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {

    @Bean
    public WebClient gitHubWebClient(@Value("${gitHubWebClient.base.url:https://api.github.com}") String baseUrl) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Bean
    public WebClient stackOverflowWebClient(@Value("${stackOverflowWebClient.base.url:https://api.stackexchange.com/2.3}") String baseUrl) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

}
