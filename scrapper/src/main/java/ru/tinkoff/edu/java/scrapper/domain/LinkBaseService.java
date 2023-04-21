package ru.tinkoff.edu.java.scrapper.domain;

import ru.tinkoff.edu.java.scrapper.domain.repository.ListLinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.ListLinkWithTimeRepository;

import java.net.URI;
import java.time.OffsetDateTime;

public interface LinkBaseService {

    void add(long tgChatId, URI url);
    void addTime(URI url, OffsetDateTime time);
    void remove(long tgChatId, URI url);
    ListLinkRepository findAll(long tgChatId);
    ListLinkWithTimeRepository findAllFilteredByTimeout(long timeout);

}
