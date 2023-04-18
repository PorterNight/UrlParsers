package ru.tinkoff.edu.java.scrapper.domain.jooq.impl;

import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Result;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.domain.TgChatBaseService;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.repository.JooqTgChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Chat;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Link;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.LinkChat;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.ChatRecord;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.LinkChatRecord;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JooqTgChatBaseService implements TgChatBaseService {

    private final DSLContext dslContext;

    public JooqTgChatBaseService(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public void add(long tgChatId) {   //register new chat
        dslContext.insertInto(Chat.CHAT, Chat.CHAT.CHAT_ID)
                .values(tgChatId)
                .execute();
    }

    @Override
    public void remove(long tgChatId) {
        dslContext.deleteFrom(Chat.CHAT)
                .where(Chat.CHAT.CHAT_ID.eq( tgChatId))
                .execute();
        dslContext.deleteFrom(LinkChat.LINK_CHAT)
                .where(LinkChat.LINK_CHAT.CHAT_ID.eq(tgChatId))
                .execute();
    }

    @Override
    public JooqTgChatRepository findAll() {
        Result<Record1<Long>> result = dslContext.select(Chat.CHAT.CHAT_ID)
                .from(Chat.CHAT)
                .fetch();

        List<Long> chatIds = result.stream()
                .map(record -> record.get(Chat.CHAT.CHAT_ID))
                .collect(Collectors.toList());

        return new JooqTgChatRepository(chatIds.toArray(Integer[]::new), chatIds.size());
    }
}
