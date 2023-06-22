package ru.tinkoff.edu.java.bot.service;

import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.service.dto.UpdateLinkDto;
import ru.tinkoff.edu.java.bot.telegramBot.impl.BotImpl;

@Service
public class BotNotifierService {

    private final BotImpl botImpl;

    public BotNotifierService(BotImpl botImpl) {
        this.botImpl = botImpl;
    }

    public void updateLink(UpdateLinkDto info) {

        for (long chat_id :
                info.tgChatIds()) {
            botImpl.sendUpdateLinkInfo(chat_id, "Новое обновление для " + info.description()  + " для ссылки: " +  info.url());
        }
    }
}
