package ru.tinkoff.edu.java.scrapper.domain;

public interface TgChatService {
    void register(long tgChatId);
    void unregister(long tgChatId);
}