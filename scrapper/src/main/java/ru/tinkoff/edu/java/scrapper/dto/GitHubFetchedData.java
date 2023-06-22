package ru.tinkoff.edu.java.scrapper.dto;

import java.time.OffsetDateTime;

public record GitHubFetchedData(long id, String type, OffsetDateTime created_at) {}

