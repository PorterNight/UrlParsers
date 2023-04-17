package ru.tinkoff.edu.java.scrapper.domain.jdbc.repository;

public class JdbcTgChatRepository {

    private final Long[] tgChatId;
    private final int length;


    public JdbcTgChatRepository(Long[] tgChatId, int length) {
        this.tgChatId = tgChatId;
        this.length = length;
    }

    public Long[] getTgChatId() {
        return tgChatId;
    }

    public int getLength() {
        return length;
    }


}
