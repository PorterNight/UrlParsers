package ru.tinkoff.edu.java.scrapper.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.tinkoff.edu.java.scrapper.domain.repository.ListLinkRepository;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.service.JdbcLinkService;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;

import ru.tinkoff.edu.java.scrapper.domain.jdbc.service.JdbcTgChatService;
import ru.tinkoff.edu.java.scrapper.service.dto.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;

@Service
public class ScrapperService {

    private HashSet<String> urls = new HashSet<>();

    private static final Logger log = LoggerFactory.getLogger(ScrapperService.class);
    private final JdbcLinkService jdbcLinkService;
    private final JdbcTgChatService jdbcTgChatService;


    public ScrapperService(JdbcLinkService jdbcLinkService, JdbcTgChatService jdbcTgChatService) {
        this.jdbcLinkService = jdbcLinkService;
        this.jdbcTgChatService = jdbcTgChatService;
    }


    @ResponseStatus
    public String registerChat(RegisterChatDto info) {


        log.info("ScrapperService: registering chat: " + info.id());

        jdbcTgChatService.register(info.id());

        return "Чат зарегистрирован";
    }

    @ResponseStatus
    public String deleteChat(DeleteChatDto info) {

        jdbcTgChatService.unregister(info.id());

        return "Чат успешно удален";
    }

    @ResponseStatus
    public ListLinksResponse getLinks(GetLinksDto info) {

        ListLinkRepository res = jdbcLinkService.listAll(info.tgChatId());

        LinkResponse[] linkResponses = Arrays.stream(res.links())
                .map(link -> new LinkResponse(info.tgChatId(), link.url()))
                .toArray(LinkResponse[]::new);

        return new ListLinksResponse(linkResponses, res.size());

    }

    public LinkResponse addLink(AddLinkDto info) throws SQLException {


        jdbcLinkService.add(info.tgChatId(), info.link());

        return new LinkResponse(info.tgChatId(), info.link());
    }

    public LinkResponse removeLink(RemoveLinkDto info) {


        jdbcLinkService.remove(info.tgChatId(), info.link());

        return new LinkResponse(info.tgChatId(), info.link());

    }
}
