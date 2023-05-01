package ru.tinkoff.edu.java.bot.service;

import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.exceptions.BotControllerException;
import ru.tinkoff.edu.java.bot.service.dto.UpdateLinkDto;

@Service
public class BotService {

    private final BotNotifierService botNotifierService;

    public BotService(BotNotifierService botNotifierService) {
        this.botNotifierService = botNotifierService;
    }

    public String updateLink(UpdateLinkDto update) {
        try {
            botNotifierService.updateLink(update);
        } catch (Exception e) {
            throw new BotControllerException("error sending update to telegram-bot", 400);
        }
        return "Ссылки отправлены";
    }
}
