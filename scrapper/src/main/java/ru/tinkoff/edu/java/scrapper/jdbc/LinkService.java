package ru.tinkoff.edu.java.scrapper.jdbc;

import ru.tinkoff.edu.java.scrapper.jdbc.repository.JdbcLinkRepository;

import java.net.URI;
import java.sql.SQLException;
import java.util.Collection;

public interface LinkService {
    LinkDao add(long tgChatId, URI url) throws SQLException;
    LinkDao remove(long tgChatId, URI url);
    JdbcLinkRepository listAll(long tgChatId);
}