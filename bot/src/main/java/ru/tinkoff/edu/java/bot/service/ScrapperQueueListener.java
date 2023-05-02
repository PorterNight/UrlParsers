package ru.tinkoff.edu.java.bot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.service.dto.UpdateLinkDto;
import ru.tinkoff.edu.java.scrapper.scheduler.LinkUpdaterScheduler;

@Service
@RabbitListener(queues = "${rabbitmq.queue}")
public class ScrapperQueueListener {

    private final BotNotifierService botNotifierService;
    private static final Logger log = LoggerFactory.getLogger(LinkUpdaterScheduler.class);


    public ScrapperQueueListener(BotNotifierService botNotifierService) {
        this.botNotifierService = botNotifierService;
    }

    @RabbitHandler
    public void receiver(UpdateLinkDto update) {
        try {
            botNotifierService.updateLink(update);
        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException("error sending update to telegram-bot, sending message to the dead letter queue", e);
        }
    }

    @RabbitListener(queues = "${rabbitmq.queue.dlq}")
    public void logoutDlqMessages(Message failedMessage) {
        log.warn("RabbitMQ DLQ, error while sending update: " + failedMessage);
    }
}
