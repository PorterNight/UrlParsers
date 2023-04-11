package ru.tinkoff.edu.java.bot.telegramBot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.telegramBot.Command;

@Service
public class HelpCommand implements Command {

    public String command() {
        return BotCommand.HELP.getCommand();
    }

    @Override
    public boolean supports(Update update) {
        return BotCommand.HELP.getCommand().equals(update.message().text());
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();

        return new SendMessage(chatId, BotCommand.START.getCommand() + BotCommand.START.getDescription() + "\n" +
                BotCommand.TRACK.getCommand() + BotCommand.TRACK.getDescription() + "\n" +
                BotCommand.UNTRACK.getCommand() + BotCommand.UNTRACK.getDescription() + "\n" +
                BotCommand.LIST.getCommand() + BotCommand.LIST.getDescription() );
    }
}
