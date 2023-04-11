package ru.tinkoff.edu.java.bot.telegramBot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.telegramBot.Command;
import ru.tinkoff.edu.java.bot.telegramBot.service.TgBotService;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class ListCommand implements Command {

    private TgBotService tgBotService;

    public ListCommand(TgBotService tgBotService) {
        this.tgBotService = tgBotService;
    }

    public String command() {
        return BotCommand.LIST.getCommand();
    }

    @Override
    public boolean supports(Update update) {
        return BotCommand.LIST.getCommand().equals(update.message().text());
    }


    @Override
    public SendMessage handle(Update update) {

        long chatId = update.message().chat().id();
        ListLinksResponse res = tgBotService.sendReqToScrapperToGetListOfLinks(chatId);

        if (res.links() != null && res.size() != 0) {
            String trackedLinks = Arrays.stream(res.links())
                    .map(linkResponse -> linkResponse.url().toString())
                    .collect(Collectors.joining("\n"));

            return new SendMessage(chatId, "tracked links: \n" + trackedLinks);
        } else {
            return new SendMessage(chatId, "no tracked links");
        }
    }
}