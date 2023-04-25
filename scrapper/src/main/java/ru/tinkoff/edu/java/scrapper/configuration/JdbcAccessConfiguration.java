package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.tinkoff.edu.java.scrapper.domain.LinkBaseService;
import ru.tinkoff.edu.java.scrapper.domain.LinkService;
import ru.tinkoff.edu.java.scrapper.domain.TgChatBaseService;
import ru.tinkoff.edu.java.scrapper.domain.TgChatService;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.service.JdbcLinkBaseService;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.service.JdbcLinkService;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.service.JdbcTgChatBaseService;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.service.JdbcTgChatService;

import javax.sql.DataSource;

@Configuration
@ConditionalOnProperty(prefix = "scrapper", name = "database-access-type", havingValue = "jdbc")
@ComponentScan("ru.tinkoff.edu.java.scrapper.domain.jdbc.service")
 public class JdbcAccessConfiguration {

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public TgChatBaseService tgChatBaseService(JdbcTemplate jdbcTemplate) {
        return new JdbcTgChatBaseService(jdbcTemplate);
    }

    @Bean
    public TgChatService tgChatService(TgChatBaseService tgChatBaseService) {
        return new JdbcTgChatService(tgChatBaseService);
    }

    @Bean
    public LinkBaseService jdbcLinkBaseService(JdbcTemplate jdbcTemplate) {
        return new JdbcLinkBaseService(jdbcTemplate);
    }

    @Bean
    public LinkService jdbcLinkService(LinkBaseService linkBaseService) {
        return new JdbcLinkService(linkBaseService);
    }

}