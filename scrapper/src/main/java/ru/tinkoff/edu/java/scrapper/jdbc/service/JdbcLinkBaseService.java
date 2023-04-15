package ru.tinkoff.edu.java.scrapper.jdbc.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.jdbc.repository.JdbcLinkRepository;
import ru.tinkoff.edu.java.scrapper.jdbc.repository.JdbcTgChatRepository;

import java.net.URI;
import java.util.List;

@Service
public class JdbcLinkBaseService {

    private final JdbcTemplate jdbcTemplate;

    public JdbcLinkBaseService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(long tgChatId, URI url) {
        jdbcTemplate.update("INSERT INTO link(url) VALUES (?)", url);

        String sql = "SELECT id FROM link WHERE url = ?";
        Long linkId = jdbcTemplate.queryForObject(sql, Long.class, url);

        jdbcTemplate.update("INSERT INTO link_chat(link_id, chat_id) VALUES (?, ?)", linkId, tgChatId);
    }

    public void remove(long tgChatId, URI url) {

        String sql = "SELECT id FROM link WHERE url = ?";
        Long linkId = jdbcTemplate.queryForObject(sql, Long.class, url);

        jdbcTemplate.update("DELETE FROM link WHERE id = ?", linkId);
        jdbcTemplate.update("DELETE FROM link_chat WHERE link_id = ?", linkId);
    }

    public JdbcLinkRepository findAll(long tgChatId) {

        String sql = "SELECT url FROM link";
        List<URI> urls = jdbcTemplate.queryForList(sql, URI.class);
        URI[] url = urls.stream().toArray(URI[]::new);
        int length = url.length;

        System.out.println("jdbs findall: " + urls.get(0) + "length: " + length);

        return new JdbcLinkRepository(url, length);    }


    }
