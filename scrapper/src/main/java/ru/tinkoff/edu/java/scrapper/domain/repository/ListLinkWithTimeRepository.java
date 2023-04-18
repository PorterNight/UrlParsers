package ru.tinkoff.edu.java.scrapper.domain.jdbc.repository;

public record JdbcListLinkWithTimeRepository(JdbcLinkWithTimeRepository[] linksWithTime, int size) {
}
