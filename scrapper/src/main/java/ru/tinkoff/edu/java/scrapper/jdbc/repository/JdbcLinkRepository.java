package ru.tinkoff.edu.java.scrapper.jdbc.repository;

import java.net.URI;

public class JdbcLinkRepository {

    private final URI[] urls;
    private final int length;


    public JdbcLinkRepository(URI[] urls, int length) {
        this.urls = urls;
        this.length = length;
    }


    public URI[] getUrls() {
        return urls;
    }

    public int getLength() {
        return length;
    }
}
