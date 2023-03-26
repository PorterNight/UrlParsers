package ru.tinkoff.edu.java.scrapper.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LinkUpdaterScheduler {

    private static final Logger log = LoggerFactory.getLogger(LinkUpdaterScheduler.class);
    private final String interval;

    @Autowired
    public LinkUpdaterScheduler(Environment env) {
        this.interval = env.getProperty("scheduler.interval");
    }

    @Scheduled(fixedDelayString = "#{T(java.time.Duration).parse('${scheduler.interval}').toMillis()}")
    public void update() {
        log.info("Updating links from data base");
    }

}