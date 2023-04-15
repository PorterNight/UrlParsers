package ru.tinkoff.edu.java.scrapper.jdbc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.exceptions.ScrapperControllerException;
import ru.tinkoff.edu.java.scrapper.jdbc.TgChatService;
import ru.tinkoff.edu.java.scrapper.jdbc.repository.JdbcTgChatRepository;

import java.util.Arrays;

@Service
public class JdbcTgChatService implements TgChatService {

    private final JdbcTgChatBaseService jdbcTgChatBaseService;

    public JdbcTgChatService(JdbcTgChatBaseService jdbcTgChatBaseService) {
        this.jdbcTgChatBaseService = jdbcTgChatBaseService;
    }

    @Transactional
    @Override
    public void register(long tgChatId) {

            // first check if tgChatId is already in table
            JdbcTgChatRepository res = jdbcTgChatBaseService.findAll();

            if (res.getLength() > 0 && Arrays.stream(res.getTgChatId()).anyMatch(id -> id.equals(tgChatId))) {

                System.out.println("JdbcTgChatService: chat already registered" );
                throw new ScrapperControllerException("Чат с id=" + tgChatId + " уже зарегистрирован: ", 400);

            } else {  // else add chatID to table

                System.out.println("JdbcTgChatService: register new chat" );
                jdbcTgChatBaseService.add(tgChatId);

            }
    }

    @Transactional
    @Override
    public void unregister(long tgChatId) {

        try {
            // first check if tgChatId is already in table
            JdbcTgChatRepository res = jdbcTgChatBaseService.findAll();
            if (res.getLength() == 0 || !Arrays.asList(res.getTgChatId()).contains(tgChatId)) {
                throw new ScrapperControllerException("Чат не существует", 404);
            } else {  // else remove chatID from table
                jdbcTgChatBaseService.remove(tgChatId);
            }
        } catch (ScrapperControllerException e) {
            throw new ScrapperControllerException("Ошибка регистрации чата", 400);
        }
    }
}
