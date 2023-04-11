package ru.tinkoff.edu.java.scrapper.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;

import ru.tinkoff.edu.java.scrapper.exceptions.ScrapperControllerException;
import ru.tinkoff.edu.java.scrapper.service.dto.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;

@Service
public class ScrapperService {

    private HashSet<String> urls = new HashSet<>();

    @ResponseStatus
    public String registerChat(RegisterChatDto info) {

        if (((Long) info.id()).equals(555L))
            throw new ScrapperControllerException("Чат с id=" + info.id() + " уже зарегистрирован: ", 400);

        return "Чат зарегистрирован";
    }

    @ResponseStatus
    public String deleteChat(DeleteChatDto info) {

        if (((Long) info.id()).equals(555L))
            throw new ScrapperControllerException("Невозможно удалить чат", 400);

        if (info.id() < 0)
            throw new ScrapperControllerException("Чат не существует", 404);

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

    public LinkResponse addLink(AddLinkDto info) {

        if (info.tgChatId() < 0)
            throw new ScrapperControllerException("Ccылка отсутствует", 400);
//        if (!IsValidURL.isValidURL(info.link()))
//            throw new ScrapperControllerException("Неверный URL: " + info.link(), 400);

        urls.add(info.link().toString());

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
