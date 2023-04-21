package ru.tinkoff.edu.java.scrapper.domain.repository;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

public record LinkWithTimeRepository(long url_id, URI url, OffsetDateTime newEventCreatedAt, List<Long> tgChatIds) {
}
