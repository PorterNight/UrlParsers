
package ru.tinkoff.edu.java.bot.telegramBot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import ru.tinkoff.edu.java.bot.telegramBot.UserMessageProcessImpl;
import ru.tinkoff.edu.java.bot.telegramBot.interfaces.Command;
import ru.tinkoff.edu.java.bot.telegramBot.interfaces.UserMessageProcessor;

import java.util.stream.Collectors;

public class HelpCommand implements Command {

    @Override
    public String command() {
        return "/Help";
    }

    @Override
    public String description() { return "command is used for getting help";  }

    @Override
    public String handle(Update update, UserMessageProcessor proc) {


        String res = proc.commands().stream()
                .map(i -> i.command() + ": " + i.description())
                .collect(Collectors.joining(",\n"));

        return (res);


    }
}
