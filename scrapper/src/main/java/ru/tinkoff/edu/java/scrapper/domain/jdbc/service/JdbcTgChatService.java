package ru.tinkoff.edu.java.scrapper.domain.jdbc.service;

import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.TgChatBaseService;
import ru.tinkoff.edu.java.scrapper.domain.TgChatService;
import ru.tinkoff.edu.java.scrapper.domain.repository.TgChatRepository;
import ru.tinkoff.edu.java.scrapper.exceptions.ScrapperControllerException;

import java.util.Arrays;

public class JdbcTgChatService implements TgChatService {


    private TgChatBaseService tgChatBaseService;

    public JdbcTgChatService(TgChatBaseService tgChatBaseService) {
        this.tgChatBaseService = tgChatBaseService;
    }

    @Transactional
    @Override
    public void register(long tgChatId) {

        // first check if tgChatId is already in table
        TgChatRepository res = tgChatBaseService.findAll();

        if (res.length() > 0 && Arrays.stream(res.tgChatId()).anyMatch(id -> id.equals(tgChatId))) {

            System.out.println("JdbcTgChatService: chat already registered");
            throw new ScrapperControllerException("Чат с id=" + tgChatId + " уже зарегистрирован: ", 400);

        } else {  // else add chatID to table

            System.out.println("JdbcTgChatService: register new chat");
            tgChatBaseService.add(tgChatId);

        }
    }

    @Transactional
    @Override
    public void unregister(long tgChatId) {

        // first check if tgChatId is already in table
        TgChatRepository res = tgChatBaseService.findAll();
        if (res.length() == 0 || !Arrays.asList(res.tgChatId()).contains(tgChatId)) {
            throw new ScrapperControllerException("Чат не существует", 404);
        } else {  // else remove chatID from table
            tgChatBaseService.remove(tgChatId);
        }
    }
}
