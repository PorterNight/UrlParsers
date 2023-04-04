package ru.tinkoff.edu.java.bot.telegramBot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.repository.UserRepository;
import ru.tinkoff.edu.java.bot.telegramBot.Command;

@Service
public class StartCommand implements Command {

    private UserRepository repo;

    public StartCommand(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public String command() {
        return BotCommand.START.getCommand();
    }

    @Override
    public boolean supports(Update update) {
        return BotCommand.START.getCommand().equals(update.message().text());
    }


    @Override
    public SendMessage handle(Update update) {

        long chatId = update.message().chat().id();
        String userFirstName = update.message().from().firstName();

        if (repo.registerUser(chatId))
            return new SendMessage(chatId, "User: " + userFirstName + " is registered");
        else
            return new SendMessage(chatId, "User: " + userFirstName + " is already registered");
    }
}