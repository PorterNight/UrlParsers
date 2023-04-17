package ru.tinkoff.edu.java.scrapper.jdbc.repository;

public record JdbcListLinkWithTimeRepository(JdbcLinkWithTimeRepository[] linksWithTime, int size) {
}
