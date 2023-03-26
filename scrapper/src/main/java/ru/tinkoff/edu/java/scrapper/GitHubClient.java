package ru.tinkoff.edu.java.scrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Map;

@Component
public class GitHubClient {

    @Autowired
    private WebClient gitHubWebClient;

//    public RepoResponse fetchRepositoryInfo(String owner, String repo) {
//        return gitHubWebClient.get()
//                .uri("/repos/{owner}/{repo}", owner, repo)
//                .retrieve()
//                .bodyToMono(RepoResponse.class)
//                .block();
//    }



    public Flux<Map> getUserRepositories(String username) {
        return gitHubWebClient.get()
                .uri("/users/{username}/repos", username)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToFlux(Map.class);
    }


}