package ru.tinkoff.edu.java.scrapper.domain.jdbc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.LinkService;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.repository.JdbcLinkChatRepository;
import ru.tinkoff.edu.java.scrapper.exceptions.ScrapperControllerException;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.repository.JdbcListLinkRepository;

import java.net.URI;
import java.util.Arrays;

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
    public JdbcLinkChatRepository add(long tgChatId, URI url) {

        JdbcListLinkRepository res = jdbcLinkBaseService.findAll(tgChatId);

        // check if url is already in table
        if (res.size() > 0 && Arrays.stream(res.links()).anyMatch(link -> link.url().equals(url))) {
            System.out.println("JdbcLinkService: url already registered " + url);
            throw new ScrapperControllerException("Ссылка уже присутствует: " + url, 400);

        } else {  // else add url with chatID to table

            System.out.println("JdbcLinkService: adding url" + url.toString());
            jdbcLinkBaseService.add(tgChatId, url);

        }
        return new JdbcLinkChatRepository(tgChatId, url);
    }

    @Transactional
    @Override
    public JdbcLinkChatRepository remove(long tgChatId, URI url) {

        JdbcListLinkRepository res = jdbcLinkBaseService.findAll(tgChatId);

        // check if url is already in table
        if (res.size() > 0 && Arrays.stream(res.links()).anyMatch(link -> link.url().equals(url))) {

            System.out.println("JdbcLinkService: removing url" + url.toString());
            jdbcLinkBaseService.remove(tgChatId, url);

        } else {

            System.out.println("JdbcLinkService: url is absent " + url);
            throw new ScrapperControllerException("Url отсутсвует для удаления: " + url, 400);

        }
        return new JdbcLinkChatRepository(tgChatId, url);
    }

    @Override
    public JdbcListLinkRepository listAll(long tgChatId) {
        return jdbcLinkBaseService.findAll(tgChatId);
    }
}
