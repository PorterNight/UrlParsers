
package ru.tinkoff.edu.java.bot.telegramBot.impl;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.telegramBot.Command;
import ru.tinkoff.edu.java.bot.telegramBot.UserMessageProcessor;

import java.net.URISyntaxException;
import java.util.List;

@Service
public class UserMessageProcessImpl implements UserMessageProcessor {

    private final List<Command> commands;

    public UserMessageProcessImpl(
            List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public List<? extends Command> commands() {
        return commands;
    }

    @Override
    public SendMessage process(Update update) {

        long chatId = update.message().chat().id();

        if (update.message() == null) {
            return null;
        }

        String msg = "Неизвестная команда";

        SendMessage res = commands.stream()
                .filter(command -> command.supports(update))
                .findFirst()
                .map(command -> {
                    try {
                        return command.handle(update);
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                })
                .orElse(new SendMessage(chatId, msg)) ;

        return res;
    }
}

