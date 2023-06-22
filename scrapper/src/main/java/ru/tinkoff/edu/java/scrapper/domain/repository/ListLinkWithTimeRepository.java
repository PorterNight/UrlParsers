package ru.tinkoff.edu.java.scrapper.domain.repository;

public record ListLinkWithTimeRepository(LinkWithTimeRepository[] linksWithTime, int size) {
}
