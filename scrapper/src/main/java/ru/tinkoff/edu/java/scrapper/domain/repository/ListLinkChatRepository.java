package ru.tinkoff.edu.java.scrapper.domain.jdbc.repository;

public record JdbcListLinkChatRepository(JdbcLinkChatRepository[] linkChat, int size) {}