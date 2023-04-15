package ru.tinkoff.edu.java.scrapper.jdbc;

public interface TgChatService {
    void register(long tgChatId);
    void unregister(long tgChatId);
}