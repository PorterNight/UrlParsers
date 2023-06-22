package ru.tinkoff.edu.java.bot.telegramBot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.bot.repository.UserRepository;
import ru.tinkoff.edu.java.bot.telegramBot.Command;
import ru.tinkoff.edu.java.bot.telegramBot.service.TgBotService;
import ru.tinkoff.edu.java.scrapper.exceptions.BadRequestException;

@Service
public class StartCommand implements Command {

    private UserRepository repo;
    private TgBotService tgBotService;


    public StartCommand(UserRepository repo, TgBotService tgBotService) {
        this.repo = repo;
        this.tgBotService = tgBotService;
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


        return tgBotService.sendChatByIDToRegister(chatId)
                .map(response -> new SendMessage(chatId, "User: " + userFirstName + " is registered"))
                .onErrorResume(WebClientResponseException.class, ex -> {
                    if (ex.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                        return Mono.just(new SendMessage(chatId, "User: " + userFirstName + " is already registered"));
                    }
                    return Mono.just(new SendMessage(chatId, "Error occurred while processing your request"));
                }).blockLast();

                //System.out.println("Start command response"  );


//        if (repo.registerUser(chatId))
//            return new SendMessage(chatId, "User: " + userFirstName + " is registered");
//        else
//            return new SendMessage(chatId, "User: " + userFirstName + " is already registered");

        }
}