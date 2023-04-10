package ru.tinkoff.edu.java.scrapper.dao;

import java.net.URI;
import java.sql.SQLException;
import java.util.Collection;

public interface LinkService {
    LinkDao add(long tgChatId, URI url) throws SQLException;
    LinkDao remove(long tgChatId, URI url);
    Collection<LinkDao> listAll(long tgChatId);
}