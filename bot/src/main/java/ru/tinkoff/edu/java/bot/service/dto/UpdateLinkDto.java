package ru.tinkoff.edu.java.bot.service.dto;

import java.net.URI;
import java.util.List;

public record UpdateLinkDto(
        Long id,
        URI url,
        String description,
        List<Long> tgChatIds) {}
