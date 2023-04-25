package ru.tinkoff.edu.java.scrapper.domain.jdbc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.LinkBaseService;
import ru.tinkoff.edu.java.scrapper.domain.LinkService;
import ru.tinkoff.edu.java.scrapper.domain.TgChatBaseService;
import ru.tinkoff.edu.java.scrapper.domain.repository.LinkChatRepository;
import ru.tinkoff.edu.java.scrapper.exceptions.ScrapperControllerException;
import ru.tinkoff.edu.java.scrapper.domain.repository.ListLinkRepository;

import java.net.URI;
import java.util.Arrays;

public class JdbcLinkService implements LinkService {

    private LinkBaseService linkBaseService;

    public JdbcLinkService(LinkBaseService linkBaseService) {
        this.linkBaseService = linkBaseService;

    }

    @Transactional
    @Override
    public LinkChatRepository add(long tgChatId, URI url) {

        ListLinkRepository res = linkBaseService.findAll(tgChatId);

        // check if url is already in table
        if (res.size() > 0 && Arrays.stream(res.links()).anyMatch(link -> link.url().equals(url))) {
            System.out.println("JdbcLinkService: url already registered " + url);
            throw new ScrapperControllerException("Ссылка уже присутствует: " + url, 400);

        } else {  // else add url with chatID to table

            System.out.println("JdbcLinkService: adding url" + url.toString());
            linkBaseService.add(tgChatId, url);

        }
        return new LinkChatRepository(tgChatId, url);
    }

    @Transactional
    @Override
    public LinkChatRepository remove(long tgChatId, URI url) {

        ListLinkRepository res = linkBaseService.findAll(tgChatId);

        // check if url is already in table
        if (res.size() > 0 && Arrays.stream(res.links()).anyMatch(link -> link.url().equals(url))) {

            System.out.println("JdbcLinkService: removing url" + url.toString());
            linkBaseService.remove(tgChatId, url);

        } else {

            System.out.println("JdbcLinkService: url is absent " + url);
            throw new ScrapperControllerException("Url отсутсвует для удаления: " + url, 400);

        }
        return new LinkChatRepository(tgChatId, url);
    }

    @Override
    public ListLinkRepository listAll(long tgChatId) {
        return linkBaseService.findAll(tgChatId);
    }
}
