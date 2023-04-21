package ru.tinkoff.edu.java.scrapper.domain;

import ru.tinkoff.edu.java.scrapper.domain.repository.LinkChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.ListLinkRepository;

import java.net.URI;

public interface LinkService {
    LinkChatRepository add(long tgChatId, URI url);
    LinkChatRepository remove(long tgChatId, URI url);
    ListLinkRepository listAll(long tgChatId);
}