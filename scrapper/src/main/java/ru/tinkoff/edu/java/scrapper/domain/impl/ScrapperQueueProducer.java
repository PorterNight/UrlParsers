package ru.tinkoff.edu.java.scrapper.domain.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.service.dto.LinkUpdateNotifyRequestDto;

@Service
@RequiredArgsConstructor
public class ScrapperQueueProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchangeName;

    @Value("${rabbitmq.routingkey}")
    private String routingKey;

    public void send(LinkUpdateNotifyRequestDto update) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, update);
    }
}