package ru.tinkoff.edu.java.scrapper.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "scrapper", ignoreUnknownFields = false)
public record ScrapperConfig(@NotNull String test, AccessType databaseAccessType) {}
