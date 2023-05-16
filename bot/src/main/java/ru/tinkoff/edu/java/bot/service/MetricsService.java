package ru.tinkoff.edu.java.bot.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {
    private final Counter messageCounter;

    public MetricsService(MeterRegistry registry) {
        this.messageCounter = Counter.builder("telegram.messages.processed")
            .description("Number of Telegram messages processed by module bot")
            .register(registry);
    }

    public void incrementMessageCounter() {
        messageCounter.increment();
    }
}
