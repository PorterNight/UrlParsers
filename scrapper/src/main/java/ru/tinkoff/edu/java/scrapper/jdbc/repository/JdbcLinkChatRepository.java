package ru.tinkoff.edu.java.scrapper.jdbc.repository;

import java.net.URI;

public record JdbcLinkChatRepository(long chat_id, URI url) {}