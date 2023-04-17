package ru.tinkoff.edu.java.scrapper.domain;

import ru.tinkoff.edu.java.scrapper.domain.jdbc.repository.JdbcLinkChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.repository.JdbcListLinkRepository;

import java.net.URI;
import java.sql.SQLException;

public interface LinkService {
    JdbcLinkChatRepository add(long tgChatId, URI url);
    JdbcLinkChatRepository remove(long tgChatId, URI url);
    JdbcListLinkRepository listAll(long tgChatId);
}