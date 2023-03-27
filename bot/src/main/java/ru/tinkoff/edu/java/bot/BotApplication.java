package ru.tinkoff.edu.java.bot;

import ru.tinkoff.edu.java.bot.configuration.BotConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(BotConfig.class)
public class BotApplication {
    public static void main(String[] args) {
        var ctx = SpringApplication.run(BotApplication.class, args);
        BotConfig config = ctx.getBean(BotConfig.class);
        System.out.println(config);
    }
}
