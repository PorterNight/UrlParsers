package ru.tinkoff.edu.java.scrapper.service.dto;

import java.net.URI;

public record RemoveLinkDto(Long tgChatId, URI link) {}