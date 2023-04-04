package ru.tinkoff.edu.java.bot.telegramBot;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import java.net.URISyntaxException;

public interface Command {

    String command();

    SendMessage handle(Update update) throws URISyntaxException;

    default boolean supports(Update update) {
        return false;
    }

}

