
package ru.tinkoff.edu.java.bot.telegramBot.impl;

import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.telegramBot.Bot;
import ru.tinkoff.edu.java.bot.telegramBot.UserMessageProcessor;
import ru.tinkoff.edu.java.bot.telegramBot.commands.BotCommand;

import java.io.IOException;
import java.util.List;

@Service
public class BotImpl implements Bot {

    private final UserMessageProcessor userProc;

    private final TelegramBot bot;


    public BotImpl(UserMessageProcessor userProc, TelegramBot bot) {
        this.userProc = userProc;
        this.bot = bot;
    }

    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            if (update.message() != null) {
                SendMessage sendMessage = userProc.process(update);
                execute(sendMessage.replyMarkup(simpleKeyboard()));
            }
        }

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void execute(SendMessage sendMessage) {
        bot.execute(sendMessage, new Callback<SendMessage, SendResponse>() {
            @Override
            public void onResponse(SendMessage request, SendResponse response) {
                System.out.println("Message sent: " + response.message().text());
            }

            @Override
            public void onFailure(SendMessage request, IOException e) {
                System.out.println("Failed to send message: " + e.getMessage());
            }
        });
    }

    public void sendUpdateLinkInfo(long chatId, String info) {
        SendMessage sendMessage = new SendMessage(chatId, info).replyMarkup(simpleKeyboard());
        bot.execute(sendMessage);
    }

    private Keyboard simpleKeyboard() {

        String[] botCommandsArrayRow0 = {BotCommand.START.getCommand(), BotCommand.HELP.getCommand(), BotCommand.LIST.getCommand()};
        String[] botCommandsArrayRow1 = {BotCommand.TRACK.getCommand(), BotCommand.UNTRACK.getCommand()};

        return new ReplyKeyboardMarkup(botCommandsArrayRow0).addRow(botCommandsArrayRow1).resizeKeyboard(true).selective(false);
    }

    @Override
    public void start() {
    }

    @Override
    public void close() {

    }

}

