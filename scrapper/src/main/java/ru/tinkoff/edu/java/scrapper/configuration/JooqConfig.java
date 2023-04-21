package ru.tinkoff.edu.java.scrapper.configuration;

import org.jooq.conf.RenderNameStyle;
import org.jooq.conf.Settings;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JooqConfig {

    @Bean
    Settings jooqSettings() {
        return new Settings().withRenderNameStyle(RenderNameStyle.AS_IS);
    }
}