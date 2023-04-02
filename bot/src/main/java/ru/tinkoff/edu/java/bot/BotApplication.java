package ru.tinkoff.edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import ru.tinkoff.edu.java.bot.configuration.BotConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.tinkoff.edu.java.bot.telegramBot.BotImpl;

import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties(BotConfig.class)
public class BotApplication {
    public static void main(String[] args) {
        var ctx = SpringApplication.run(BotApplication.class, args);
        BotConfig config = ctx.getBean(BotConfig.class);
        System.out.println(config);


        String token = "6085626394:AAHTSJEb7K9rAPM-ZmseAv3fbGvo7oElWCI";
        //String token = config.token();

        try (BotImpl bot = new BotImpl(token)) {
            //bot.start();
            bot.setUpUpdatesListener();
        }

    }
}

