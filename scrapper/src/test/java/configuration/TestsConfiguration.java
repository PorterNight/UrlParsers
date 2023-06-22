package configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.service.JdbcLinkBaseService;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.service.JdbcLinkService;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.service.JdbcTgChatBaseService;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.service.JdbcTgChatService;
import ru.tinkoff.edu.java.scrapper.domain.jpa.repository.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.repository.JpaLinkChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.repository.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.service.JpaLinkBaseService;
import ru.tinkoff.edu.java.scrapper.domain.jpa.service.JpaLinkService;
import ru.tinkoff.edu.java.scrapper.domain.jpa.service.JpaTgChatBaseService;
import ru.tinkoff.edu.java.scrapper.domain.jpa.service.JpaTgChatService;

import javax.sql.DataSource;

@TestConfiguration
@Import({IntegrationEnvironment.IntegrationEnvironmentConfig.class})
@ComponentScan("ru.tinkoff.edu.java.scrapper.domain.jpa.repository")
@EnableJpaRepositories("ru.tinkoff.edu.java.scrapper.domain.jpa.repository")
public class TestsConfiguration {

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public JdbcTgChatBaseService jdbcTgChatBaseService(JdbcTemplate jdbcTemplate) {
        return new JdbcTgChatBaseService(jdbcTemplate);
    }

    @Bean
    public JdbcTgChatService jdbcTgChatService(JdbcTgChatBaseService jdbcTgChatService) {
        return new JdbcTgChatService(jdbcTgChatService);
    }

    @Bean
    public JdbcLinkBaseService jdbcLinkBaseService(JdbcTemplate jdbcTemplate) {
        return new JdbcLinkBaseService(jdbcTemplate);
    }

    @Bean
    public JdbcLinkService jdbcLinkService(JdbcLinkBaseService jdbcLinkBaseService) {
        return new JdbcLinkService(jdbcLinkBaseService);
    }

    @Bean
    public JpaTgChatBaseService jpaTgChatBaseService(JpaChatRepository jpaChatRepository) {
        return new JpaTgChatBaseService(jpaChatRepository);
    }

    @Bean
    public JpaTgChatService jpaTgChatService(JpaTgChatBaseService jpaTgChatBaseService, JpaChatRepository jpaChatRepository) {
        return new JpaTgChatService(jpaTgChatBaseService, jpaChatRepository);
    }

    @Bean
    public JpaLinkBaseService jpaLinkBaseService(JpaLinkRepository jpaLinkRepository, JpaChatRepository jpaChatRepository, JpaLinkChatRepository jpaLinkChatRepository) {
        return new JpaLinkBaseService(jpaLinkRepository, jpaChatRepository, jpaLinkChatRepository);
    }

    @Bean
    public JpaLinkService jpaLinkService(JpaLinkBaseService jpaLinkBaseService, JpaChatRepository jpaChatRepository, JpaLinkRepository jpaLinkRepository, JpaLinkChatRepository jpaLinkChatRepository) {
        return new JpaLinkService(jpaLinkBaseService, jpaChatRepository, jpaLinkRepository, jpaLinkChatRepository);
    }
}