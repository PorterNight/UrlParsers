package ru.tinkoff.edu.java.scrapper.dto;

public class AddLinkRequest {

    private long id;

    public AddLinkRequest(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
