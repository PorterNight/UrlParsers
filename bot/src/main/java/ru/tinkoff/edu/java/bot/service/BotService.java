package ru.tinkoff.edu.java.bot.service;

import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.exceptions.BotControllerException;
import ru.tinkoff.edu.java.bot.service.dto.UpdateLinkDto;
import ru.tinkoff.edu.java.scrapper.exceptions.IsValidURL;

@Service
public class BotService {

    public String updateLink(UpdateLinkDto info) {
        return "ссылка обновлена";
    }
}
