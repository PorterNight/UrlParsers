package ru.tinkoff.edu.java.scrapper.domain.jdbc.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.domain.TgChatBaseService;
import ru.tinkoff.edu.java.scrapper.domain.repository.TgChatRepository;

import java.util.List;

@Service
public class JdbcTgChatBaseService implements TgChatBaseService {

    private final JdbcTemplate jdbcTemplate;
    public JdbcTgChatBaseService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(long tgChatId) {   //register new chat
        jdbcTemplate.update("INSERT INTO chat(chat_id) VALUES (?)", tgChatId);
    }

    @Override
    public void remove(long tgChatId) {
        jdbcTemplate.update("DELETE FROM chat WHERE chat_id=?", tgChatId);
        jdbcTemplate.update("DELETE FROM link_chat WHERE chat_id=?", tgChatId);
    }

    @Override
    public TgChatRepository findAll() {
        String sql = "SELECT chat_id FROM chat";
        List<Long> chatIds = jdbcTemplate.queryForList(sql, Long.class);
        Long[] chatId = chatIds.stream().toArray(Long[]::new);
        int length = chatId.length;

        return new TgChatRepository(chatId, length);
    }
}
