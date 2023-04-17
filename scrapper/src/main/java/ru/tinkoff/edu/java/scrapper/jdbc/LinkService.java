package ru.tinkoff.edu.java.scrapper.jdbc;

import ru.tinkoff.edu.java.scrapper.jdbc.repository.JdbcLinkChatRepository;
import ru.tinkoff.edu.java.scrapper.jdbc.repository.JdbcLinkRepository;
import ru.tinkoff.edu.java.scrapper.jdbc.repository.JdbcListLinkRepository;

import java.net.URI;
import java.sql.SQLException;
import java.util.Collection;

public interface LinkService {
    JdbcLinkChatRepository add(long tgChatId, URI url) throws SQLException;
    JdbcLinkChatRepository remove(long tgChatId, URI url);
    JdbcListLinkRepository listAll(long tgChatId);
}