package ru.tinkoff.edu.java.scrapper.domain.jooq.repository;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import ru.tinkoff.edu.java.scrapper.domain.TgChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Chat;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.LinkChat;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.ChatRecord;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JooqTgChatRepository {// implements TgChatRepository {

    private final List<Long> tgChatIds;

    public JooqTgChatRepository(List<Long> tgChatIds) {
        this.tgChatIds = tgChatIds;
    }

    public List<Long> getTgChatIds() {
        return tgChatIds;
    }

    public int getLength() {
        return tgChatIds.size();
    }
}