package ru.tinkoff.edu.java.scrapper.jdbc.repository;

public record JdbcListLinkChatRepository(JdbcLinkChatRepository[] linkChat, int size) {}