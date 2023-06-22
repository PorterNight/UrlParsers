package ru.tinkoff.edu.java.scrapper.domain.jpa.service;

import ru.tinkoff.edu.java.scrapper.domain.LinkBaseService;
import ru.tinkoff.edu.java.scrapper.domain.LinkService;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entity.Chat;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entity.Link;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entity.LinkChat;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entity.LinkChatId;
import ru.tinkoff.edu.java.scrapper.domain.jpa.repository.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.repository.JpaLinkChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.repository.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.LinkChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.ListLinkRepository;
import ru.tinkoff.edu.java.scrapper.exceptions.ScrapperControllerException;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

public class JpaLinkService implements LinkService {

    private final LinkBaseService linkBaseService;

    private final JpaChatRepository jpaChatRepository;

    private final JpaLinkRepository jpaLinkRepository;

    private final JpaLinkChatRepository jpaLinkChatRepository;

    public JpaLinkService(LinkBaseService linkBaseService, JpaChatRepository jpaChatRepository, JpaLinkRepository jpaLinkRepository, JpaLinkChatRepository jpaLinkChatRepository) {
        this.linkBaseService = linkBaseService;
        this.jpaChatRepository = jpaChatRepository;
        this.jpaLinkRepository = jpaLinkRepository;
        this.jpaLinkChatRepository = jpaLinkChatRepository;
    }


    @Override
    public LinkChatRepository add(long tgChatId, URI url) {

        // check if chat is registered
        jpaChatRepository.findById(tgChatId).orElseThrow(() -> new ScrapperControllerException("Chat not found: " + tgChatId, 404));

        // check if url is registered with tgChatId, then throw exception
        Link link = jpaLinkRepository.findByUrl(url.toString()).orElse(null);
        if (link != null) {
            LinkChatId linkChatId = new LinkChatId();
            linkChatId.setChatId(tgChatId);
            linkChatId.setLinkId(link.getId());
            if (jpaLinkChatRepository.findById(linkChatId).isPresent()) {
                throw new ScrapperControllerException("URL уже присутствует: " + url, 400);
            } else linkBaseService.add(tgChatId, url);

        } else {  // write new url related to tgChatId
            linkBaseService.add(tgChatId, url);
        }

        return new LinkChatRepository(tgChatId, url);
    }

    @Override
    public LinkChatRepository remove(long tgChatId, URI url) {

        jpaChatRepository.findById(tgChatId).orElseThrow(() -> new ScrapperControllerException("Chat not found: " + tgChatId, 404));
        jpaLinkRepository.findByUrl(url.toString()).orElseThrow(() -> new ScrapperControllerException("Url отсутсвует для удаления: " + url, 400));

        linkBaseService.remove(tgChatId, url);

        return new LinkChatRepository(tgChatId, url);
    }

    @Override
    public ListLinkRepository listAll(long tgChatId) {
        return linkBaseService.findAll(tgChatId);
    }
}
