package ru.tinkoff.edu.java.scrapper.jdbc.repository;

public record JdbcListLinkRepository(JdbcLinkRepository[] links, int size) {
}
