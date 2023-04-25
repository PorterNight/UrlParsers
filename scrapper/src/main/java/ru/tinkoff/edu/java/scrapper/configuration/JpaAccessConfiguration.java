package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.tinkoff.edu.java.scrapper.domain.LinkBaseService;
import ru.tinkoff.edu.java.scrapper.domain.LinkService;
import ru.tinkoff.edu.java.scrapper.domain.TgChatBaseService;
import ru.tinkoff.edu.java.scrapper.domain.TgChatService;
import ru.tinkoff.edu.java.scrapper.domain.jpa.repository.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.repository.JpaLinkChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.repository.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.service.JpaLinkBaseService;
import ru.tinkoff.edu.java.scrapper.domain.jpa.service.JpaLinkService;
import ru.tinkoff.edu.java.scrapper.domain.jpa.service.JpaTgChatBaseService;
import ru.tinkoff.edu.java.scrapper.domain.jpa.service.JpaTgChatService;

import javax.sql.DataSource;

@Configuration
@ConditionalOnProperty(prefix = "scrapper", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfiguration {

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public TgChatBaseService tgChatBaseService(JpaChatRepository jpaChatRepository) {
        return new JpaTgChatBaseService(jpaChatRepository);
    }

    @Bean
    public TgChatService jpaTgChatService(JpaChatRepository jpaChatRepository) {
        return new JpaTgChatService(jpaChatRepository);
    }

    @Bean
    public LinkBaseService jpaLinkBaseService(JpaLinkRepository jpaLinkRepository, JpaChatRepository jpaChatRepository, JpaLinkChatRepository jpaLinkChatRepository) {
        return new JpaLinkBaseService(jpaLinkRepository, jpaChatRepository, jpaLinkChatRepository);
    }

    @Bean
    public LinkService jpaLinkService(LinkBaseService linkBaseService, JpaChatRepository jpaChatRepository, JpaLinkRepository jpaLinkRepository, JpaLinkChatRepository jpaLinkChatRepository) {
        return new JpaLinkService(linkBaseService, jpaChatRepository, jpaLinkRepository, jpaLinkChatRepository);
    }
}