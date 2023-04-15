package ru.tinkoff.edu.java.scrapper.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.tinkoff.edu.java.scrapper.jdbc.service.JdbcLinkService;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;

import ru.tinkoff.edu.java.scrapper.exceptions.ScrapperControllerException;
import ru.tinkoff.edu.java.scrapper.jdbc.service.JdbcTgChatService;
import ru.tinkoff.edu.java.scrapper.scheduler.LinkUpdaterScheduler;
import ru.tinkoff.edu.java.scrapper.service.dto.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.HashSet;

@Service
public class ScrapperService {

    private HashSet<String> urls = new HashSet<>();

    private static final Logger log = LoggerFactory.getLogger(ScrapperService.class);
//    private final JdbcLinkService jdbcLinkService;
    private final JdbcTgChatService jdbcTgChatService;


    public ScrapperService(JdbcTgChatService jdbcTgChatService) {
//        this.jdbcLinkService = jdbcLinkService;
        this.jdbcTgChatService = jdbcTgChatService;
    }


    @ResponseStatus
    public String registerChat(RegisterChatDto info) {

        log.info("Scrapper: registering chat");
        jdbcTgChatService.register(info.id());


//        if (((Long) info.id()).equals(555L))
//            throw new ScrapperControllerException("Чат с id=" + info.id() + " уже зарегистрирован: ", 400);
//
        return "Чат зарегистрирован";
    }

    @ResponseStatus
    public String deleteChat(DeleteChatDto info) {

        jdbcTgChatService.unregister(info.id());


//        if (((Long) info.id()).equals(555L))
//            throw new ScrapperControllerException("Невозможно удалить чат", 400);
//
//        if (info.id() < 0)
//            throw new ScrapperControllerException("Чат не существует", 404);

        return "Чат успешно удален";
    }

    @ResponseStatus
    public ListLinksResponse getLinks(GetLinksDto info) {

        if (info.tgChatId() < 0)
            throw new ScrapperControllerException("Ошибка получения ссылок", 400);

        LinkResponse[] listLinksResponse = urls.stream()
                .map(url -> {
                    try {
                        return new LinkResponse(info.tgChatId(), new URI(url));
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toArray(LinkResponse[]::new);

        return new ListLinksResponse(listLinksResponse, listLinksResponse.length);


    }

    public LinkResponse addLink(AddLinkDto info) throws SQLException {

        if (info.tgChatId() < 0)
            throw new ScrapperControllerException("Ccылка отсутствует", 400);

        urls.add(info.link().toString());

//        JdbcLinkService jdbcLinkService = new JdbcLinkService();
//        jdbcLinkService.add( info.tgChatId(), info.link());

        System.out.println("Scrapper urls:" + urls.toArray().toString());

        return new LinkResponse(info.tgChatId(), info.link());
    }

    public LinkResponse removeLink(RemoveLinkDto info) {

        if (info.tgChatId().equals(555L))
            throw new ScrapperControllerException("Ccылка не найдена", 404);

//        if (!IsValidURL.isValidURL(info.link()))
//            throw new ScrapperControllerException("Неверный URL: " + info.link(), 400);

        urls.remove(info.link().toString());

        return new LinkResponse(info.tgChatId(), info.link());

    }
}
