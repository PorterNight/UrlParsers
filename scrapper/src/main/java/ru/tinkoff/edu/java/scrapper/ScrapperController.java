package ru.tinkoff.edu.java.scrapper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.*;
import ru.tinkoff.edu.java.scrapper.service.ScrapperService;
import ru.tinkoff.edu.java.scrapper.service.dto.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/scrapper")
public class ScrapperController {

    private final ScrapperService service;

    public ScrapperController(ScrapperService service) {
        this.service = service;
    }


    @PostMapping("/tg-chat/{id}")
    public ResponseEntity<String> registerChatById(@PathVariable long id) {

        RegisterChatDto registerChatDto = new RegisterChatDto(id);
        String result = service.registerChat(registerChatDto);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity<String> deleteChatByID(@PathVariable long id) {

        DeleteChatDto deleteChatDto = new DeleteChatDto(id);
        String result = service.deleteChat(deleteChatDto);

        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/links", produces = "application/json")
    public ResponseEntity<ListLinksResponse> getLinks(@RequestHeader long tgChatId) {

        GetLinksDto getLinksDto = new GetLinksDto(tgChatId);
        ListLinksResponse result = service.getLinks(getLinksDto);

        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/links", produces = "application/json")
    public ResponseEntity<LinkResponse> addLink(@RequestHeader long tgChatId, @RequestBody AddLinkRequest request) {

        System.out.println("Scrapper chat start : " + tgChatId);

        AddLinkDto addLinkDto = new AddLinkDto(tgChatId, request.link());
        LinkResponse result = service.addLink(addLinkDto);

        System.out.println("Scrapper : link added:" + result);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping(value = "/links", produces = "application/json")
    public ResponseEntity<LinkResponse> removeLink(@RequestHeader long tgChatId, @RequestBody RemoveLinkRequest request) {

        RemoveLinkDto removeLinkDto = new RemoveLinkDto(tgChatId, request.link());
        LinkResponse result = service.removeLink(removeLinkDto);

        return ResponseEntity.ok(result);
    }
}

