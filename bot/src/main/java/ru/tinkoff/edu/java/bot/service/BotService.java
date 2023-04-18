package ru.tinkoff.edu.java.bot.service;

import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.service.dto.UpdateLinkDto;
import ru.tinkoff.edu.java.bot.telegramBot.impl.BotImpl;

@Service
public class BotService {


    private BotImpl botImpl;

    public BotService(BotImpl botImpl) {
        this.botImpl = botImpl;
    }


    public String updateLink(UpdateLinkDto info) {

        for (long chat_id :
                info.tgChatIds()) {
            botImpl.sendUpdateLinkInfo(chat_id, "Новое обновление для " + info.description()  + " для ссылки: " +  info.url());
        }
        return "Ссылки отправлены";
    }
}
