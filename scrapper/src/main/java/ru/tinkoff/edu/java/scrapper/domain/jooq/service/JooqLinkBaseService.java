package ru.tinkoff.edu.java.scrapper.domain.jooq.service;

import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.domain.LinkBaseService;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.LinkChat;
import ru.tinkoff.edu.java.scrapper.domain.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.LinkWithTimeRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.ListLinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.ListLinkWithTimeRepository;

import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import static ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Link.LINK;
import static ru.tinkoff.edu.java.scrapper.domain.jooq.tables.LinkChat.LINK_CHAT;

public class JooqLinkBaseService implements LinkBaseService {


    private final DSLContext dslContext;

    public JooqLinkBaseService(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public void add(long tgChatId, URI url) {
        dslContext.insertInto(LINK, LINK.URL)
                .values(url.toString())
                .execute();

        long linkId = dslContext.select(LINK.ID)
                .from(LINK)
                .where(LINK.URL.eq(url.toString()))
                .fetchOne(LINK.ID);

        dslContext.insertInto(LINK_CHAT, LINK_CHAT.LINK_ID, LINK_CHAT.CHAT_ID)
                .values(linkId, tgChatId)
                .execute();
    }

    @Override
    public void addTime(URI url, OffsetDateTime time) {

        OffsetDateTime timeval = time.toInstant().atZone(ZoneId.systemDefault()).toOffsetDateTime();

        dslContext.update(LINK)
                .set(LINK.NEW_EVENT_CREATED_AT, timeval)
                .where(LINK.URL.eq(url.toString()))
                .execute();
    }

    @Override
    public void remove(long tgChatId, URI url) {
        long linkId = dslContext.select(LINK.ID)
                .from(LINK)
                .where(LINK.URL.eq(url.toString()))
                .fetchOne(LINK.ID);

        dslContext.deleteFrom(LINK_CHAT)
                .where(LINK_CHAT.LINK_ID.eq(linkId))
                .execute();

        dslContext.deleteFrom(LINK)
                .where(LINK.ID.eq(linkId))
                .execute();
    }

    @Override
    public ListLinkRepository findAll(long tgChatId) {
        List<URI> urls = dslContext.select(LINK.URL)
                .from(LINK)
                .join(LINK_CHAT)
                .on(LINK.ID.eq(LINK_CHAT.LINK_ID))
                .where(LINK_CHAT.CHAT_ID.eq(tgChatId))
                .fetchInto(String.class)
                .stream()
                .map(URI::create)
                .collect(Collectors.toList());

        LinkRepository[] links = urls.stream()
                .map(LinkRepository::new)
                .toArray(LinkRepository[]::new);

        int length = links.length;

        return new ListLinkRepository(links, length);
    }

    @Override
    public ListLinkWithTimeRepository findAllFilteredByTimeout(long timeout) {
        OffsetDateTime time = OffsetDateTime.now().minusMinutes(timeout);

        List<LinkWithTimeRepository> linksWithTime = dslContext.select(LINK.ID, LINK.URL, LINK.NEW_EVENT_CREATED_AT)
                .from(LINK)
                .where(LINK.UPDATED_AT.lt(time))
                .fetch()
                .map(record -> {
                    long linkId = record.get(LINK.ID);
                    URI url = URI.create(record.get(LINK.URL));
                    OffsetDateTime newEventCreatedAt = record.get(LINK.NEW_EVENT_CREATED_AT);
                    List<Long> tgChatIds = findChatIdsByLinkId(linkId);
                    return new LinkWithTimeRepository(linkId, url, newEventCreatedAt, tgChatIds);
                });

        return new ListLinkWithTimeRepository(linksWithTime.toArray(new LinkWithTimeRepository[0]), linksWithTime.size());
    }

    private List<Long> findChatIdsByLinkId(long linkId) {
        LinkChat lc = LinkChat.LINK_CHAT;
        List<Long> chatIds = dslContext.select(lc.CHAT_ID)
                .from(lc)
                .where(lc.LINK_ID.eq(linkId))
                .fetchInto(Long.class);
        return chatIds;
    }
}