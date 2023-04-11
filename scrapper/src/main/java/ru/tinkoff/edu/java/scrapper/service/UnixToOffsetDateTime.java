package ru.tinkoff.edu.java.scrapper.service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class UnixToOffsetDateTime {

    public static OffsetDateTime getUnixtoOffsetDateTime(long unixTime){
        Instant instant = Instant.ofEpochSecond(unixTime);
        OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(instant, ZoneOffset.UTC);
        return offsetDateTime;
    }

}
