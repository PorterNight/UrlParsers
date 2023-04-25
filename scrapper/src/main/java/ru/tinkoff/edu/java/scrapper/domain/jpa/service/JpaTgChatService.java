package ru.tinkoff.edu.java.scrapper.domain.jpa.service;

import ru.tinkoff.edu.java.scrapper.domain.TgChatService;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entity.Chat;
import ru.tinkoff.edu.java.scrapper.domain.jpa.repository.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.exceptions.ScrapperControllerException;

import java.time.OffsetDateTime;

public class JpaTgChatService implements TgChatService {

    private final JpaChatRepository jpaChatRepository;

    public JpaTgChatService(JpaChatRepository jpaChatRepository) {
        this.jpaChatRepository = jpaChatRepository;
    }

    @Override
    public void register(long tgChatId) {
        if (jpaChatRepository.existsById(tgChatId)) {
            System.out.println("JpaTgChatService: chat already registered");
            throw new ScrapperControllerException("Чат с id=" + tgChatId + " уже зарегистрирован: ", 400);
        } else {
            System.out.println("JpaTgChatService: register new chat");
            Chat chat = new Chat();
            chat.setChatId(tgChatId);
            chat.setUpdatedAt(OffsetDateTime.now());
            jpaChatRepository.save(chat);
        }
    }

    @Override
    public void unregister(long tgChatId) {
        if (!jpaChatRepository.existsById(tgChatId)) {
            throw new ScrapperControllerException("Чат не существует", 404);
        } else {
            jpaChatRepository.deleteById(tgChatId);
        }
    }
}
