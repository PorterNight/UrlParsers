
package ru.tinkoff.edu.java.bot.telegramBot;

import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.SendResponse;
import ru.tinkoff.edu.java.bot.telegramBot.interfaces.Bot;
import ru.tinkoff.edu.java.bot.telegramBot.interfaces.UserMessageProcessor;
import ru.tinkoff.edu.java.bot.telegramBot.interfaces.Command;

import java.io.IOException;
import java.util.List;

public class BotImpl implements Bot {

    private final TelegramBot bot;

    public BotImpl(String botToken) {
        bot = new TelegramBot(botToken);
    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {

    }

    @Override
    public int process(List<Update> updates) {

        for (Update update : updates) {
            if (update.message() != null) {
                long chatId = update.message().chat().id();
                String userFirstName = update.message().from().firstName();


                UserMessageProcessImpl userProc = new UserMessageProcessImpl();

                SendMessage sendMessage = userProc.process(update);


                bot.execute(sendMessage.replyMarkup(simpleKeyboard(userProc)), new Callback<SendMessage, SendResponse>() {
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
        }

        return UpdatesListener.CONFIRMED_UPDATES_ALL;

    }


    public void setUpUpdatesListener() {
        bot.setUpdatesListener( updates -> (this.process(updates)) );
    }

    @Override
    public void start() {
    }

    @Override
    public void close() {
    }


    private Keyboard simpleKeyboard(UserMessageProcessor proc) {

        String[] botCommandsArray = proc.commands().stream()
                .map(Command::command)
                .toArray(String[]::new);

        return new ReplyKeyboardMarkup(botCommandsArray).resizeKeyboard(true).selective(true);
    }

}

