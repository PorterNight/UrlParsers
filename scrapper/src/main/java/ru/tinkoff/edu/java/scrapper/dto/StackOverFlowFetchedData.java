package ru.tinkoff.edu.java.scrapper.dto;

import java.time.OffsetDateTime;

public record StackOverFlowFetchedData(String link, String title, String[] tags, OffsetDateTime creation_date) {}
