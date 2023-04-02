package ru.tinkoff.edu.java.bot.telegramBot.commands;

import com.pengrad.telegrambot.model.Update;
import ru.tinkoff.edu.java.bot.telegramBot.interfaces.Command;
import ru.tinkoff.edu.java.bot.telegramBot.interfaces.UserMessageProcessor;

import java.util.HashMap;
import java.util.Map;

public class TrackCommand implements Command {

    private static Map<String, Boolean> userList = new HashMap<>();

    @Override
    public String command() {
        return "/Track";
    }

    @Override
    public String description() {
        return "command is used to track url updates";
    }

    @Override
    public String handle(Update update, UserMessageProcessor proc) {

        String user = update.message().text();

        System.out.println(userList);

        if (!userList.getOrDefault(user, false)) {
            userList.put(user, true);
            return ("Track " + user + " registered");
        } else
            return ("Track " + user + " already registered");



    }
}