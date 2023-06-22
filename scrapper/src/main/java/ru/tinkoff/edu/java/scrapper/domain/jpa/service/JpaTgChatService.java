package ru.tinkoff.edu.java.scrapper.domain.jpa.service;

import ru.tinkoff.edu.java.scrapper.domain.TgChatBaseService;
import ru.tinkoff.edu.java.scrapper.domain.TgChatService;
import ru.tinkoff.edu.java.scrapper.domain.jpa.repository.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.exceptions.ScrapperControllerException;

public class JpaTgChatService implements TgChatService {

    private final TgChatBaseService jpaTgChatBaseService;
    private final JpaChatRepository jpaChatRepository;

    public JpaTgChatService(TgChatBaseService jpaTgChatBaseService, JpaChatRepository jpaChatRepository) {
        this.jpaTgChatBaseService = jpaTgChatBaseService;
        this.jpaChatRepository = jpaChatRepository;
    }

    @Override
    public void register(long tgChatId) {
        if (jpaChatRepository.existsById(tgChatId)) {
            System.out.println("JpaTgChatService: chat already registered");
            throw new ScrapperControllerException("Чат с id=" + tgChatId + " уже зарегистрирован: ", 400);
        } else {
            jpaTgChatBaseService.add(tgChatId);
            System.out.println("JpaTgChatService: register new chat");
        }
    }

    @Override
    public void unregister(long tgChatId) {
        if (!jpaChatRepository.existsById(tgChatId)) {
            throw new ScrapperControllerException("Чат не существует", 404);
        } else {
            jpaTgChatBaseService.remove(tgChatId);
        }
    }
}
