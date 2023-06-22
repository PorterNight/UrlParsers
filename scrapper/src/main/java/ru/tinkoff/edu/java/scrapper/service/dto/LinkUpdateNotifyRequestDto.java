package ru.tinkoff.edu.java.scrapper.service.dto;

import java.net.URI;
import java.util.List;

public record LinkUpdateNotifyRequestDto(
        long id,
        URI url,
        String description,
        List<Long> tgChatIds
) {}
