package ru.tinkoff.edu.java.bot.webClients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BotClientConfiguration {

    @Bean
    public WebClient botWebConfigClient(@Value("${bot.webclient.base.url:http://localhost:8080/}") String baseUrl) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }


}
