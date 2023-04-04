package ru.tinkoff.edu.java.scrapper.service.dto;

import java.net.URI;

public record AddLinkDto(Long tgChatId, URI link) {}