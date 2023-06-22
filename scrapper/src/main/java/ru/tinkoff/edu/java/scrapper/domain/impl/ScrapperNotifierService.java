package ru.tinkoff.edu.java.scrapper.domain.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.domain.ScrapperNotifier;
import ru.tinkoff.edu.java.scrapper.service.dto.LinkUpdateNotifyRequestDto;
import ru.tinkoff.edu.java.scrapper.webclients.ScrapperToBotClient;

@Service
@RequiredArgsConstructor
public class ScrapperNotifierService implements ScrapperNotifier {

    private final ScrapperQueueProducer scrapperQueueProducer;
    private final ScrapperToBotClient scrapperToBotClient;

    @Value("${useQueue}")
    private boolean useQueue;

    @Override
    public void send(LinkUpdateNotifyRequestDto data) {
        if (useQueue) {
            scrapperQueueProducer.send(data);
        } else {
            scrapperToBotClient.sendTrackedLinkNotify(data).blockLast();
        }
    }
}