package ru.tinkoff.edu.java.scrapper.service.dto;

public record StackOverflowItemsDto(String[] tags, Long view_count, String title, String link, long creation_date) {}

