package ru.tinkoff.edu.java.scrapper.jdbc.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.jdbc.repository.*;

import java.net.URI;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class JdbcLinkBaseService {

    private final JdbcTemplate jdbcTemplate;

    public JdbcLinkBaseService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(long tgChatId, URI url) {
        jdbcTemplate.update("INSERT INTO link(url) VALUES (?)", url.toString());

        long link_id = jdbcTemplate.queryForObject("SELECT id FROM link WHERE url = ?", Long.class, url.toString());

        jdbcTemplate.update("INSERT INTO link_chat(link_id, chat_id) VALUES (?, ?)", link_id, tgChatId);
    }

    public void addTime(URI url, OffsetDateTime time) {
        Timestamp timestamp = Timestamp.from(time.toInstant());
        jdbcTemplate.update("UPDATE link SET new_event_created_at = ? WHERE url = ?", timestamp, url.toString());
    }

    public void remove(long tgChatId, URI url) {

        long link_id = jdbcTemplate.queryForObject("SELECT id FROM link WHERE url = ?", Long.class, url.toString());

        jdbcTemplate.update("DELETE FROM link WHERE id = ?", link_id);
        jdbcTemplate.update("DELETE FROM link_chat WHERE link_id = ?", link_id);
    }

    public JdbcListLinkRepository findAll(long tgChatId) {

        String sql = "SELECT url FROM link";
        List<URI> urls = jdbcTemplate.queryForList(sql, URI.class);
        JdbcLinkRepository[] links = urls.stream()
                .map(JdbcLinkRepository::new)
                .toArray(JdbcLinkRepository[]::new);

        int length = links.length;

        return new JdbcListLinkRepository(links, length);
    }


    public JdbcListLinkWithTimeRepository findAllFilteredByTimeout(long timeout) {

        OffsetDateTime time = OffsetDateTime.now().minusMinutes(timeout);
        List<JdbcLinkWithTimeRepository> linksWithTime = jdbcTemplate.query(
                "SELECT url, new_event_created_at FROM link WHERE updated_at < ?",
                (resultSet, rowNum) -> new JdbcLinkWithTimeRepository(
                        URI.create(resultSet.getString("url")),
                        resultSet.getObject("new_event_created_at", OffsetDateTime.class)
                ), time);

        return new JdbcListLinkWithTimeRepository(linksWithTime.toArray(new JdbcLinkWithTimeRepository[0]), linksWithTime.size());
    }
}

