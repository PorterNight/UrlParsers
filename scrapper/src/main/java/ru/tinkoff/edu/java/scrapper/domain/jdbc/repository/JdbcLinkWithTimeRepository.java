package ru.tinkoff.edu.java.scrapper.domain.jdbc.repository;

import java.net.URI;
import java.time.OffsetDateTime;

public record JdbcLinkWithTimeRepository(URI url, OffsetDateTime newEventCreatedAt) {
}
