package ru.tinkoff.edu.java.bot.telegramBot.commands;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.telegramBot.Command;
import ru.tinkoff.edu.java.bot.telegramBot.service.TgBotService;

import java.net.URISyntaxException;

@Service
public class UntrackSaveCommand implements Command {

    private TgBotService tgBotService;

    public UntrackSaveCommand(TgBotService tgBotService) {
        this.tgBotService = tgBotService;
    }

    public String command() {
        return BotCommand.UNTRACK_SAVE.getCommand();
    }


    @Override
    public boolean supports(Update update) {
        Message reply = update.message().replyToMessage();
        if (reply != null) {
            return BotCommand.UNTRACK_SAVE.getCommand().equals(reply.text());
        } else return false;
    }


    public String description() {
        return "command is used for untracking link";
    }

    @Override
    public SendMessage handle(Update update) throws URISyntaxException {
        String link = update.message().text();
        long chatId = update.message().chat().id();

        tgBotService.sendLinkToScrapperToUntrack(chatId, link);

        return new SendMessage(chatId, "Ваша ссылка успешно убрана из регистрации: " + link);
    }
}
