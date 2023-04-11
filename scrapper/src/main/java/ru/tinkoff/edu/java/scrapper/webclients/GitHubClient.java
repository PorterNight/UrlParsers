package ru.tinkoff.edu.java.scrapper.webclients;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ru.tinkoff.edu.java.scrapper.service.dto.GitHubClientResponseDto;

@Component
public class GitHubClient {

    private final WebClient gitHubWebClient;

    public GitHubClient(@Qualifier("gitHubWebClient") WebClient gitHubWebClient) {
        this.gitHubWebClient = gitHubWebClient;
    }

    public Flux<GitHubClientResponseDto> getUserRepositories(String owner) {
        return gitHubWebClient.get()
                .uri("/users/{username}/repos", owner)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToFlux(GitHubClientResponseDto.class);
    }

    public Flux<GitHubClientResponseDto> getListRepoEvents(String owner, String repo) {
        return gitHubWebClient.get()
                .uri("/repos/{owner}/{repo}/events", owner, repo)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToFlux(GitHubClientResponseDto.class);
    }
}


