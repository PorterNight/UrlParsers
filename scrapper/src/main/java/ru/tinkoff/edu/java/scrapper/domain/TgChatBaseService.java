package ru.tinkoff.edu.java.scrapper.domain;
import ru.tinkoff.edu.java.scrapper.domain.repository.TgChatRepository;

public interface TgChatBaseService {
    void add(long tgChatId);
    void remove(long tgChatId);
    TgChatRepository findAll();
}