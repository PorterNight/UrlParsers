package ru.tinkoff.edu.java.bot.dto;

import ru.tinkoff.edu.java.scrapper.exceptions.IsValidURL;

import java.util.List;

public class LinkUpdateRequest {

    private long id;
    private String url;
    private String description;
    private List<Long> tgChatIds;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if (IsValidURL.isValidURL(url)){
            this.url = url;
        } else
            throw new IllegalArgumentException("Invalid URL: " + url);

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getTgChatIds() {
        return tgChatIds;
    }

    public void setTgChatIds(List<Long> tgChatIds) {
        this.tgChatIds = tgChatIds;
    }
}
