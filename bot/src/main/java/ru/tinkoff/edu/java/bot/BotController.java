package ru.tinkoff.edu.java.bot;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateRequest;

@RestController
@RequestMapping("")
public class BotController {

//    private final BotService service;

    @PostMapping("/updates")
    public ResponseEntity<String> handleUpdate(@Valid @RequestBody LinkUpdateRequest linkUpdate) {

        if (linkUpdate.getId() < 0) {
            throw new IllegalArgumentException("Wrong ID");
        }
        if (linkUpdate.getUrl() == "https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c") {
            throw new IllegalStateException("Link already exists");
        }
        return ResponseEntity.ok("Update processed");
    }
}

