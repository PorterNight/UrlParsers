package ru.tinkoff.edu.java.scrapper.jdbc;

import java.net.URI;

public class LinkDao {

    public LinkDao(long chatID, URI link) {
        this.chatID = chatID;
        this.link = link;
    }

    long chatID;
    URI link;
}
