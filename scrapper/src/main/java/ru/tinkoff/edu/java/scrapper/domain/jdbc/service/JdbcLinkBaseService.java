package ru.tinkoff.edu.java.scrapper.domain.jdbc.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.domain.LinkBaseService;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.repository.JdbcLinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.repository.JdbcLinkWithTimeRepository;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.repository.JdbcListLinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.repository.JdbcListLinkWithTimeRepository;

import java.net.URI;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class JdbcLinkBaseService implements LinkBaseService {

    private final JdbcTemplate jdbcTemplate;

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
    public JdbcListLinkRepository findAll(long tgChatId) {

        String sql = "SELECT l.url FROM link l " +
                "JOIN link_chat lc ON l.id = lc.link_id " +
                "WHERE lc.chat_id = ?";

        RowMapper<URI> rowMapper = (rs, rowNum) -> URI.create(rs.getString("url"));

        PreparedStatementSetter preparedStatementSetter = preparedStatement -> {
            preparedStatement.setLong(1, tgChatId);
        };

        List<URI> urls = jdbcTemplate.query(sql, preparedStatementSetter, rowMapper);
        JdbcLinkRepository[] links = urls.stream()
                .map(JdbcLinkRepository::new)
                .toArray(JdbcLinkRepository[]::new);

        int length = links.length;

        return new JdbcListLinkRepository(links, length);
    }


    @Override
    public JdbcListLinkWithTimeRepository findAllFilteredByTimeout(long timeout) {

        OffsetDateTime time = OffsetDateTime.now().minusMinutes(timeout);

        List<JdbcLinkWithTimeRepository> linksWithTime = jdbcTemplate.query(
                "SELECT id, url, new_event_created_at FROM link WHERE updated_at < ?",
                (resultSet, rowNum) -> {
                    long linkId = resultSet.getLong("id");
                    URI url = URI.create(resultSet.getString("url"));
                    OffsetDateTime newEventCreatedAt = resultSet.getObject("new_event_created_at", OffsetDateTime.class);
                    List<Long> tgChatIds = findChatIdsByLinkId(linkId);
                    return new JdbcLinkWithTimeRepository(linkId, url, newEventCreatedAt, tgChatIds);
                }, time);

        return new JdbcListLinkWithTimeRepository(linksWithTime.toArray(new JdbcLinkWithTimeRepository[0]), linksWithTime.size());
    }

    private List<Long> findChatIdsByLinkId(long linkId) {
        return jdbcTemplate.query(
                "SELECT chat_id FROM link_chat WHERE link_id = ?",
                (resultSet, rowNum) -> resultSet.getLong("chat_id"),
                linkId);
    }
}

