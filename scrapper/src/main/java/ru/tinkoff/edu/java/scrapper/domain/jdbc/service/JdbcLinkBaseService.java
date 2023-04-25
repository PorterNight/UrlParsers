package ru.tinkoff.edu.java.scrapper.domain.jdbc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.domain.LinkBaseService;
import ru.tinkoff.edu.java.scrapper.domain.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.LinkWithTimeRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.ListLinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.ListLinkWithTimeRepository;

import java.net.URI;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.List;

public class JdbcLinkBaseService implements LinkBaseService {


    private JdbcTemplate jdbcTemplate;

    public JdbcLinkBaseService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(long tgChatId, URI url) {

        jdbcTemplate.update("INSERT INTO link(url) VALUES (?)", url.toString());
        long link_id = jdbcTemplate.queryForObject("SELECT id FROM link WHERE url = ?", Long.class, url.toString());
        jdbcTemplate.update("INSERT INTO link_chat(link_id, chat_id) VALUES (?, ?)", link_id, tgChatId);

    }

    @Override
    public void addTime(URI url, OffsetDateTime time) {

        Timestamp timestamp = Timestamp.from(time.toInstant());
        jdbcTemplate.update("UPDATE link SET new_event_created_at = ? WHERE url = ?", timestamp, url.toString());

    }

    @Override
    public void remove(long tgChatId, URI url) {

        long link_id = jdbcTemplate.queryForObject("SELECT id FROM link WHERE url = ?", Long.class, url.toString());

        jdbcTemplate.update("DELETE FROM link WHERE id = ?", link_id);
        jdbcTemplate.update("DELETE FROM link_chat WHERE link_id = ?", link_id);
    }

    @Override
    public ListLinkRepository findAll(long tgChatId) {

        String sql = "SELECT l.url FROM link l " +
                "JOIN link_chat lc ON l.id = lc.link_id " +
                "WHERE lc.chat_id = ?";

        RowMapper<URI> rowMapper = (rs, rowNum) -> URI.create(rs.getString("url"));

        PreparedStatementSetter preparedStatementSetter = preparedStatement -> {
            preparedStatement.setLong(1, tgChatId);
        };

        List<URI> urls = jdbcTemplate.query(sql, preparedStatementSetter, rowMapper);
        LinkRepository[] links = urls.stream()
                .map(LinkRepository::new)
                .toArray(LinkRepository[]::new);

        int length = links.length;

        return new ListLinkRepository(links, length);
    }


    @Override
    public ListLinkWithTimeRepository findAllFilteredByTimeout(long timeout) {

        OffsetDateTime time = OffsetDateTime.now().minusMinutes(timeout);

        List<LinkWithTimeRepository> linksWithTime = jdbcTemplate.query(
                "SELECT id, url, new_event_created_at FROM link WHERE updated_at < ?",
                (resultSet, rowNum) -> {
                    long linkId = resultSet.getLong("id");
                    URI url = URI.create(resultSet.getString("url"));
                    OffsetDateTime newEventCreatedAt = resultSet.getObject("new_event_created_at", OffsetDateTime.class);
                    List<Long> tgChatIds = findChatIdsByLinkId(linkId);
                    return new LinkWithTimeRepository(linkId, url, newEventCreatedAt, tgChatIds);
                }, time);

        return new ListLinkWithTimeRepository(linksWithTime.toArray(new LinkWithTimeRepository[0]), linksWithTime.size());
    }

    private List<Long> findChatIdsByLinkId(long linkId) {
        return jdbcTemplate.query(
                "SELECT chat_id FROM link_chat WHERE link_id = ?",
                (resultSet, rowNum) -> resultSet.getLong("chat_id"),
                linkId);
    }
}

