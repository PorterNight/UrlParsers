package ru.tinkoff.edu.java.scrapper.domain;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.repository.JdbcTgChatRepository;

public interface TgChatBaseService {
    void add(long tgChatId);
    void remove(long tgChatId);
    JdbcTgChatRepository findAll();
}