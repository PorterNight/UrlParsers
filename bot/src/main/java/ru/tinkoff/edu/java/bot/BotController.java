package ru.tinkoff.edu.java.bot;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.bot.dto.LinkUpdate;
import ru.tinkoff.edu.java.bot.service.BotService;
import ru.tinkoff.edu.java.bot.service.dto.UpdateLinkDto;

@RestController
@RequestMapping("bot/")
public class BotController {

    private final BotService service;

    public BotController(BotService service) {
        this.service = service;
    }

    @PostMapping(value="/updates", produces = "application/json")
    public ResponseEntity<String> handleUpdate(@Valid @RequestBody LinkUpdate link) throws Exception {
        UpdateLinkDto linkDto = new UpdateLinkDto(link.id(), link.url(), link.description(), link.tgChatIds());
        String result = service.updateLink(linkDto);

        return ResponseEntity.ok(result);
    }
}
