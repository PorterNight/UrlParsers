package ru.tinkoff.edu.java.scrapper.jdbc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.exceptions.ScrapperControllerException;
import ru.tinkoff.edu.java.scrapper.jdbc.LinkDao;
import ru.tinkoff.edu.java.scrapper.jdbc.LinkService;
import ru.tinkoff.edu.java.scrapper.jdbc.repository.JdbcLinkRepository;

import java.net.URI;
import java.util.Arrays;
import java.util.Collection;

@Service
public class JdbcLinkService implements LinkService {

    private final JdbcLinkBaseService jdbcLinkBaseService;
    private final JdbcTgChatBaseService jdbcTgChatBaseService;

    @Autowired
    public JdbcLinkService(JdbcLinkBaseService jdbcLinkBaseService, JdbcTemplate jdbcTemplate) {
        this.jdbcLinkBaseService = jdbcLinkBaseService;
        this.jdbcTgChatBaseService = new JdbcTgChatBaseService(jdbcTemplate);
    }

    @Transactional
    @Override
    public LinkDao add(long tgChatId, URI url) {

        // first check if url is already in table
        JdbcLinkRepository res = jdbcLinkBaseService.findAll(tgChatId);

        if (res.getLength() > 0 && Arrays.stream(res.getUrls()).anyMatch(id -> id.equals(tgChatId))) {

            System.out.println("JdbcLinkService: url already registered " + url.toString());
            throw new ScrapperControllerException("Ссылка уже присутствует: " + url, 400);

        } else {  // else add url with chatID to table

            System.out.println("JdbcLinkService: adding url" + url.toString());
            jdbcLinkBaseService.add(tgChatId, url);

        }
        return new LinkDao(tgChatId, url);
    }

    @Override
    public LinkDao remove(long tgChatId, URI url) {

        JdbcLinkRepository res = jdbcLinkBaseService.findAll(tgChatId);

        if (res.getLength() > 0 && Arrays.stream(res.getUrls()).anyMatch(id -> id.equals(tgChatId))) {

            System.out.println("JdbcLinkService: url is absent " + url.toString());
            throw new ScrapperControllerException("Url отсутсвует для удаления: " + url, 400);

        } else {

            System.out.println("JdbcLinkService: removing url" + url.toString());
            jdbcLinkBaseService.remove(tgChatId, url);

        }
        return new LinkDao(tgChatId, url);
    }

    @Override
    public JdbcLinkRepository listAll(long tgChatId) {
        return jdbcLinkBaseService.findAll(tgChatId);
    }
}
