package ru.tinkoff.edu.java.scrapper.domain;

import ru.tinkoff.edu.java.scrapper.domain.jdbc.repository.JdbcListLinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.repository.JdbcListLinkWithTimeRepository;

import java.net.URI;
import java.time.OffsetDateTime;

public interface LinkBaseService {

    void add(long tgChatId, URI url);
    void addTime(URI url, OffsetDateTime time);
    void remove(long tgChatId, URI url);
    JdbcListLinkRepository findAll(long tgChatId);
    JdbcListLinkWithTimeRepository findAllFilteredByTimeout(long timeout);

}
