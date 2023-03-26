package ru.tinkoff.edu.java.scrapper.dto;

public class DeleteChatRequest {

    private long id;

    public DeleteChatRequest(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

}
