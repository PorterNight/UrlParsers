package ru.tinkoff.edu.java.scrapper.domain.repository;

import java.net.URI;

public record LinkChatRepository(long chat_id, URI url) {}