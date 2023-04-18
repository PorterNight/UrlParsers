package ru.tinkoff.edu.java.scrapper.domain.jdbc.repository;

public record JdbcListLinkRepository(JdbcLinkRepository[] links, int size) {
}
