package ru.tinkoff.edu.java.scrapper.domain.jpa.service;

import ru.tinkoff.edu.java.scrapper.domain.TgChatBaseService;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entity.Chat;
import ru.tinkoff.edu.java.scrapper.domain.jpa.repository.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.TgChatRepository;

import java.time.OffsetDateTime;
import java.util.List;

public class JpaTgChatBaseService implements TgChatBaseService {


    private final JpaChatRepository jpaChatRepository;

    public JpaTgChatBaseService(JpaChatRepository jpaChatRepository) {
        this.jpaChatRepository = jpaChatRepository;
    }

    @Override
    public void add(long tgChatId) {
        Chat chat = new Chat();
        chat.setUpdatedAt(OffsetDateTime.now());
        chat.setChatId(tgChatId);
        jpaChatRepository.save(chat);
    }

    @Override
    public void remove(long tgChatId) {
        jpaChatRepository.deleteById(tgChatId);
    }

    @Override
    public TgChatRepository findAll() {
        List<Long> chatIds = jpaChatRepository.findAll().stream()
                .map(Chat::getChatId)
                .toList();
        Long[] chatIdArray = chatIds.toArray(new Long[0]);
        int length = chatIdArray.length;

        return new TgChatRepository(chatIdArray, length);
    }
}
