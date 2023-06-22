package ru.tinkoff.edu.java.scrapper.domain.jpa.service;

import ru.tinkoff.edu.java.scrapper.domain.LinkBaseService;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entity.Chat;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entity.Link;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entity.LinkChat;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entity.LinkChatId;
import ru.tinkoff.edu.java.scrapper.domain.jpa.repository.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.repository.JpaLinkChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.repository.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.LinkWithTimeRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.ListLinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.ListLinkWithTimeRepository;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class JpaLinkBaseService implements LinkBaseService {

    private final JpaLinkRepository jpaLinkRepository;
    private final JpaChatRepository jpaChatRepository;
    private final JpaLinkChatRepository jpaLinkChatRepository;

    public JpaLinkBaseService(JpaLinkRepository jpaLinkRepository, JpaChatRepository jpaChatRepository, JpaLinkChatRepository jpaLinkChatRepository) {
        this.jpaLinkRepository = jpaLinkRepository;
        this.jpaChatRepository = jpaChatRepository;
        this.jpaLinkChatRepository = jpaLinkChatRepository;
    }

    @Override
    public void add(long tgChatId, URI url) {

        Link res = jpaLinkRepository.findByUrl(url.toString()).orElse(null);
        Link savedLink;

        if (res == null) {
            Link link = new Link();
            link.setUrl(url.toString());
            link.setUpdatedAt(OffsetDateTime.now());
            savedLink = jpaLinkRepository.save(link);
        } else
            savedLink = res;


        Chat savedChat = jpaChatRepository.findById(tgChatId).get();

        LinkChat linkChat = new LinkChat();
        // Set the LinkChatId value :
        LinkChatId linkChatId = new LinkChatId();
        linkChatId.setLinkId(savedLink.getId());
        linkChatId.setChatId(savedChat.getChatId());

        linkChat.setId(linkChatId);
        linkChat.setLink(savedLink);
        linkChat.setChat(savedChat);
        jpaLinkChatRepository.save(linkChat);
    }

    @Override
    public void addTime(URI url, OffsetDateTime time) {
        Link link = jpaLinkRepository.findByUrl(url.toString()).get();
        link.setNewEventCreatedAt(time);
        jpaLinkRepository.save(link);
    }

    @Override
    public void remove(long tgChatId, URI url) {

        Link link = jpaLinkRepository.findByUrl(url.toString()).get();
        List<LinkChat> linkChats = jpaLinkChatRepository.findByChat_ChatIdAndLink_Id(tgChatId, link.getId());
        jpaLinkChatRepository.deleteAll(linkChats);
        if ( jpaLinkChatRepository.findAllByLink_Id(link.getId()).size() == 0) {  // if no other chats have subscribed on this url then delete url
            jpaLinkRepository.delete(link);
        }
    }

    @Override
    public ListLinkRepository findAll(long tgChatId) {

        List<LinkChat> linkChats = jpaLinkChatRepository.findAllByChat_ChatId(tgChatId);

        List<URI> urls = linkChats.stream()
                .map(linkChat -> URI.create(linkChat.getLink().getUrl()))
                .toList();

        LinkRepository[] links = urls.stream()
                .map(LinkRepository::new)
                .toArray(LinkRepository[]::new);

        int length = links.length;

        return new ListLinkRepository(links, length);
    }

    @Override
    public ListLinkWithTimeRepository findAllFilteredByTimeout(long timeout) {

        OffsetDateTime time = OffsetDateTime.now().minusMinutes(timeout);

        List<Link> linksWithTime = jpaLinkRepository.findAllByUpdatedAtBefore(time);

        List<LinkWithTimeRepository> linkWithTimeRepositories = linksWithTime.stream()
                .map(link -> {
                    List<Long> tgChatIds = jpaLinkChatRepository.findAllByLink_Id(link.getId())
                            .stream()
                            .map(linkChat -> linkChat.getChat().getChatId())
                            .collect(Collectors.toList());
                    return new LinkWithTimeRepository(link.getId(), URI.create(link.getUrl()), link.getNewEventCreatedAt(), tgChatIds);
                })
                .toList();

        return new ListLinkWithTimeRepository(linkWithTimeRepositories.toArray(new LinkWithTimeRepository[0]), linkWithTimeRepositories.size());
    }
}