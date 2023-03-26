package ru.tinkoff.edu.java.scrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.exceptions.*;
import ru.tinkoff.edu.java.scrapper.dto.*;

import java.util.Map;

@RestController
@RequestMapping("")
public class ScrapperController {

//    private final ScrapperService service;

    @PostMapping("/tg-chat/{id}")
    public ResponseEntity<String> registerChatById(@PathVariable Long id) {

        if (id.equals(555L)) {
            throw new BadRequestException("Чат уже зарегистрирован");
        }
        AddLinkRequest addLinkRequest = new AddLinkRequest(id);
        return ResponseEntity.ok("Чат зарегистрирован");
    }

    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity<String> deleteChat(@PathVariable Long id) {

        if (id.equals(555L)) {
            throw new BadRequestException("Ошибка удаления чата");
        }
        if (id < 0) {
            throw new NotFoundException("Чат не существует");
        }
        DeleteChatRequest deleteChatRequest = new DeleteChatRequest(id);
        return ResponseEntity.ok("Чат успешно удален");
    }

    @GetMapping("/links")
    public ResponseEntity<ListLinksResponse> getLinks(@RequestHeader Long tgChatId) {

        if (tgChatId < 0) {
            throw new BadRequestException("Ошибка получения ссылок");
        }

        return ResponseEntity.ok(new ListLinksResponse());

    }

    @PostMapping("/links")
    public ResponseEntity<LinkResponse> addLink(@RequestHeader Long tgChatId, @RequestBody AddLinkRequest request) {

        if (tgChatId < 0) {
            throw new BadRequestException("Ошибка получения ссылок");
        }
        AddLinkRequest addLinkRequest = request;
        return ResponseEntity.ok(new LinkResponse());
    }

    @DeleteMapping("/links")
    public ResponseEntity<String> removeLink(@RequestHeader Long tgChatId, @RequestBody RemoveLinkRequest request) {

        if (tgChatId.equals(555L)) {
            throw new BadRequestException("Чат уже зарегистрирован");
        }
        if (tgChatId < 0) {
            throw new NotFoundException("Ссылка не найдена");
        }

        RemoveLinkRequest req = request;
        return ResponseEntity.ok("Ссылка успешно убрана");
    }


// Github and Stackoverflow web clients:

    @Autowired
    private StackOverflowClient stackOverflowClient;

    @GetMapping("/stackoverflow/{questionId}")
    public ResponseEntity<QuestionResponse> getQuestionInfo(@PathVariable int questionId) {
        QuestionResponse questionResponse = stackOverflowClient.fetchQuestionInfo(questionId);
        System.out.println(questionResponse.toString());
        return ResponseEntity.ok(questionResponse);
    }

    @Autowired
    private GitHubClient gitHubClient;

    @GetMapping("/github/{owner}")
    public ResponseEntity<String> getRepositoryInfo(@PathVariable String owner) {
        Map<String,RepoResponse> repoResponse = gitHubClient.getUserRepositories(owner).blockLast();
        System.out.println(repoResponse.toString());
        return ResponseEntity.ok("Ok");
    }
}

