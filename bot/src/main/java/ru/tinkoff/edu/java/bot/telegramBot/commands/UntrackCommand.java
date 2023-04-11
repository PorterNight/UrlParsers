package ru.tinkoff.edu.java.bot.telegramBot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.telegramBot.Command;

@Service
public class UntrackCommand implements Command {

    public String command() {
        return BotCommand.UNTRACK.getCommand();
    }

    @Override
    public boolean supports(Update update) {
        return BotCommand.UNTRACK.getCommand().equals(update.message().text());
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        return new SendMessage(chatId,  BotCommand.UNTRACK_SAVE.getCommand().toString());
    }
}