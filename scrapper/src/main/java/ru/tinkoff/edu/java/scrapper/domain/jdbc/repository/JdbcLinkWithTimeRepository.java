package ru.tinkoff.edu.java.scrapper.domain.jdbc.repository;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

public record JdbcLinkWithTimeRepository(long url_id, URI url, OffsetDateTime newEventCreatedAt, List<Long> tgChatIds) {
}
