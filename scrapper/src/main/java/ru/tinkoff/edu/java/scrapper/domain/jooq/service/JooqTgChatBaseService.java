package ru.tinkoff.edu.java.scrapper.domain.jooq.service;

import org.jooq.DSLContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.tinkoff.edu.java.scrapper.domain.TgChatBaseService;
import ru.tinkoff.edu.java.scrapper.domain.repository.TgChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.LinkChat;

import java.util.List;


import static ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Chat.CHAT;


public class JooqTgChatBaseService implements TgChatBaseService {


    private final DSLContext dslContext;

    public JooqTgChatBaseService(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public void add(long tgChatId) {   //register new chat
        dslContext.insertInto(CHAT, CHAT.CHAT_ID)
                .values(tgChatId)
                .execute();
    }

    @Override
    public void remove(long tgChatId) {
        dslContext.deleteFrom(CHAT)
                .where(CHAT.CHAT_ID.eq( tgChatId))
                .execute();
        dslContext.deleteFrom(LinkChat.LINK_CHAT)
                .where(LinkChat.LINK_CHAT.CHAT_ID.eq(tgChatId))
                .execute();
    }

    @Override
    public TgChatRepository findAll() {

        List<Long> chatIds = dslContext.select(CHAT.CHAT_ID)
                .from(CHAT)
                .fetch(CHAT.CHAT_ID);

        Long[] chatIdArray = chatIds.toArray(new Long[0]);
        int length = chatIds.size();

        return new TgChatRepository(chatIdArray, length);
    }
}
