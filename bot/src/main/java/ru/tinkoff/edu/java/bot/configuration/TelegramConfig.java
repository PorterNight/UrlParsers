
package ru.tinkoff.edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.telegramBot.impl.BotImpl;
import ru.tinkoff.edu.java.bot.telegramBot.Bot;
import ru.tinkoff.edu.java.bot.telegramBot.UserMessageProcessor;

@Configuration
public class TelegramConfig {

    private final String token;

    //public TelegramConfig(@Value("${bot.token}") String token) {
    public TelegramConfig(@Value("${TGTOKEN}") String token) {
        this.token = token;
    }
    @Bean
    public Bot getBot(UserMessageProcessor processor){ //, BotConfig config) {
        TelegramBot telegramBot = new TelegramBot(token);
        BotImpl bot = new BotImpl(processor, telegramBot);
        telegramBot.setUpdatesListener(bot);
        return bot;
    }
}