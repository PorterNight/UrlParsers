package ru.tinkoff.edu.java.scrapper.webclients;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.scrapper.dto.GitHubFetchedData;
import ru.tinkoff.edu.java.scrapper.dto.StackOverFlowFetchedData;
import ru.tinkoff.edu.java.scrapper.service.ScrapperWebClientsService;
import ru.tinkoff.edu.java.scrapper.service.dto.GitHubClientResponseDto;
import ru.tinkoff.edu.java.scrapper.service.dto.StackOverflowInfoDto;

@RestController
@RequestMapping("/scrapper")
public class ScrapperWebClientsController {

    private final ScrapperWebClientsService service;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;

    public ScrapperWebClientsController(ScrapperWebClientsService service, GitHubClient gitHubClient, StackOverflowClient stackOverflowClient) {
        this.service = service;
        this.gitHubClient = gitHubClient;
        this.stackOverflowClient = stackOverflowClient;
    }


    @GetMapping("/stackoverflow/{tag}")
    public ResponseEntity<StackOverFlowFetchedData> getStackOverFlowInfoByTag(@PathVariable String tag) {

        StackOverflowInfoDto questions = stackOverflowClient.getFaqByTag(tag).blockLast();
        StackOverFlowFetchedData result = service.getStackOverflowInfo(questions);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/github/{owner}/{repo}")
    public ResponseEntity<GitHubFetchedData> getGitHubInfoByOwner(@PathVariable String owner, @PathVariable String repo) {

        GitHubClientResponseDto repoResponse = gitHubClient.getListRepoEvents(owner, repo).blockLast();
        GitHubFetchedData result = service.getGitHubInfo(repoResponse);
        return ResponseEntity.ok(null);
    }

}
