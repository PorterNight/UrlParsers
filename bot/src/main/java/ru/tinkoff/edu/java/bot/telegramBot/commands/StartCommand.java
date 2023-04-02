package ru.tinkoff.edu.java.bot.telegramBot.commands;

import com.pengrad.telegrambot.model.Update;
import ru.tinkoff.edu.java.bot.telegramBot.interfaces.Command;
import ru.tinkoff.edu.java.bot.telegramBot.interfaces.UserMessageProcessor;

import java.util.HashMap;
import java.util.Map;

public class StartCommand implements Command {

    private static Map<String, Boolean> userList = new HashMap<>();

    @Override
    public String command() {
        return "/Start";
    }

    @Override
    public String description() {
        return "command is used for user registration";
    }

    @Override
    public String handle(Update update, UserMessageProcessor proc) {

        String url = update.message().text();

        System.out.println(userList);

        if (!userList.getOrDefault(url, false)) {
            userList.put(url, true);
            return ("User " + url + " registered");
        } else
            return ("User " + url + " already registered");



    }
}