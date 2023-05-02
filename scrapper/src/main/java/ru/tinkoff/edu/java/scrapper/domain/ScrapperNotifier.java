package ru.tinkoff.edu.java.scrapper.domain;

import ru.tinkoff.edu.java.scrapper.service.dto.LinkUpdateNotifyRequestDto;

public interface ScrapperNotifier {
    void send(LinkUpdateNotifyRequestDto data);
}
