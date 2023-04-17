package ru.tinkoff.edu.java.scrapper.jdbc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.jdbc.repository.JdbcTgChatRepository;

import java.util.List;

@Service
public class JdbcTgChatBaseService {


    private final JdbcTemplate jdbcTemplate;

    public JdbcTgChatBaseService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(long tgChatId) {
        jdbcTemplate.update("INSERT INTO chat(chat_id) VALUES (?)", tgChatId);
    }

    public void remove(long tgChatId) {
        jdbcTemplate.update("DELETE FROM chat WHERE chat_id=?", tgChatId);
    }

    public JdbcTgChatRepository findAll() {
        String sql = "SELECT chat_id FROM chat";
        List<Long> chatIds = jdbcTemplate.queryForList(sql, Long.class);
        Long[] chatId = chatIds.stream().toArray(Long[]::new);
        int length = chatId.length;

        return new JdbcTgChatRepository(chatId, length);
    }
}
